package com.qding.bigdata.ds.component;

import com.qding.framework.common.codis.JedisClient;
import com.qding.framework.common.codis.JedisClientConfig;
import com.qding.framework.common.codis.JedisClientFactory;
import com.qding.framework.common.codis.JedisClientPoolConfig;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author yanpf
 * @date 2018/5/9 18:36
 * @description
 */
public class RedisTest implements FactoryBean<JedisClient>, InitializingBean, DisposableBean {

    public RedisTest(String zkAddr, String productName) {

    }

    private JedisClientPoolConfig poolConfig;

    private JedisClientConfig jedisClientConfig;

    public JedisClientPoolConfig getPoolConfig() {
        return poolConfig;
    }

    public void setPoolConfig(JedisClientPoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    public JedisClientConfig getJedisClientConfig() {
        return jedisClientConfig;
    }

    public void setJedisClientConfig(JedisClientConfig jedisClientConfig) {
        this.jedisClientConfig = jedisClientConfig;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public JedisClient getObject() throws Exception {
        return null;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
