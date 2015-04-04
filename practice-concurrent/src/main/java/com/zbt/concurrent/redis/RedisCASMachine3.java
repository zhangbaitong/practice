package com.zbt.concurrent.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

public class RedisCASMachine3 {

	public static ThreadLocal<JedisPool> pool = new ThreadLocal<JedisPool>();
	public static String SIP1 = "192.168.100.20";
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1000);
		config.setMaxWaitMillis(3000);
		config.setTestOnBorrow(true);
		config.setMaxIdle(100);
		pool.set(new JedisPool(new JedisPoolConfig(), SIP1, 6379));
	}

	public static void main(String[] args) {
		new MachineClient3(pool.get()).start();//machine3
	}
}

class MachineClient3 extends Thread {
	JedisPool pool;
	Jedis jedis;

	public MachineClient3(JedisPool pool) {
		jedis = pool.getResource();
	}

	public void run() {
		try {
			Thread.currentThread().sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String name = jedis.get("name");
		System.out.println("MachineClient3 获取name 的值为:" + name);
		jedis.close();
	}
}