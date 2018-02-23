package com.ztth.api.path.send;

import com.alibaba.fastjson.JSON;
import com.ztth.api.path.biz.MsgLogBiz;
import com.ztth.api.path.biz.RedisQueueBiz;
import com.ztth.api.path.config.MessageConfig;
import com.ztth.api.path.entity.Message;
import com.ztth.api.path.entity.MessageLog;
import com.ztth.api.path.spring.SpringUtil;
import com.ztth.core.constant.ServerConstant;
import com.ztth.core.util.HttpRequest;
import com.ztth.core.util.MD5Gen;
import com.ztth.core.util.TimeUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

            //System.out.println("dataSending:"+dataSending);
            //System.out.println("message:"+message);
            String sendQueue = ServerConstant.SEND_PREFIX + channel.substring(4);
            redisQueueBiz.lpush("q_"+sendQueue,dataSending);

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


        String url = messageConfig.getSingleSendUrl();
        String username=messageConfig.getUid();  //账e号
        String password=messageConfig.getKey();  //密码
        String tkey= TimeUtil.getNowTime("yyyyMMddHHmmss");
        String mobile=message.getMobile();  //发送的手机号
        String content=message.getContent();   //内容
        //String msgid=message.getId()+"";

        //String time="2016-09-06 17:48:22";//定时信息所需参数时间格式为yyyy-MM-dd HH:mm:ss
        String xh="";
        try {
            content= URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        StringBuilder  sb = new StringBuilder();

      /*  try {
            content=URLEncoder.encode(content, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        sb.append(url);
        sb.append("&username=").append(username);
        sb.append("&password=").append(MD5Gen.getMD5(MD5Gen.getMD5(password)+tkey));
        sb.append("&tkey=").append(TimeUtil.getNowTime("yyyyMMddHHmmss"));
        sb.append("&mobile=").append(mobile);
        sb.append("&content=").append(content);
        //sb.append("&msgid=").append(msgid);
      /*  int i = 0;
        if(i==0){
            return ;
        }
*/
        //String param="url="+url+"&username="+username+"&password="+MD5Gen.getMD5(MD5Gen.getMD5(password)+tkey)+"&tkey="+tkey+"&time="+time+"&mobile="+mobile+"&content="+content+"&xh="+xh;//定时信息url输出
        String param = sb.toString();
        System.out.println(param);
        String ret= HttpRequest.sendPost(url,param);//定时信息只可为post方式提交
        String rescode = ret.split(",")[0];

        System.out.println(rescode);

        if(rescode.equals("1")){
            redisQueueBiz.delqueue("q_"+ServerConstant.SEND_PREFIX+channel.substring(4),dataSending);
            msglog.setAttr2("success");
        }else{
            redisQueueBiz.delqueue("q_"+ServerConstant.SEND_PREFIX+channel.substring(4),dataSending);
            msglog.setAttr2("error");
            msglog.setAttr3(ret);
        }

            //从发送队列中移除

            msglog.setBacktime(new Date());
            msgLogBiz.updateMsgLog(msglog);

       // System.out.println(count);
        //跟进返回值处理
        //SpringUtil.getBean("msgLogBiz");



    }
}
