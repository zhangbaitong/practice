package com.zbt.concurrent.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

public class RedisCASMachine2 {

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
		new MachineClient2(pool.get()).start();//machine2
		new MachineClient2(pool.get()).start();//machine2
	}
}

class MachineClient2 extends Thread {
	JedisPool pool;
	Jedis jedis;

	public MachineClient2(JedisPool pool) {
		jedis = pool.getResource();
	}

	public void run() {
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if ("OK".equals(jedis.set("name", "MachineClient2"))) {
			System.out.println("MachineClient2 set name 成功");
		}
		jedis.close();
	}
}