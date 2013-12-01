package com.zbt.concurrent.collections;

import java.util.LinkedList;
import java.util.Queue;

public class LinkedListTest {

	public static void main(String[] args) {
		Queue queue=new LinkedList();
		queue.offer("testone");
		queue.offer("testtwo");
		queue.offer("testthree");
		queue.offer("testfour");
		System.out.println(queue.poll()); //testone

	}

}
