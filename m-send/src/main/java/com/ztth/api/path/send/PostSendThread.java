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
    private String reponseType;
    private int count;
    private String queuecOut;
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
        HttpPost httpPost = new HttpPost(httpUrl);

        RedisQueueBiz redisQueueBiz = SpringUtil.getBean("redisQueueBiz",RedisQueueBiz.class);
        MessageConfig messageConfig = SpringUtil.getBean("messageConfig",MessageConfig.class);
        MobileDataBiz mobileDataBiz = SpringUtil.getBean("mobileDataBiz",MobileDataBiz.class);
        MsgLogBiz msgLogBiz = SpringUtil.getBean("msgLogBiz",MsgLogBiz.class);
        try {
            //模拟信息发送
            //出待发队列
            //String objdata = redisQueueBiz.rpop(messageConfig.getQueueIn());
            //MsgLog msgLog = JSON.parseObject(objdata, new TypeReference<MsgLog>() {});
            //msgLog.setSendtime(new Date());
            //m.updateMsgLog(msgLog);;
            //入正在发送队列
            MessageLog msglog = new MessageLog();

            String messagejson = JSON.toJSONString(message);
            //msglog.setId(new IdWorker(8).nextId());
            msglog.setMessageid(message.getId());
            msglog.setContent(message.getContent());
            msglog.setMobile(message.getMobile());
            msglog.setAttr1(String.valueOf(Thread.currentThread().getId())+"-"+message.getMobile());
            msglog.setSendtime(new Date());
            msgLogBiz.addMsgLog(msglog);

            //获取手机号 所在的省市区域
            int mobileMiddle = Integer.parseInt(message.getMobile().substring(0,7));
            MobileData mobileData = null;
            //判断手机号来源
            String mobileDataStr = redisQueueBiz.get(String.valueOf(mobileMiddle));

            if(mobileDataStr == null){
                //查询数据 并添加到redis
                mobileData = mobileDataBiz.selectMobileData(mobileMiddle);
                redisQueueBiz.set(String.valueOf(mobileMiddle),JSON.toJSONString(mobileData));
            }else{
                 mobileData = JSON.parseObject(mobileDataStr,MobileData.class)  ;
            }
            //根据 网段找到 相应的通道 信息 发送信息
            Thread.sleep(random.nextInt(5)*1000 ); //模拟发送时间 5 秒

            if(mobileData.getChannel().equals("联通")){

            }else if(mobileData.getChannel().equals("移动")){

            }else if(mobileData.getChannel().equals("电信")){

            }else{
                //错误手机号号码来源
            }
            //短信发送参考例子 http://blog.sina.com.cn/s/blog_13e4b87b80102x5lf.html


            //添加参数
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (String key : maps.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, maps.get(key)));
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, reponseType));
            } catch (Exception e) {
                e.printStackTrace();
            }

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
            }

/*            HttpClient client = new HttpClient();
            PostMethod post = new PostMethod("http://sms.webchinese.cn/web_api/");
            post.addRequestHeader("Content-Type",  "application/x-www-form-urlencoded;charset=gbk"); //在头文件中设置转码
            NameValuePair[] data = { new NameValuePair("Uid", "polaris"),        //注册的用户名
                    new NameValuePair("Key", "c83102f7fea3a7053643"),   //注册成功后，登录网站，在"修改短信接口密钥"这一栏里面
                    new NameValuePair("smsMob", "188xxxxxxxx"),               // 需要发送的手机号码
                    new NameValuePair("smsText", "验证码：9999") };           //需要发送的短信内容
            post.setRequestBody(data);
            client.executeMethod(post);
            Header[] headers = post.getResponseHeaders();
            int statusCode = post.getStatusCode();
            String result = new String(post.getResponseBodyAsString().getBytes( "gbk"));
            System.out.println(result);
            post.releaseConnection();*/


            //从发送队列中移除
            redisQueueBiz.delqueue(messageConfig.getQueueOut(),messagejson);
            msglog.setAttr2(String.valueOf(Thread.currentThread().getId())+"-"+message.getMobile()+"-"+msgLogBiz.hashCode());
            msglog.setBacktime(new Date());
            msgLogBiz.updateMsgLog(msglog);
        } catch (InterruptedException e) {
            e.printStackTrace();
            //发送超时 重新发送
        }
       // System.out.println(count);
        //跟进返回值处理
        //SpringUtil.getBean("msgLogBiz");



    }
}
