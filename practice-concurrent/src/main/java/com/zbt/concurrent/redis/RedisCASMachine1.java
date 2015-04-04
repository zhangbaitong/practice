package com.zbt.concurrent.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

public class RedisCASMachine1 {

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
		Jedis jedis = pool.get().getResource();
		jedis.set("name", "zhangbaitong");
		new MachineClient1(pool.get()).start();//machine1
	}
}

class MachineClient1 extends Thread {
	JedisPool pool;
	Jedis jedis;

	public MachineClient1(JedisPool pool) {
		jedis = pool.getResource();
	}

	public void run() {
		if ("OK".equals(jedis.watch("name"))) {
			System.out.println("key:name 被监视" + jedis.get("name"));
		}
		try {
			Thread.currentThread().sleep(8000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Transaction t = jedis.multi();
		t.set("name", "MachineClient1");
		if (t.exec() == null) {
			System.out.println("数据库中的name 已经被修改,MachineClient1无法set  name");
		}
		jedis.unwatch();
		jedis.close();
	}
}