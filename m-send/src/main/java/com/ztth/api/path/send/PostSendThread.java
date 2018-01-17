package com.ztth.api.path.send;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ztth.api.path.biz.MobileDataBiz;
import com.ztth.api.path.biz.MsgLogBiz;
import com.ztth.api.path.biz.RedisQueueBiz;
import com.ztth.api.path.config.MessageConfig;
import com.ztth.api.path.entity.Message;
import com.ztth.api.path.entity.MessageLog;
import com.ztth.api.path.entity.MobileData;
import com.ztth.api.path.spring.SpringUtil;
import com.ztth.core.constant.ServerConstant;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.*;

public class PostSendThread extends Thread {

    private int counter = 0;
    private String httpUrl;
    private Map<String, String> maps ;//参数集合
  /*  private String reponseType;
    private int count;
    private String queuecOut;*/
    private MessageConfig messageConfig;
    private Message message;

    private Random random = new Random();

    public PostSendThread(MessageConfig messageConfig,Message message){

        this.messageConfig = messageConfig;
        this.message = message;

    }

    private RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(15000)
            .setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000)
            .build();

    public void run() {

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
       // HttpPost httpPost = new HttpPost(httpUrl);

        RedisQueueBiz redisQueueBiz = SpringUtil.getBean("redisQueueBiz",RedisQueueBiz.class);
        MsgLogBiz msgLogBiz = SpringUtil.getBean("msgLogBiz",MsgLogBiz.class);

            //模拟信息发送
            //出待发队列
            String channel = messageConfig.getChannel();
            //String objdata = redisQueueBiz.rpop(messageConfig.getChannel());
            String dataSending = JSON.toJSONString(message);
            String sendQueue = ServerConstant.SEND_PREFIX + channel.substring(4);
            redisQueueBiz.lpush(sendQueue,dataSending);

            if(!redisQueueBiz.isSetValue(ServerConstant.SENDING_SET,sendQueue)){
                redisQueueBiz.sSet(ServerConstant.SENDING_SET,sendQueue);
            }

            //入正在发送队列
            MessageLog msglog = new MessageLog();
            //msglog.setId(new IdWorker(8).nextId());
            msglog.setMessageid(message.getId());
            msglog.setContent(message.getContent());
            msglog.setMobile(message.getMobile());
            msglog.setAttr1(String.valueOf(Thread.currentThread().getId())+"-"+message.getMobile());
            msglog.setSendtime(new Date());
            msgLogBiz.addMsgLog(msglog);

            //从发送队列中移除
            redisQueueBiz.delqueue(ServerConstant.SEND_PREFIX+channel.substring(4),dataSending);
            msglog.setAttr2(String.valueOf(Thread.currentThread().getId())+"-"+message.getMobile()+"-"+msgLogBiz.hashCode());
            msglog.setBacktime(new Date());
            msgLogBiz.updateMsgLog(msglog);

       // System.out.println(count);
        //跟进返回值处理
        //SpringUtil.getBean("msgLogBiz");



    }
}
