package com.zbt.concurrent.collections;

import java.util.concurrent.CopyOnWriteArrayList;


/**
 * CopyOnWriteArrayList采用写入时复制的方式避开并发问题。
 * 这其实是通过冗余和不可变性来解决并发问题，在性能上会有比较大的代价，
 * 但如果写入的操作远远小于迭代和读操作，那么性能就差别不大了.
 * 应用它们的场合通常在数组相对较小，并且遍历操作的数量大大超过可变操作的数量时，
 * 这种场合应用它们非常好。它们所有可变的操作都是先取得后台数组的副本，对副本进行更改，
 * 然后替换副本，这样可以保证永远不会抛出ConcurrentModificationException移除。
 * @author zbt
 *
 */
public class CopyOnWriteArrayListTest {
	
	public static void main(String[] args) {
		//适合
		CopyOnWriteArrayList list = new CopyOnWriteArrayList();
		//如果不存在对应key则加入
		list.addIfAbsent("key");
	}

}
