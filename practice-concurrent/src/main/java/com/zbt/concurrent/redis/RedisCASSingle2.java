package com.zbt.concurrent.redis;

import java.util.List;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Transaction;

public class RedisCASSingle2 {

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
		try {
			System.out.println(">>>> thread id is " + Thread.currentThread().getId());
			Jedis jedis = pool.getResource();
			if(!"zhangbaitong".equals(jedis.get("name"))){
				System.out.println("init");
				jedis.set("name", "zhangbaitong");	
			}else{
				System.out.println("uninit");
			}
			jedis.watch("name");
//			Thread.currentThread().sleep(3000);
			Transaction t = jedis.multi();
			System.out.println(">>>>> the value will be set " + "newvalue" + Thread.currentThread().getId());
			t.set("name", "newvalue" + Thread.currentThread().getId());
			List<Object> list = t.exec();
			if(list == null){
				System.out.println("<<<<<<<< 事务执行失败：" + jedis.get("name"));
			}else{
				System.out.println(">>>>>>>> 事务执行成功：" + jedis.get("name"));
			}
			jedis.unwatch();
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}