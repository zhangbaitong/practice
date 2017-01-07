package com.zbt.concurrent.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

public class RedisTest {

	public static JedisPool pool;
	public static String SIP1 = "192.168.100.9";
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1000);
		config.setMaxWaitMillis(3000);
		config.setTestOnBorrow(true);
		config.setMaxIdle(100);
		pool = new JedisPool(new JedisPoolConfig(), SIP1, 6379);
	}

	public static void main(String[] args) {
		System.out.println(">>>>>>>>>>>>>>>");
		Jedis jedis = pool.getResource();
		jedis.set("name", "testbyjedis");
		System.out.println(jedis.get("name"));
	}
}