package com.ztth.core.kafkaQueue;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;
import org.springframework.stereotype.Component;

@Component
public interface ZtthMessage {

    String  boardcastMSM = "boardcastMSM";
    String  waitingMSM = "waitingMSM";
    String  sendingMSM = "sendingMSM";

    @Input("boardcastMSM") //自定义通道名称
    SubscribableChannel boardcastMSM(); //广播通道


    @Output("waitingMSM")
    MessageChannel waitingMSM(); //等待通道


    @Output("sendingMSM")
    MessageChannel sendingMSM(); //正在发送通道

}
