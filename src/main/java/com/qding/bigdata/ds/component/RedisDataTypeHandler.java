package com.qding.bigdata.ds.component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.qding.framework.common.codis.JedisClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.qding.bigdata.ds.annotation.RedisDataType;
import com.qding.bigdata.ds.common.Result;
import com.qding.bigdata.ds.enums.RedisDataTypeEnum;

/**
 * Created by yanpf on 2017/8/7.
 *
 * 对redis各种类型的获取方式进行封装
 */

@Component
public class RedisDataTypeHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Autowired
    JedisClient jedisClient;

    /**
     * 处理类型与具体method的映射关系，
     * 各个处理方法里面最后一参数必须是一个Object的可变参数，以适配其他方法参数数量不一致的情况
     */
    private Map<String, Method> handleMethods;

    /**
     * 在构造方法中初始化 handleMethods
     */
    public RedisDataTypeHandler() {
        handleMethods = new HashMap<String, Method>();
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method method : methods) {
            RedisDataType redisDataType = method.getAnnotation(RedisDataType.class);
            if (redisDataType != null) {
                handleMethods.put(redisDataType.value().name(), method);
            }
        }
    }

    /**
     * 统一的对外调用接口，具体类型的调用方式会在这里进行分发
     * @param dataType
     * @param params
     * @return
     */
    public Result invoke(String dataType, Object... params) {
        Method handleMethod = handleMethods.get(dataType);
        if (handleMethod != null) {
            try {
                logger.info("处理参数.....调用的方法是:" + handleMethod.getName());
                //这里避免参数不一致的情况发生，坑爹的多参数。。。
                Class<?>[] types = handleMethod.getParameterTypes();
                Object[] realParams = new Object[types.length];
                for(int i = 0; i<types.length; i++){
                    realParams[i] = params[i];
                }
                return Result.success(handleMethod.invoke(this, realParams));
            } catch (Exception e) {
                return Result.failed(8, "系统异常" + e.getMessage());
            }
        } else {
            return Result.failed(1, "不支持的redis数据类型:" + dataType);
        }
    }

    /**
     * 最简单的获取kv值
     * @param key
     * @return
     */
    @RedisDataType(RedisDataTypeEnum.KV)
    private Object getValue(String key) {
        logger.info("获取kv的值:" + key);
        return jedisClient.get(key);
    }

    /**
     * 获取hash类型的值
     * @param key
     * @param fields 各字段
     * @return
     */
    @RedisDataType(RedisDataTypeEnum.HASH)
    private Object getHash(String key, List fields) {
        logger.info("获取hash值，key:" + key);
        Map map = new HashMap();
        if (fields != null && fields.size() > 0) {
            String[] strFields = new String[fields.size()];
            for(int i=0; i<fields.size(); i++){
                strFields[i] = fields.get(i).toString();
            }
            List<byte[]> list = jedisClient.hmget(key, strFields);
            for (int i = 0; i < strFields.length; i++) {
                map.put(fields.get(i), new String(list.get(i)));
            }
        } else {
            map = jedisClient.hgetAllString(key);
        }
        return map;
    }
}
