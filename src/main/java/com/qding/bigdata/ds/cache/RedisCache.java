package com.qding.bigdata.ds.cache;

import com.qding.framework.common.util.MD5Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisCluster;

import java.util.concurrent.Callable;


/**
 * Created by yanpf on 2017/9/21.
 */

public class RedisCache implements Cache {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String SPLIT_KEY = ":";

   /* private JedisClient jedisClient;*/

    private JedisCluster redisCluster;

    private RedisSerializer valueSerializer;

    private RedisSerializer keySerializer;

    private String prefix;

    private String name;

    private int expire;

    public static String PREFIX = "WEB:COMPASS:";

    public void setName(String name) {
        this.name = name;
    }

    public int getExpire() {
        return expire;
    }

    public void setExpire(int expire) {
        this.expire = expire;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Object getNativeCache() {
        return redisCluster;
    }

    public RedisCache(String prefix, String name) {
        this.prefix = prefix;
        this.name = name;
        PREFIX = PREFIX + prefix;
    }

    protected Object getCacheValue(Object key) {
        try {
            byte[] bytes = this.redisCluster.get(this.generateKey(key));
            return valueSerializer.deserialize(bytes);
        }catch (Exception e){
            logger.error("获取缓存异常，{}", e);
        }
        return null;
    }

    private byte[] generateKey(Object key) {
        String val = key.toString();
        String[] splited = val.split(SPLIT_KEY);
        String generatedKey = key.toString();
        if(splited.length >= 2) {
            String serviceKey = splited[0];
            String dataApiName = splited[1];
            //BD:DS:dataApi:apiName:md5
            generatedKey = PREFIX + prefix + ':' + serviceKey + SPLIT_KEY + dataApiName + SPLIT_KEY + MD5Util.md5(val);
        }
        return keySerializer.serialize(generatedKey);
    }

    private Integer getExpireTime(Object key){
        String[] splited = key.toString().split(SPLIT_KEY);
        if(splited.length == 7){
            return Integer.valueOf(splited[6]) * 60;
        }
        return expire;
    }

    public ValueWrapper get(Object key) {
        if (key == null) {
            logger.warn("get key is null, can't get from cache!");
            return null;
        } else {
            logger.debug("get data begin with key:{}", key);
            Object object = this.getCacheValue(key);
            if (object != null) {
                return new SimpleValueWrapper(object);
            } else {
                logger.debug("get data result is null with key:{}", key);

                return null;
            }
        }
    }

    public <T> T get(Object key, Class<T> clazz) {
        if (key == null) {
            logger.warn("get key is null, can't get from cache!");
            return null;
        }
        logger.debug("get data with key:{}, class:{}", key, clazz);

        Object object = this.getCacheValue(key);
        if (object == null) {
            logger.debug("get data result is null with key:{}, class:{}", key, clazz);
            return null;
        } else if (object.getClass() != clazz && !clazz.isAssignableFrom(object.getClass())) {
            throw new IllegalStateException("The value type is not matching with key:" + key + "! Want to get type " + clazz.getName() + ", but real type is " + object.getClass().getName());
        } else {
            return (T) object;
        }
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return null;
    }

    public void put(Object key, Object value) {
        if (value == null){
            logger.warn("put value is null, can't put to cache!");
            return;
        }
        try {
            if (getExpireTime(key) <= 0) {
                return;
            }
            if (key == null) {
                logger.warn("put key is null, can't put to cache!");
            } else {
                logger.debug("put data with key:{}, value:{}", key, value);
                redisCluster.setex(generateKey(key), getExpireTime(key), valueSerializer.serialize(value));
            }
        }catch (Exception e){
            logger.error("存放缓存发生异常:{}", e);
        }
    }

    public ValueWrapper putIfAbsent(Object key, Object value) {
        if (key == null) {
            logger.warn("put key is null, can't put to cache!");
            return null;
        }


        logger.debug("putIfAbsent data with key:{}, value:{}", key, value);


        this.put(key, value);
        return null;

    }

    @Override
    public void evict(Object key) {
        if(key == null){
            logger.warn("Redis cache[name={}] evict key is null, can't evict!", getName());
            return;
        }
        if(logger.isDebugEnabled()) {
            logger.debug("Redis cache[name={}] evict data with key:{}", getName(), key);
        }
        //删除指定的元素
        deleteCacheValue(key);
    }
    /**
     * 删除缓存数据
     * @param key
     */
    protected void deleteCacheValue(Object key){
        redisCluster.del(generateKey(key));
    }
    public void clear() {
    }

    public JedisCluster getRedisCluster() {
        return redisCluster;
    }

    public void setRedisCluster(JedisCluster redisCluster) {
        this.redisCluster = redisCluster;
    }

    public RedisSerializer getValueSerializer() {
        return valueSerializer;
    }

    public void setValueSerializer(RedisSerializer valueSerializer) {
        this.valueSerializer = valueSerializer;
    }

    public RedisSerializer getKeySerializer() {
        return keySerializer;
    }

    public void setKeySerializer(RedisSerializer keySerializer) {
        this.keySerializer = keySerializer;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
