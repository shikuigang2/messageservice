package com.ztth.api.path.rest;

import com.alibaba.fastjson.JSON;
import com.ztth.api.path.biz.MsgLogBiz;
import com.ztth.api.path.biz.RedisQueueBiz;
import com.ztth.api.path.config.MessageConfig;
import com.ztth.api.path.entity.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class msgSendController {


    @Autowired
    private MessageConfig messageConfig;

    @Autowired
    private RedisQueueBiz redisQueueBiz;

    @Autowired
    private MsgLogBiz msgLogBiz;

    @RequestMapping(value = "/sendPost")
    @ResponseBody
    public ResponseEntity<?> sendPost(String mobile,String content) throws Exception {

        //httpPost 发送
        //return ResponseEntity.status(200).body(apiPathBiz.selectListAll());
        Message msgLog = new Message();
        msgLog.setMobile(mobile);
        msgLog.setContent(content);
        msgLog.setCreatetime(new Date());//加入队列时间
        msgLogBiz.addMsgLog(msgLog);
        redisQueueBiz.lpush(messageConfig.getQueueIn(), JSON.toJSONString(msgLog));

        return ResponseEntity.ok(200);

    }

    @RequestMapping(value = "/sendGet")
    @ResponseBody
    public ResponseEntity<?> sendGet(String mobile,String content) throws Exception {


        return  null;

    }
}
