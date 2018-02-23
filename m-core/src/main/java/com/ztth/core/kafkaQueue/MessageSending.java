package com.ztth.core.kafkaQueue;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

@Component
public interface MessageSending {

    String MSMSENDING = "msm-sending";

    @Output(MSMSENDING)
    MessageChannel sending();
}
