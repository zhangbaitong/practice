package com.zbt.concurrent.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

public class RedisCAS {

	public static JedisPool pool;
	public static String SIP1 = "192.168.100.20";
	static {
		JedisPoolConfig config = new JedisPoolConfig();
		config.setMaxTotal(1000);
		config.setMaxWaitMillis(3000);
		config.setTestOnBorrow(true);
		config.setMaxIdle(100);
		pool = new JedisPool(new JedisPoolConfig(), SIP1, 6379);
	}

	public static void main(String[] args) {
		Jedis jedis = pool.getResource();
		jedis.set("name", "zhangbaitong");
		new ThreadClient3(pool).start();//client1
		new ThreadClient4(pool).start();//client2
		new ThreadClient5(pool).start();//check result
	}
}

class ThreadClient3 extends Thread {
	JedisPool pool;
	Jedis jedis;

	public ThreadClient3(JedisPool pool) {
		jedis = pool.getResource();
	}

	public void run() {
		System.out.println(">>>>>>>Thread id is "+Thread.currentThread().getId());
		if ("OK".equals(jedis.watch("name"))) {
			System.out.println("key:name 被监视");
		}
		try {
			Thread.currentThread().sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Transaction t = jedis.multi();
		t.set("name", "threadClient3");
		if (t.exec() == null) {
			System.out.println("数据库中的name 已经被修改,ThreadClient3无法set  name");
		}
		jedis.unwatch();

		// pool.returnResource(jedis);
		jedis.close();
	}
}

class ThreadClient4 extends Thread {
	JedisPool pool;
	Jedis jedis;

	public ThreadClient4(JedisPool pool) {
		jedis = pool.getResource();
	}

	public void run() {
		System.out.println(">>>>>>>Thread id is "+Thread.currentThread().getId());
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if ("OK".equals(jedis.set("name", "threadclient4"))) {
			System.out.println("ThreadClient4 set name 成功");
		}
		// pool.returnResource(jedis);
		jedis.close();
	}
}

class ThreadClient5 extends Thread {
	JedisPool pool;
	Jedis jedis;

	public ThreadClient5(JedisPool pool) {
		jedis = pool.getResource();
	}

	public void run() {
		System.out.println(">>>>>>>Thread id is "+Thread.currentThread().getId());
		try {
			Thread.currentThread().sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String name = jedis.get("name");
		System.out.println("ThreadClient5 获取name 的值为:" + name);
		// pool.returnResource(jedis);
		jedis.close();
	}
}