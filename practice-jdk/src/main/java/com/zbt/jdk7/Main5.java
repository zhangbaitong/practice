package com.zbt.jdk7;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;

public class Main5 {
	
	/**
	 * 语法层面上支持集合，不再是数组的专利(jdk7没有这个特性)
	 */
	public static void testCollectionSupport(){
		//final List<Integer> piDigits = [1,2,3,4,5,8]; 
		//List<Integer> intList = [3,4,5];
		//Set<Integer> primes = { 2, 7, 31, 127, 8191, 131071, 524287 };
		//Map<Integer, String> platonicSolids = { 4 : "tetrahedron",       6 : "cube", 8 : "octahedron", 12 : "dodecahedron", 20 : "icosahedron"      }; 
	}
	
	/**
	 * 不存在.蛋疼的转载。。。
	 */
	public static void testSystemProperties(){
//		File Systems.getJavaIoTempDir(); // IO临时文件夹 
//		File System.getJavaHomeDir() // JRE的安装目录 
//		File System.getUserHomeDir() // 当前用户目录 
//		File System.getUserDir() // 启动java进程时所在的目录
	}
	
	/**
	 * 支持任何操作系统文件
	 */
	public static void testFilePath(){
		Path path = Paths.get("file url");
		path.toFile().toPath();
	}
	
	public static void testSmallFeature(){
		//not null api
		Object obj = null;
		//Objects.requireNonNull(obj, "not null pls");
		//null tostring
		obj = Objects.toString(obj, "is null");
		System.out.println(obj);
		//hash
		int hashCode = Objects.hash("name","age","address");
		System.out.println("hashCode:" + hashCode);
		//equals
		boolean result = Objects.equals(null, "abc");
		System.out.println("equals:" + result);
		//compare
		Objects.compare("a", "b",new Comparator(){
			public int compare(Object o1, Object o2) {
				return 0;
			}
		});
	}
	
	public static void testDD(){
		//Math.s
	}

	
	/**
	 * 虚拟机提升:Garbage-First Collector （G1）是一个服务器端的垃圾收集器用于替换 Concurrent  Mark-Sweep Collector (CMS). 
	 * NIO.2和java.nio.file
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		testSmallFeature();
		
		//并发HashMap,传输队列，轻量级的fork/join框架，本地线程的伪随机数生成
		
		//
		//LinkedTransferQueue 
		
		
		/**
		 * 可以通过真正的异步I/O，在不同的线程中运行数以万计的流操作！JKD7提供了对文件和socket的异步操作。一些JDK7中的新通道：
AsynchronousFileChannel：异步文件通道可以完成对文件的异步读写操作。
AsynchronouseSocketChannel：Socket中的一个简单异步通道，方法是异步的并且支持超时。
AsynchronousServerSocketChannel：异步的ServerSocket
AsynchronousDatagramChannel：基于数据包的异步socket
		 */

		
		/**
		 * 在JDK7中面向通道的网络编程也得以更新！JDK7中可以直接绑定通道的socket和直接操作socket属性。JDK7提供了平台socket属性和指定实现的socket属性。
JDK7加入了一个新的字节通道类，SeekableByteChannel
NetworkChannel是面向网络通道编程模块中的又一个新的超接口。利用它可以方便的绑定通道socket，并且方便设置和获取socket的属性。
MulticastChannel接口方便创建IP协议多播。多播实现直接绑定到本地的多播设备。

 
ForkJoinPool 

ThreadLocalRandom
Phaser 类是一个新的同步的屏障，与 
CyclicBarrier 类似.
		 */
	}

}
