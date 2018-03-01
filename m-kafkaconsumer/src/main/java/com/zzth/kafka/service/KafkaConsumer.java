package com.zzth.kafka.service;

import com.ztth.core.kafkaQueue.MessageSink;
import com.ztth.core.kafkaQueue.MessageWaiting;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

@EnableBinding(MessageSink.class)
public class KafkaConsumer {

    @StreamListener(MessageSink.INPUT)
    public void process(Message<?> message) {
        System.out.println(message.getPayload());
    }

}
