package com.zbt.concurrent.collections;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ArrayBlockingQueueTest {

	/**
	 * FIFO的队列，与LinkedList(由链节点支持，无界)和ArrayList（由数组支持，有界）相似（Linked有更好的插入和移除性能，
	 * Array有更好的查找性能，考虑到阻塞队列的特性，移除头部，加入尾部，两个都区别不大）， 但是却拥有比同步List更好的并发性能。
	 * 另外，LinkedList永远不会等待，因为他是无界的。
	 */
	public void test() {
		BlockingQueue<String> queue = new ArrayBlockingQueue<String>(5);

		Producer p = new Producer(queue);
		Consumer c1 = new Consumer(queue);
		Consumer c2 = new Consumer(queue);

		new Thread(p).start();
		new Thread(c1).start();
		new Thread(c2).start();
	}

	public static void main(String[] args) {
		new ArrayBlockingQueueTest().test();
	}

	class Producer implements Runnable {
		private final BlockingQueue queue;

		Producer(BlockingQueue<String> q) {
			queue = q;
		}

		public void run() {
			try {
				for (int i = 0; i < 100; i++) {
					queue.put(produce());
				}

			} catch (InterruptedException ex) {
			}
		}

		String produce() {
			String temp = "" + (char) ('A' + (int) (Math.random() * 26));
			System.out.println("produce" + temp);
			return temp;
		}
	}

	class Consumer implements Runnable {
		private final BlockingQueue queue;

		Consumer(BlockingQueue q) {
			queue = q;
		}

		public void run() {
			try {
				for (int i = 0; i < 100; i++) {
					consume(queue.take());
				}
			} catch (InterruptedException ex) {
			}
		}

		void consume(Object x) {
			System.out.println("cousume" + x.toString());
		}
	}

}
