package com.zbt.concurrent.collections.example1;


/**
 * 同一个进程内的静态变量访问是一致的，不同进程的静态变量访问是不一样的。
 * 
 * 测试方法：运行本程序，再运行Service程序
 * @author zhangbaitong
 *
 */
public class Client {

	public static void main(String[] args) throws Exception {
		
		Service.init();
		System.out.println(Service.getList().size());
		
		//同一个进程内变量一致
		//Thread.sleep(5 * 1000);
		//System.out.println("start new thread...");
		//new Thread(new Service()).start();
		
		//Thread.sleep(15 * 1000);
		//防止sleep做变量保护使用循环
		for(int i=0;i<10000000;i++){
			System.out.println("index : " + i);
		}
		System.out.println("finish");

	}

}
