package com.qding.bigdata.ds.util;



import com.qding.bigdata.ds.common.Constant;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author wangjunping
 *
 */
public final class JedisUtil {

	private static JedisPool pool;

	private JedisUtil() {
	}

	private synchronized static JedisPool getPool() {
		if (pool == null) {
			JedisPoolConfig config = new JedisPoolConfig();
			// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
			// 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
			config.setMaxTotal(20);
			// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
			config.setMaxIdle(2);
			// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
			config.setMaxWaitMillis(1000);
			// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
			config.setTestOnBorrow(false);
			pool = new JedisPool(config, PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, Constant.DSREDISHOST),Integer.parseInt(PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, Constant.DSREDISPORT)));
		}
		return pool;
	}

	public static Jedis getJedis() {
		Jedis jedis = getPool().getResource();
		if (jedis==null || !jedis.isConnected()) {
			return null;
		}
		return jedis;
	}

	public static void returnResource(Jedis redis) {
		if (redis != null) {
			pool.returnResource(redis);
		}
	}

	public static void closePool() {
		if (pool != null) {
			pool.destroy();
		}
	}

	public static Jedis getJedisClient() {
		return new Jedis( PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, Constant.DSREDISHOST),Integer.parseInt(PropertiesUtil.getPropertiesByKey(Constant.CONFIGFILE, Constant.DSREDISPORT)));
	}

}
