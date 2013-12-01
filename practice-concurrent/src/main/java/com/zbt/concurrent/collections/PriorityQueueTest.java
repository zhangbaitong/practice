package com.zbt.concurrent.collections;

import java.util.PriorityQueue;

public class PriorityQueueTest {

	/**
	 * 一个基于优先级堆（简单的使用链表的话，可能插入的效率会比较低O(N)）的无界优先级队列。
	 * 优先级队列的元素按照其自然顺序进行排序，或者根据构造队列时提供的 Comparator 进行排序，
	 * 具体取决于所使用的构造方法。优先级队列不允许使用 null 元素。
	 * 依靠自然顺序的优先级队列还不允许插入不可比较的对象。
	 */
	public static void main(String[] args) {
		PriorityQueue queue=new PriorityQueue();
		queue.offer("testone");
		queue.offer("testtwo");
		queue.offer("testthree");
		queue.offer("testfour");
		 
		System.out.println(queue.poll()); //testfour

	}

}
