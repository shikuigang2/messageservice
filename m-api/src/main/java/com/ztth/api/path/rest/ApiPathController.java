package com.ztth.api.path.rest;

import com.ztth.api.path.biz.ApiPathBiz;
import com.ztth.api.path.biz.RedisQueueBiz;
import com.ztth.api.path.config.MessageConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class ApiPathController {

    @Autowired
    private ApiPathBiz apiPathBiz;

    @Autowired
    private RedisQueueBiz redisQueueBiz;

    @Autowired
    private MessageConfig messageConfig;

    @RequestMapping(value = "/pathAllpathAll")
    @ResponseBody
    public ResponseEntity<?> getMenuListAll() throws Exception {
        return ResponseEntity.status(200).body(apiPathBiz.selectListAll());
    }

    /**
     * 获取当前队列中待发的 信息
     */

    @RequestMapping(value = "/waitingMSM")
    @ResponseBody
    public ResponseEntity<?> getWaitSMS() throws Exception {

        return ResponseEntity.status(200).body(redisQueueBiz.getQueueList(messageConfig.getQueueIn()));
    }

    /**
     * 获取当前 并发中的 信息数量
     */
    @RequestMapping(value = "/sendingMSM")
    @ResponseBody
    public ResponseEntity<?> getSendingSMS() throws Exception {
        return ResponseEntity.status(200).body(redisQueueBiz.getQueueList(messageConfig.getQueueOut()));
    }

}
