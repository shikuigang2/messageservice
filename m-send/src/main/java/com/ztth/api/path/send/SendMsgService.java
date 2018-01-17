package com.ztth.api.path.send;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ztth.api.path.biz.MobileChannelBiz;
import com.ztth.api.path.biz.MsgLogBiz;
import com.ztth.api.path.biz.RedisQueueBiz;
import com.ztth.api.path.config.MessageConfig;
import com.ztth.api.path.entity.Message;
import com.ztth.api.path.entity.MobileChannel;
import com.ztth.api.path.spring.SpringUtil;
import com.ztth.core.constant.ServerConstant;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SendMsgService implements CommandLineRunner {

    private Logger logger = LoggerFactory.getLogger(SendMsgService.class);

    @Autowired
    private RedisQueueBiz redisQueueBiz;

    @Autowired
    private MessageConfig messageConfig;

    @Autowired
    private MobileChannelBiz mobileChannelBiz;

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

           boolean needSleep = false;
            while(true){
                /**
                 * 获取待发队列中的数据
                 */
               Set<String> queueSet = redisQueueBiz.getSetQueue(ServerConstant.WAITING_SET);
               for(String queStr:queueSet){
                   long lengthin = redisQueueBiz.getQueueLength(queStr);
                   //获取正在发送中队列长度
                   String enterpriseCode = queStr.substring(4);//企业代码
                   long lengthout = redisQueueBiz.getQueueLength(ServerConstant.SEND_PREFIX+queStr.substring(4));

                   String concurrent = redisQueueBiz.get("enterpriseCode");//该企业的最大并发数
                    if(concurrent == null || concurrent.equals("")){
                        MobileChannel m = mobileChannelBiz.getMaxConcurrentNumber(enterpriseCode);

                        if(m == null){
                            logger.info("没找到发送 渠道");
                            break;
                        }
                        concurrent = m.getAttr1();
                        redisQueueBiz.set(enterpriseCode,concurrent);
                    }

                   //正在发送中并发处理
                   if(lengthout >Integer.parseInt(concurrent)){ //达到最大并发数量
                       logger.error("通道："+queStr+"达到最大并发");
                       //break; //跳出当前循环
                   }else{
                       if(lengthin>0){
                           //System.out.println(queStr);
                           //出待发队列
                           String objdata = redisQueueBiz.rpop(queStr);
                           //入正在发送队列
                           Message message = JSON.parseObject(objdata, new TypeReference<Message>() {});
                           //还有排队一种状态 忽略 暂时
                          // redisQueueBiz.lpush(ServerConstant.SEND_PREFIX+queStr.substring(4),objdata);//准备发送不一定执行
                           // System.out.println("创建线程");

                           messageConfig.setChannel(queStr);
                           executorService.execute(new PostSendThread(messageConfig,message));
                       }

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
