package com.ztth.api.path.rest;

import com.alibaba.fastjson.JSON;
import com.ztth.api.path.annotation.LogAnnotation;
import com.ztth.api.path.annotation.Valid;
import com.ztth.api.path.biz.*;
import com.ztth.api.path.entity.Message;
import com.ztth.api.path.entity.MessageLog;
import com.ztth.api.path.entity.MobileData;
import com.ztth.api.path.feignService.KafkaService;
import com.ztth.core.constant.ServerConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

@RestController
public class TestKafkaController {

    @Autowired
    private KafkaService kafkaService;



    @RequestMapping(value = "/ksend")
    @ResponseBody
    @LogAnnotation(targetType = "message",action = "send",remark = "短信发送")
    public ResponseEntity<?> sendPost(@Valid Message message , @RequestParam String mobile, @RequestParam String content, @RequestParam String token) throws Exception {

        boolean res = kafkaService.addQueue(JSON.toJSONString(message));
        //boolean res = true;
        if(res){
            return  ResponseEntity.ok(200);
        }else{
            return  ResponseEntity.status(500).body("add queue error");
        }
    }


}
