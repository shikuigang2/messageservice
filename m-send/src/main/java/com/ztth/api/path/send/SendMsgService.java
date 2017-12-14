package com.ztth.api.path.send;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ztth.api.path.biz.MsgLogBiz;
import com.ztth.api.path.biz.RedisQueueBiz;
import com.ztth.api.path.config.MessageConfig;
import com.ztth.api.path.entity.Message;
import com.ztth.api.path.spring.SpringUtil;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SendMsgService implements CommandLineRunner {

    @Autowired
    private RedisQueueBiz redisQueueBiz;

    @Autowired
    private MessageConfig messageConfig;

    private ExecutorService executorService = Executors.newCachedThreadPool();
/*
    @Value("message.queueIn")
    private String queueKey;
    @Value("message.httpUrl")
    private String httpUrl;
    @Value("message.httpUrl")
    private String reponseType;
*/

   /* @Autowired
    private MsgLogBiz msgLogBiz;*/
    @Override
    public void run(String... strings) throws Exception {


            while(true){
                //获取队列中待发 信息
                //获取待发长度
               long lengthin = redisQueueBiz.getQueueLength(messageConfig.getQueueIn());
               //获取正在发送中队列长度
               long lengthout = redisQueueBiz.getQueueLength(messageConfig.getQueueOut());
               //正在发送中并发处理
                if(lengthout > messageConfig.getMaxLength() ){ //达到最大并发数量
                    System.out.println("达到最大并发数 等待1秒");
                    Thread.sleep(200);//等待 一秒
                }else{
                    if(lengthin>0){
                        //出待发队列
                        String objdata = redisQueueBiz.rpop(messageConfig.getQueueIn());
                        //入正在发送队列
                        redisQueueBiz.lpush(messageConfig.getQueueOut(),objdata);
                        Message message = JSON.parseObject(objdata, new TypeReference<Message>() {});
                        //发送成功后
                        System.out.println("发送中......");
                       /* public PostSendThread(String httpUrl,Map<String, String> maps,String reponseType,int count,String queuecOut){
                            this.httpUrl = httpUrl;
                            this.maps = maps;
                            this.reponseType = reponseType;
                            this.count = count;
                            this.queuecOut = queuecOut;
                        }*/
                        // System.out.println("创建线程");
                        executorService.execute(new PostSendThread(messageConfig,message));
                    }

                }



               /*  MsgLog msgLog = JSON.parseObject(objdata, new TypeReference<MsgLog>() {});

                RequestConfig requestConfig = RequestConfig.custom()
                        .setSocketTimeout(15000)
                        .setConnectTimeout(15000)
                        .setConnectionRequestTimeout(15000)
                        .build();

                HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
                // 创建参数队列
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                //post 传递参数
                nameValuePairs.add(new BasicNameValuePair("mobile", msgLog.getMobile()));
                nameValuePairs.add(new BasicNameValuePair("content", msgLog.getContent()));
                nameValuePairs.add(new BasicNameValuePair("key", msgLog.getContent()));

                try {
                    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, reponseType));
                } catch (Exception e) {
                    e.printStackTrace();
                }


                CloseableHttpClient httpClient = null;
                CloseableHttpResponse response = null;
                HttpEntity entity = null;
                String responseContent = null;

                try {
                    // 创建默认的httpClient实例.
                    httpClient = HttpClients.createDefault();
                    httpPost.setConfig(requestConfig);
                    // 执行请求
                    response = httpClient.execute(httpPost);
                    entity = response.getEntity();
                    responseContent = EntityUtils.toString(entity, reponseType);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // 关闭连接,释放资源
                        if (response != null) {
                            response.close();
                        }
                        if (httpClient != null) {
                            httpClient.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }*/

                //System.out.println(responseContent);
                //跟进返回值处理
                //System.out.println(objdata);

               /* Thread.sleep(10000);
                String json = redisQueueBiz.rpop(messageConfig.getQueueOut());
                SpringUtil.getBean("msgLogBiz");*/
            }

    }
}
