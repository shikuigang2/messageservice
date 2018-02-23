package com.ztth.api.path.send;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ztth.api.path.biz.MobileChannelBiz;
import com.ztth.api.path.biz.MsgLogBiz;
import com.ztth.api.path.biz.RedisQueueBiz;
import com.ztth.api.path.config.MessageConfig;
import com.ztth.api.path.entity.Message;
import com.ztth.api.path.entity.MobileChannel;
import com.ztth.core.constant.ServerConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Component
public class SendMsgService implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(SendMsgService.class);

    @Autowired
    private RedisQueueBiz redisQueueBiz;

    @Autowired
    private MessageConfig messageConfig;

    @Autowired
    private MobileChannelBiz mobileChannelBiz;

    private ExecutorService executorService = Executors.newCachedThreadPool();
/*
    @Value("message.queueIn")
    private String queueKey;
    @Value("message.httpUrl")
    private String httpUrl;
    @Value("message.httpUrl")
    private String reponseType;
*/

   /* @Autowired
    private MsgLogBiz msgLogBiz;*/
    @Override
    public void run(String... strings) throws Exception {

           boolean needSleep = false;
            while(true){
                /**
                 * 获取待发队列中的数据
                 */
               Set<String> queueSet = redisQueueBiz.getSetQueue(ServerConstant.WAITING_SET);
               for(String queStr:queueSet){
                   //获取待发队列长度
                   long lengthin = redisQueueBiz.getQueueLength("q_"+queStr);
                   //获取正在发送中队列长度
                   String enterpriseCode = queStr.substring(4);//企业代码
                   long lengthout = redisQueueBiz.getQueueLength("q_"+ServerConstant.SEND_PREFIX+queStr.substring(4));

                   String concurrent = redisQueueBiz.get(enterpriseCode);//该企业的最大并发数
                    if(concurrent == null || concurrent.equals("")){
                        MobileChannel m = mobileChannelBiz.getMaxConcurrentNumber(enterpriseCode);

                        if(m == null){
                            logger.info("没找到发送 渠道");
                            break;
                        }
                        concurrent = m.getAttr1();
                        redisQueueBiz.set(enterpriseCode,concurrent);
                    }

                   //正在发送中并发处理
                   if(lengthout >Integer.parseInt(concurrent)){ //达到最大并发数量
                       logger.error("通道："+queStr+"达到最大并发");
                       //break; //跳出当前循环
                   }else{
                       if(lengthin>0){
                          /* System.out.println(redisQueueBiz.getQueueLength("q_"+queStr));
                           System.out.println(queStr);*/
                           //出待发队列
                           String objdata = redisQueueBiz.rpop("q_"+queStr);
                           if(objdata == null ){
                               System.out.println("lost");
                                //return ;
                           }else{
                               //入正在发送队列
                               Message message = JSON.parseObject(objdata, new TypeReference<Message>() {});
                               //还有排队一种状态 忽略 暂时
                               //redisQueueBiz.lpush(ServerConstant.SEND_PREFIX+queStr.substring(4),objdata);//准备发送不一定执行
                               // System.out.println("创建线程");

                               messageConfig.setChannel(queStr);
                               executorService.execute(new PostSendThread(messageConfig,message));
                           }

                       }

                   }
               }

            }

    }
}
