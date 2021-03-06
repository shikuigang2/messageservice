package com.ztth.message.gate.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

@Service
public class RedisQueueBiz {

        private static final Logger logger = LoggerFactory.getLogger(RedisQueueBiz.class);

        @Autowired
        private JedisPool jedisPool;

        /**
         * 存入List集合中
         * @param key
         * @param value
         * @return
         */
        public long lpush(String key, String value){

                Jedis jedis = null;
                try {
                        jedis = jedisPool.getResource();
                        long result = jedis.lpush(key, value);
                        return result;
                }catch (Exception e){
                        logger.error("Jedis lpush 异常 ：" + e.getMessage());
                        return 0;
                }finally {
                        if (jedis != null){
                                try {
                                        jedis.close();
                                }catch (Exception e){
                                        logger.error(e.getMessage());
                                }
                        }
                }
        }

        /**
         * 获取指定值
         * @param timeout
         * @param key
         * @return
         */
        public List<String> brpop(int timeout, String key){
                Jedis jedis = null;
                try {
                        jedis = jedisPool.getResource();
                        return jedis.brpop(timeout, key);
                }catch (Exception e){
                        logger.error("Jedis brpop 异常 ：" + e.getMessage());
                        return null;
                }finally {
                        if (jedis != null){
                                try {
                                        jedis.close();
                                }catch (Exception e){
                                        logger.error(e.getMessage());
                                }
                        }
                }
        }

        /***
         * 获取当前队列中的数据
         */

        public  List<String> getQueueList(String key){
                Jedis jedis = null;
                try {
                        jedis = jedisPool.getResource();
                        return jedis.lrange(key,0,-1);
                }catch (Exception e){
                        logger.error("Jedis brpop 异常 ：" + e.getMessage());
                        return null;
                }finally {
                        if (jedis != null){
                                try {
                                        jedis.close();
                                }catch (Exception e){
                                        logger.error(e.getMessage());
                                }
                        }
                }
        }

        /**
         * 给Redis中Set集合中某个key值设值
         * @param key
         * @param value
         */
        public void set(String key, String value){
                Jedis jedis = null;
                try {
                        jedis = jedisPool.getResource();
                        jedis.set(key, value);
                }catch (Exception e){
                        logger.error("Jedis set 异常" + e.getMessage());
                }finally {
                        if(jedis != null){
                                jedis.close();
                        }
                }
        }

        /**
         * 从Redis中Set集合中获取key对应value值
         * @param key
         */
        public String get(String key){
                Jedis jedis = null;
                try {
                        jedis = jedisPool.getResource();
                        return jedis.get(key);
                }catch (Exception e){
                        logger.error("Jedis get 异常" + e.getMessage());
                        return null;
                }finally {
                        if(jedis != null){
                                jedis.close();
                        }
                }
        }

        /**
         * 序列化
         * @param key
         * @param object
         */
        public void setObject(String key, Object object){
                set(key, JSONObject.toJSONString(object));
        }

        public <T>T getObject(String key, Class<T> clazz){

                String value = get(key);
                if (value != null){
                        return JSON.parseObject(value, clazz);
                }
                return null;
        }

        /**
         *  获取总长度
         */
        public Long getTotalLength(String prefix){
                Jedis jedis = null;
                try {
                        jedis = jedisPool.getResource();
                        Set<String> keyset = jedis.keys(prefix+"%");
                        long len = 0;
                        for (String str : keyset) {
                                len +=jedis.llen(str);
                        }

                        return len;
                }catch (Exception e){
                        logger.error("Jedis get 异常" + e.getMessage());
                        return 0L;
                }finally {
                        if(jedis != null){
                                jedis.close();
                        }
                }
        }

}
