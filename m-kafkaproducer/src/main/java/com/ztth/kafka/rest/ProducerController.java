package com.ztth.kafka.rest;

import com.ztth.kafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProducerController {

    @Autowired
    private KafkaProducerService kafkaProducerService;


    @RequestMapping(value = "/addqueue")
    public boolean  produceKafkaMessage(@RequestParam("message")  String message){

        return  kafkaProducerService.sendMessage(message);

    }

}
