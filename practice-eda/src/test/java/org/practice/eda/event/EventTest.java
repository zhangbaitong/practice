package org.practice.eda.event;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import org.practice.eda.route.RouteAlgorithm;

public class EventTest extends TestCase{
	
	/**
	 * 使用Processor添加一个订阅者
	 */
	public static void testCase0(){
		//添加message发送的订阅者
		ProxyDemo proxy = new ProxyDemo();
		Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy);
		assertEquals(1, Processor.getInstance().getWatchList(BankPayActor.class.getName()));
	}
	
	/**
	 * 使用Processor添加一个订阅者并触发业务事件
	 */
	public static void testCase1(){
		//添加message发送的订阅者
		ProxyDemo proxy = new ProxyDemo();
		Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy);
		//执行银行付款业务，触发对应事件
		BankPayActor actor = new BankPayActor();
		actor.makeOrder();
		//payAction方法希望通过事件触发信息发送功能
		actor.payAction();
		try {
			//因为是异步调用，所以需要稍微等待下再进行验证
			Thread.sleep(1 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		assertEquals(1, proxy.getResultNum());
	}
	
	/**
	 * 使用Processor添加两个订阅者并触发两次业务事件，同时使用轮询策略进行分发
	 * @throws InterruptedException
	 */
	public static void testCase2() throws InterruptedException{
		//设置路由策略
		Processor.getInstance().setRoute(RouteAlgorithm.ROUTE_ROUND);
		//添加message发送的订阅者
		ProxyDemo proxy = new ProxyDemo();
		Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy);
		
		
		ProxyDemo proxy2 = new ProxyDemo();
		Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy2);
		//执行银行付款业务，触发对应事件
		BankPayActor actor = new BankPayActor();
		actor.makeOrder();
		//payAction方法希望通过事件触发信息发送功能
		actor.payAction();
		
		
		BankPayActor actor2 = new BankPayActor();
		actor2.makeOrder();
		//payAction方法希望通过事件触发信息发送功能
		actor2.payAction();
		//因为是异步调用，所以需要稍微等待下再进行验证
		Thread.sleep(1 * 1000);
		assertEquals(2, proxy.getResultNum() + proxy2.getResultNum());
//		Thread.sleep(50 * 1000);
//		System.out.println("new thread is running...");
//		new Thread(new BankPayActor()).start();
	}
	
	/**
	 * 使用Processor添加两个订阅者并使用多线程触发两次业务事件，同时使用轮询策略进行分发
	 * @throws InterruptedException
	 */
	public static void testCase3() throws InterruptedException{
		//设置路由策略
		Processor.getInstance().setRoute(RouteAlgorithm.ROUTE_ROUND);
		//添加message发送的订阅者
		ProxyDemo proxy = new ProxyDemo();
		Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy);
		
		
		ProxyDemo proxy2 = new ProxyDemo();
		Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy2);
		//执行银行付款业务，触发对应事件
		BankPayActor actor = new BankPayActor();
		actor.makeOrder();
		//payAction方法希望通过事件触发信息发送功能
		actor.payAction();
		
		
		System.out.println("new thread is running...");
		new Thread(new BankPayActor()).start();
		
		//因为是异步调用，所以需要稍微等待下再进行验证
		Thread.sleep(1 * 1000);
		assertEquals(2, proxy.getResultNum() + proxy2.getResultNum());
	}
	
	
	/**
	 * 使用Processor添加三个个订阅者并使用多线程触发n次业务事件，同时使用轮询策略进行分发
	 * @throws InterruptedException
	 */
	public static void testCase4() throws InterruptedException{
		//设置路由策略
		Processor.getInstance().setRoute(RouteAlgorithm.ROUTE_ROUND);
		//添加message发送的订阅者
		ProxyDemo proxy = new ProxyDemo();
		Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy);
		
		
		ProxyDemo proxy2 = new ProxyDemo();
		Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy2);
		
		
		ProxyDemo proxy3 = new ProxyDemo();
		Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy3);
		//多线程异步执行银行付款业务，触发对应事件
		ExecutorService service = Executors.newCachedThreadPool();
		int total = 15;
		for(int i=0;i<total;i++){
			service.execute(new BankPayActor());
		}
		
		//因为是异步调用，所以需要稍微等待下再进行验证
		Thread.sleep(5 * 1000);
		assertEquals(total, proxy.getResultNum() + proxy2.getResultNum() + proxy3.getResultNum());
	}
	/**
	 * 使用Processor添加三个个订阅者并使用多线程触发n次业务事件，同时使用轮询策略进行分发
	 * 并且在运行时动态添加订阅者
	 * @throws InterruptedException
	 */
	public static void testCase5() throws InterruptedException{
		//设置路由策略
		Processor.getInstance().setRoute(RouteAlgorithm.ROUTE_ROUND);
		//添加message发送的订阅者
		ProxyDemo proxy = new ProxyDemo();
		Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy);
		
		
		ProxyDemo proxy2 = new ProxyDemo();
		Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy2);
		
		
		ProxyDemo proxy3 = new ProxyDemo();
		Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy3);
		
		
		ProxyDemo proxy4 = new ProxyDemo();
		//多线程异步执行银行付款业务，触发对应事件
		ExecutorService service = Executors.newCachedThreadPool();
		int total = 15;
		for(int i=0;i<total;i++){
			service.execute(new BankPayActor());
			if(i == 8){
				Processor.getInstance().addWatcher(new Event(BankPayActor.class.getName()), proxy4);				
			}
		}
		
		//因为是异步调用，所以需要稍微等待下再进行验证
		Thread.sleep(5 * 1000);
		assertEquals(total, proxy.getResultNum() + proxy2.getResultNum() + proxy3.getResultNum() + proxy4.getResultNum());
	}

}
