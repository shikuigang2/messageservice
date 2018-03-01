package com.ztth.kafka.service;


import com.ztth.core.kafkaQueue.MessageSource;
import com.ztth.core.kafkaQueue.MessageWaiting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

@EnableBinding(value ={ MessageSource.class })
@Component
public class KafkaProducerService {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Autowired
    private RedisServiceBiz redisServiceBiz;

    @Autowired
    private MessageSource messageSource;

    public boolean sendMessage(String msg) {

        boolean res = false;
        try {
            if(messageSource == null){
                System.out.println("1111");
            }
            res = messageSource.output().send(MessageBuilder.withPayload(msg).build());

            if(res){
                //统计排队数量 有误差 先使用
                System.out.println(System.currentTimeMillis()+":"+msg);
                redisServiceBiz.incr(MessageWaiting.MSMWAITING);
            }else{
                //添加到错误日志 或 数据表中
                logger.error("channel send error："+msg);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  res;
    }

}
