package com.qding.bigdata.ds.util;

public class JedisPoolUtil {
   /* private static JedisPool pool;

    *//**
     * 建立连接池 真实环境，一般把配置参数缺抽取出来。
     *//*
    private static void createJedisPool() {

        // 建立连接池配置参数
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxActive(100);
        // 设置最大阻塞时间，记住是毫秒数milliseconds
        config.setMaxWait(1000);
        // 设置空间连接
        config.setMaxIdle(10);
        // 创建连接池 ，使用twemproxy代理
        pool = new JedisPool(config, "10.10.53.68", 6379);
        // pool = new JedisPool(config, "10.10.53.68", 6379);
    }

    *//**
     * 在多线程环境同步初始化
     *//*
    private static synchronized void poolInit() {
        if (pool == null) {
            createJedisPool();
        }
    }

    *//**
     * 获取一个jedis 对象
     *//*
    public static Jedis getJedis() {
        if (pool == null) {
            poolInit();
        }
        return pool.getResource();
    }

    *//**
     * 归还一个连接
     *//*
    public static void returnRes(Jedis jedis) {
        pool.returnResource(jedis);
    }*/
}
