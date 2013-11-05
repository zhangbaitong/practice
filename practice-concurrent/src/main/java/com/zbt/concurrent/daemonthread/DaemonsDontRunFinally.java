package com.zbt.concurrent.daemonthread;

import java.util.concurrent.TimeUnit;

/**
 * 
 * Finally shoud be always run ?
 */

public class DaemonsDontRunFinally {

	/**
	 * 
	 * @param args
	 */

	public static void main(String[] args) {

		Thread t = new Thread(new ADaemon());

		t.setDaemon(true);

		t.start();

	}

}

class ADaemon implements Runnable {

	public void run() {

		try {

			System.out.println("start ADaemon...");

			TimeUnit.SECONDS.sleep(1);

		} catch (InterruptedException e) {

			System.out.println("Exiting via InterruptedException");

		} finally {

			System.out.println("This shoud be always run ?");

		}

	}

}