package com.ztth.api.path.rest;

import com.alibaba.fastjson.JSON;
import com.ztth.api.path.biz.MobileChannelBiz;
import com.ztth.api.path.biz.MobileDataBiz;
import com.ztth.api.path.biz.MsgLogBiz;
import com.ztth.api.path.biz.RedisQueueBiz;
import com.ztth.api.path.config.MessageConfig;
import com.ztth.api.path.entity.Message;
import com.ztth.api.path.entity.MobileData;
import com.ztth.core.constant.ServerConstant;
import com.ztth.core.util.PhoneFormatCheckUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class msgSendController {

    @Autowired
    private MessageConfig messageConfig;

    @Autowired
    private RedisQueueBiz redisQueueBiz;

    @Autowired
    private MsgLogBiz msgLogBiz;
    @Autowired
    private MobileDataBiz mobileDataBiz;

    @Autowired
    private MobileChannelBiz mobileChannelBiz;

    @RequestMapping(value = "/send")
    @ResponseBody
    public ResponseEntity<?> sendPost(@RequestParam String mobile,@RequestParam String content,@RequestParam String token) throws Exception {

        //检查 手机号码的有效性
        if(mobile.indexOf(",")!= -1){
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
        }

        //通过手机号获取渠道号并写入相对应的队列
         //队列统一命名 sms_企业代码
        int mobileMiddle = Integer.parseInt(mobile.substring(0,7));
        MobileData mobileData = mobileDataBiz.selectMobileData(mobileMiddle);

        //根据 网段找到 相应的通道 信息 发送信息 (亦可以跟进号段 进行分配 渠道商的 号码段)
        if(mobileData == null){
            return ResponseEntity.badRequest().body("{error:'can not find send channel'}");
        }
        Message message = new Message();
        message.setMobile(mobile);
        message.setContent(content);
        message.setCreatetime(new Date());//加入队列时间
        //使用区号 作为队列
        msgLogBiz.addMsgLog(message);

        String channelQueue = ServerConstant.QUEUE_PREFIX+mobileData.getAreacode();

        if(!redisQueueBiz.isSetValue(ServerConstant.WAITING_SET,channelQueue)){
            redisQueueBiz.sSet(ServerConstant.WAITING_SET,channelQueue);
        }
        //System.out.println(channelQueue);
        redisQueueBiz.lpush(channelQueue,JSON.toJSONString(message));
        return ResponseEntity.ok(200);
    }

    @RequestMapping(value = "/sendGet")
    @ResponseBody
    public ResponseEntity<?> sendGet(String mobile,String content) throws Exception {
        return  null;
    }
}
