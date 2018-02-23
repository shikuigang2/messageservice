package com.ztth.api.path.send;

import com.ztth.core.kafkaQueue.MessageSending;
import com.ztth.core.kafkaQueue.MessageWaiting;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;

@EnableBinding(MessageWaiting.class)
public class KafkaConsumer {

    @StreamListener(MessageWaiting.MSMWAITING)
    public void process(Message<?> message) {
        System.out.println(message.getPayload());

    }

}
