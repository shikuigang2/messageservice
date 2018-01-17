package com.ztth.api.path.send;


import com.alibaba.fastjson.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPubSub;

@Service
public class SubscribeQueueService extends JedisPubSub implements CommandLineRunner {

    @Override
    public void run(String... strings) throws Exception {

    }

    @Override
    public void onMessage(String channel, String message) {
        //订阅方式获取 队里数据

    }

}

