package com.zbt.concurrent.collections;

public class SynchronousQueueTest {

	/**
	 * 不是一个真正的队列，因为它不会为队列元素维护任何存储空间，不过它维护一个排队的线程清单，
	 * 这些线程等待把元素加入（enqueue）队列或者移出（dequeue）队列。
	 * 也就是说，它非常直接的移交工作，减少了生产者和消费者之间移动数据的延迟时间，另外，也可以更快的知道反馈信息，
	 * 当移交被接受时，它就知道消费者已经得到了任务。
	 * 因为SynChronousQueue没有存储的能力，所以除非另一个线程已经做好准备，否则put和take会一直阻止。
	 * 它只有在消费者比较充足的时候比较合适。
	 */
	public static void main(String[] args) {
		// TODO 自动生成的方法存根

	}

}
