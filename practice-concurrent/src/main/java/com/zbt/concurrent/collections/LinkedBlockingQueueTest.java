package com.zbt.concurrent.collections;

import java.util.concurrent.LinkedBlockingQueue;

public class LinkedBlockingQueueTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		LinkedBlockingQueue queue = new LinkedBlockingQueue();
		//可阻塞
		queue.put("");
		queue.take();
		//可定时
		queue.offer("");
		queue.poll();
	}

}
