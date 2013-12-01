package com.zbt.concurrent.collections;

import java.util.Random;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayQueueTest implements Delayed {

	/**
	 * 一个优先级堆支持的，基于时间的调度队列。 加入到队列中的元素必须实现新的Delayed接口（只有一个方法，Long
	 * getDelay(java.util.concurrent.TimeUnit unit)）， 添加可以理立即返回，但是在延迟时间过去之前，
	 * 不能从队列中取出元素，如果多个元素的延迟时间已到，那么最早失效链接/失效时间最长的元素将第一个取出。
	 */
	long tigger;

	DelayQueueTest(long i) {
		tigger = System.nanoTime() + i;
	}

	public boolean equals(Object other) {
		return ((DelayQueueTest) other).tigger == tigger;
	}

	public long getDelay(TimeUnit unit) {
		long n = tigger - System.nanoTime();
		return unit.convert(n, TimeUnit.NANOSECONDS);
	}

	public long getTriggerTime() {
		return tigger;
	}

	public int compareTo(Delayed o) {
		long i = tigger;
		long j = ((DelayQueueTest) o).tigger;
		if (i < j) {
			return -1;
		}
		if (i > j) {
			return 1;
		}
		return 0;
	}

	public static void main(String[] args) throws InterruptedException {
		Random random = new Random();
		DelayQueue<DelayQueueTest> queue = new DelayQueue<DelayQueueTest>();
		for (int i = 0; i < 5; i++) {
			queue.add(new DelayQueueTest(random.nextInt(1000)));
		}
		long last = 0;
		for (int i = 0; i < 5; i++) {
			DelayQueueTest delay = (DelayQueueTest) (queue.take());
			long tt = delay.getTriggerTime();
			System.out.println("Trigger time：" + tt);

			if (i != 0) {
				System.out.println("Data: " + (tt - last));
			}
			last = tt;
		}
	}

}
