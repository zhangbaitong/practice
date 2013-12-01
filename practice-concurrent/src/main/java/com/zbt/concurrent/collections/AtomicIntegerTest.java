package com.zbt.concurrent.collections;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AtomicInteger count = new AtomicInteger();
		count.addAndGet(1);

	}

}
