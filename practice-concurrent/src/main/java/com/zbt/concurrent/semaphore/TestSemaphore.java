package com.zbt.concurrent.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class TestSemaphore {

	public static void main(String[] args) {
		
		//需要注意的是获取资源时,通过synchronized关键词或锁声明同步.
		//这是因为Semaphore类只是一个资源数量的抽象表示,并不负责管理资源对象本身,
		//可能有多个线程同时获取到资源使用许可,因此需要使用同步机制避免数据竞争.
		// 线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		// 只能5个线程同时访问，如果设置在分配许可时使用公平模式，第二个参数为true（申请资源的顺序依次获取许可）
		final Semaphore semp = new Semaphore(5);
		// 模拟20个客户端访问
		for (int index = 0; index < 20; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				public void run() {
					try {
						// 获取许可
						semp.acquire();
						//非阻塞获取许可
						//semp.tryAcquire();
						//不中断获取许可
						//semp.acquireUninterruptibly();
						System.out.println("Accessing: " + NO);
						Thread.sleep((long) (Math.random() * 10000));
						// 访问完后，释放
						semp.release();
						System.out.println("-----------------"
								+ semp.availablePermits());
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			exec.execute(run);
		}
		// 退出线程池
		exec.shutdown();
	}
}
