package com.zbt.concurrent.collections;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {

	public static void main(String[] args) {
		//三个原子操作
		ConcurrentHashMap map = new ConcurrentHashMap();
		map.put("key", "value");
		//如果不存在对应key则加入
		map.putIfAbsent("key", "not value");
		//相等的值则移除
		map.remove("key", "value");
		System.out.println(map.get("key"));
		map.putIfAbsent("key", "not value");
		System.out.println(map.get("key"));
		map.replace("key", "not value2", "not value2");
		System.out.println(map.get("key"));
		//相等的值则替换
		map.replace("key", "not value", "not value2");
		System.out.println(map.get("key"));

	}

}
