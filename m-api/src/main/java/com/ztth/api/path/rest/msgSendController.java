package com.ztth.api.path.rest;

import com.alibaba.fastjson.JSON;
import com.ztth.api.path.annotation.LogAnnotation;
import com.ztth.api.path.biz.*;
import com.ztth.api.path.annotation.Valid;
import com.ztth.api.path.config.MessageConfig;
import com.ztth.api.path.entity.Message;
import com.ztth.api.path.entity.MessageLog;
import com.ztth.api.path.entity.MobileData;

import com.ztth.core.constant.ServerConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
public class msgSendController {

/*    @Autowired
    private MessageConfig messageConfig;*/

/*    @Autowired
    private MessageKafkaBiz messageKafkaBiz;*/

    @Autowired
    private KafkaProducerBiz kafkaProducerBiz;

    @Autowired
    private RedisQueueBiz redisQueueBiz;

    @Autowired
    private MessageBiz messageBiz;

    @Autowired
    private MsgLogBiz msgLogBiz;

    @Autowired
    private MobileDataBiz mobileDataBiz;

    @Autowired
    private MobileChannelBiz mobileChannelBiz;

    @RequestMapping(value = "/send")
    @ResponseBody
    @LogAnnotation(targetType = "message",action = "send",remark = "短信发送")
    public ResponseEntity<?> sendPost(@Valid Message message , @RequestParam String mobile, @RequestParam String content, @RequestParam String token) throws Exception {

        //检查 手机号码的有效性
       /* if(mobile.indexOf(",")!= -1){
            String[] moblies = mobile.split(",");
            for(int i =0 ;i<moblies.length;i++){
                if(!PhoneFormatCheckUtils.isChinaPhoneLegal(moblies[i])){
                    return ResponseEntity.badRequest().body("{error:'invalid mobile'}");
                }
            }
        }else{
            if(!PhoneFormatCheckUtils.isChinaPhoneLegal(mobile)){
                return ResponseEntity.badRequest().body("{error:'invalid mobile'}");
            }
        }*/

        /*if(message != null ){
            return ResponseEntity.ok(200);
        }*/

        //通过手机号获取渠道号并写入相对应的队列
         //队列统一命名 sms_企业代码
        int mobileMiddle = Integer.parseInt(mobile.substring(0,7));
        MobileData mobileData = mobileDataBiz.selectMobileData(mobileMiddle);

        //根据 网段找到 相应的通道 信息 发送信息 (亦可以跟进号段 进行分配 渠道商的 号码段)
        if(mobileData == null){
            return ResponseEntity.badRequest().body("{error:'can not find send channel'}");
        }
      /*  Message message = new Message();
        message.setMobile(mobile);
        message.setContent(content);*/
        message.setCreatetime(new Date());//加入队列时间
        //使用区号 作为队列
        messageBiz.addMsgLog(message);

        String channelQueue = ServerConstant.QUEUE_PREFIX+mobileData.getAreacode();

        if(!redisQueueBiz.isSetValue(ServerConstant.WAITING_SET,channelQueue)){
            redisQueueBiz.sSet(ServerConstant.WAITING_SET,channelQueue);
        }
        //集合中的 值不能直接做 队列的键值
        long aa = redisQueueBiz.lpush("q_"+channelQueue,JSON.toJSONString(message));
        //System.out.println("q_"+channelQueue+":"+aa);
        return ResponseEntity.ok(200);
    }

    @RequestMapping(value = "/sendGet")
    @ResponseBody
    public ResponseEntity<?> sendGet(@Valid Message message) throws Exception {

        //messageKafkaBiz.sendMessage(JSON.toJSONString(message));
        boolean res = kafkaProducerBiz.messageInMessageQueue(JSON.toJSONString(message));
        //boolean res = true;
        if(res){
            return  ResponseEntity.ok(200);
        }else{
            return  ResponseEntity.status(500).body("add queue error");
        }

    }

    @RequestMapping(value = "/addMSG")
    @ResponseBody
    public ResponseEntity<?> addMSG(String mobile,String content) throws Exception {

        Random r = new Random();

        for(int i=0;i<10000;i++){

            MessageLog msgLog = new MessageLog();
            if(i%3==1){
                msgLog.setMobile("138"+(r.nextInt(89999999)+10000000));
                msgLog.setChannel("移动");
            }else  if(i%3==2){
                msgLog.setMobile("186"+(r.nextInt(89999999)+10000000));
                msgLog.setChannel("联通");
            }else{
                msgLog.setMobile("177"+(r.nextInt(89999999)+10000000));
                msgLog.setChannel("电信");
            }
            msgLog.setContent("测试短信1231231223【助通科技】");
            msgLog.setSendtime(new Date());
            msgLog.setBacktime(new Date());
            msgLog.setMessageid(100000L+i);
            msgLogBiz.addMsgLog(msgLog);
        }

        return null;
    }
}
