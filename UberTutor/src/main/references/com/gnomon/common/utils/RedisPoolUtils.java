package com.gnomon.common.utils;
import java.util.ResourceBundle;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPoolUtils {
    
    private static final JedisPool pool;
    
    static{
        ResourceBundle bundle = ResourceBundle.getBundle("redis");
        JedisPoolConfig config = new JedisPoolConfig();
        if (bundle == null) {    
            throw new IllegalArgumentException("[redis.properties] is not found!");    
        }
        //设置池配置项值  
        config.setMaxTotal(Integer.valueOf(bundle.getString("jedis.pool.maxActive")));    
        config.setMaxIdle(Integer.valueOf(bundle.getString("jedis.pool.maxIdle")));    
        config.setMaxWaitMillis(Long.valueOf(bundle.getString("jedis.pool.maxWait")));    
        config.setTestOnBorrow(Boolean.valueOf(bundle.getString("jedis.pool.testOnBorrow")));    
        config.setTestOnReturn(Boolean.valueOf(bundle.getString("jedis.pool.testOnReturn")));
        
        pool = new JedisPool(config, bundle.getString("redis.ip"),Integer.valueOf(bundle.getString("redis.port")) );
    }
    
    /**
     * 
     * @Title: release
     * @Description: 释放连接
     * @param @param jedis
     * @return void
     * @throws
     */
    public static void release(Jedis jedis){
        pool.returnResource(jedis);
    }
    
    public static Jedis getJedis(){
        return pool.getResource();
    }

}