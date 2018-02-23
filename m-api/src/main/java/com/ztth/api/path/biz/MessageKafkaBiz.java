package com.ztth.api.path.biz;

import com.ztth.core.kafkaQueue.MessageWaiting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Service;

//@Service
//@EnableBinding(MessageWaiting.class)
public class MessageKafkaBiz {


    @Autowired
    private MessageWaiting messageWaiting;

    public void sendMessage(String msg) {
        try {
            if(messageWaiting == null){
                System.out.println("1111");
            }
            boolean res = messageWaiting.waiting().send(MessageBuilder.withPayload(msg).build());
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
