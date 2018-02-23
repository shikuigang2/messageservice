package com.ztth.api.path.biz;

import com.ztth.core.kafkaQueue.MessageSink;
import com.ztth.core.kafkaQueue.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.core.annotation.Order;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableBinding(value ={ MessageSource.class })
//@EnableBinding(value ={Source.class })
@Component
public class KafkaProducerBiz {

    @Autowired
    private MessageSource source;

    //@Scheduled(fixedRate = 5000,initialDelay = 1000) //每隔五秒执行一次 延时一秒执行
    public boolean messageInMessageQueue(String msg) {
        return source.output().send(
                MessageBuilder.withPayload(msg).build());
    }

/*  @Scheduled(fixedRate = 3000)
    public void produceColdDrinks() {
        source.output().send(MessageBuilder
                .withPayload(Order.builder().flag("Cold").num(new Random().nextInt(100)).build()).build());

    }*/

}
