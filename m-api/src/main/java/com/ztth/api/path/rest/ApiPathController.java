package com.ztth.api.path.rest;

import com.ztth.api.path.biz.ApiPathBiz;
import com.ztth.api.path.biz.RedisQueueBiz;
import com.ztth.api.path.config.MessageConfig;
import com.ztth.core.constant.ServerConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

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
    public ResponseEntity<?> getWaitSMS( String channel) throws Exception {

        if(channel != null){
            return ResponseEntity.status(200).body(redisQueueBiz.getQueueList(ServerConstant.QUEUE_PREFIX+channel).size());
        }else{
            Long  totalLength = redisQueueBiz.getTotalLength(ServerConstant.WAITING_SET);
            Map<String,Long> result = new HashMap<String,Long>();
            result.put("totalLen",totalLength);
            return ResponseEntity.status(200).body(result);
        }
    }

    @RequestMapping(value = "/waitingMSMData")
    @ResponseBody
    public ResponseEntity<?> waitingMSMData( String channel) throws Exception {

        if(channel != null){
            return ResponseEntity.status(200).body(redisQueueBiz.getQueueList(ServerConstant.QUEUE_PREFIX+channel));
        }else{
            Long  totalLength = redisQueueBiz.getTotalLength(ServerConstant.WAITING_SET);
            Map<String,Long> result = new HashMap<String,Long>();
            result.put("totalLen",totalLength);
            return ResponseEntity.status(200).body(result);
        }
    }


    /**
     * 获取当前 并发中的 信息数量
     */
    @RequestMapping(value = "/sendingMSM")
    @ResponseBody
    public ResponseEntity<?> getSendingSMS(String channel) throws Exception {//@RequestParam
        //通过渠道号 获取当前渠道的并发数
        if(channel != null){
            return ResponseEntity.status(200).body(redisQueueBiz.getQueueList(ServerConstant.SEND_PREFIX+channel).size());
        }else{
            Long  totalLength = redisQueueBiz.getTotalLength(ServerConstant.SENDING_SET);
            Map<String,Long> result = new HashMap<String,Long>();
            result.put("totalLen",totalLength);
            return ResponseEntity.status(200).body(result);
        }
    }

    /**
     * 获取当前 并发中的 信息数量
     */
    @RequestMapping(value = "/sendingMSMData")
    @ResponseBody
    public ResponseEntity<?> sendingMSMData(String channel) throws Exception {//@RequestParam
        //通过渠道号 获取当前渠道的并发数
        if(channel != null){
            return ResponseEntity.status(200).body(redisQueueBiz.getQueueList(ServerConstant.SEND_PREFIX+channel));
        }else{
            Long  totalLength = redisQueueBiz.getTotalLength(ServerConstant.SENDING_SET);
            Map<String,Long> result = new HashMap<String,Long>();
            result.put("totalLen",totalLength);
            return ResponseEntity.status(200).body(result);
        }
    }

    /**
     * 通过 渠道 编号获取当前并发数据
     */
    @RequestMapping(value = "/sending")
    @ResponseBody
    public ResponseEntity<?> getSendingSMSByChannel(@RequestParam String channel) throws Exception {
        //return ResponseEntity.status(200).body(redisQueueBiz.getQueueList(messageConfig.getQueueOut()));
        return null;
    }

    @RequestMapping(value = "/msmCount")
    @ResponseBody
    public ResponseEntity<?> msmCount(@RequestParam String channel) throws Exception {
        //return ResponseEntity.status(200).body(redisQueueBiz.getQueueList(messageConfig.getQueueOut()));
        return null;
    }

}
