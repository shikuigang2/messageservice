package com.ztth.api.path.biz;


import com.ztth.core.kafkaQueue.MessageSink;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding({ MessageSink.class })
public class KafkaConsumerBiz {

    @StreamListener(MessageSink.INPUT)
    public synchronized void listen_average(String message) {
        System.out.println("Message From port:8766: " + message);
    }

}
