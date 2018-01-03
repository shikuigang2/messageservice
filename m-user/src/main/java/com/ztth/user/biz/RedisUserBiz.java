package com.ztth.user.biz;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;

@Service("redisUserBiz")
public class RedisUserBiz {

    private static final Logger logger = LoggerFactory.getLogger(RedisUserBiz.class);

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

    /**
     * 删除指定元素
     */

    public boolean delqueue(String key,String value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            long i = jedis.lrem(key,1,value);
            return i > 0;
        }catch (Exception e){
            logger.error("Jedis brpop 异常 ：" + e.getMessage());
            return false;
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
                // jedisPool.returnResource(jedis);
                jedis.close();
            }
        }
    }

    public void set(String key, String value,int  time){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value);
            jedis.expire(key,time);
        }catch (Exception e){
            logger.error("Jedis set 异常" + e.getMessage());
        }finally {
            if(jedis != null){
                // jedisPool.returnResource(jedis);
                jedis.close();
            }
        }
    }

    //获取队列长度
    public long getQueueLength(String key){

        Jedis jedis = null;
        long length =0;
        try {
            jedis = jedisPool.getResource();
            //length = jedis.lrange(key,0,-1).size();
            length = jedis.llen(key);
        }catch (Exception e){
            logger.error("Jedis get 异常" + e.getMessage());
        }finally {
            if(jedis != null){
                jedis.close();
            }

        }
        return length;
    }


    public  String rpop(String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            //System.out.println(key+":"+jedis.lrange(key,0,-1).size()) ;
            return jedis.rpop(key);
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


}
