package org.practice.eda.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.practice.eda.route.RandomAlgorithm;
import org.practice.eda.route.RoundAlgorithm;
import org.practice.eda.route.RouteAlgorithm;

public class Processor implements Runnable{
	
	private static Processor processor;
	
	/**
	 * 初始化路由策略
	 */
	private Processor(){
		ROUTEMAP.put(RouteAlgorithm.ROUTE_RANDOM, new RandomAlgorithm());
		ROUTEMAP.put(RouteAlgorithm.ROUTE_ROUND, new RoundAlgorithm());
	}
	
	public String ROUTE = "BROADCAST";
	
	public boolean isRunning = true;
	
	/**
	 * 停止监听触发事件
	 */
	public void stop() {
		isRunning = false;
	}

	@Override
	/**
	 * 以独立线程的方式运行监听触发事件
	 */
	public void run() {
		System.out.println("start running processor thread...");
		while (isRunning) {
			response();
		}
		System.out.println("stop running processor thread...");
		
	}

	/**
	 * 保证运行时单例模式
	 * @return
	 */
	public static Processor getInstance(){
		if(processor == null){
			processor = new Processor();
			new Thread(processor).start();
		}
		return processor;
		
	}
	
	public String getRoute() {
		return ROUTE;
	}

	public void setRoute(String route) {
		ROUTE = route;
	}

	public Map<String,RouteAlgorithm> ROUTEMAP = new HashMap<String,RouteAlgorithm>();
	
	public Map<String,List<EventProxy>> watcher = new HashMap<String,List<EventProxy>>();
	
	//list不适合并发场景，故采用BlockingQueue，同时LinkedBlockingQueue比ArrayBlockingQueue插入消费效率要高，同时不需要查找需求
	public BlockingQueue<Event> eventList = new LinkedBlockingQueue<Event>(100);
	//public static List<Event> eventList = new ArrayList<Event>();
	
	/**
	 * 添加订阅者
	 * @param evt 订阅事件类型
	 * @param wat 订阅者
	 */
	public void addWatcher(Event evt,EventProxy wat){
		if(watcher.get(evt.getName()) == null){
			List<EventProxy> list =  new ArrayList<EventProxy>();
			list.add(wat);
			watcher.put(evt.getName(), list);
		}else{
			//TODO:可能会出现重复的情况
			watcher.get(evt.getName()).add(wat);
		}
	}
	
	/**
	 * 返回制定事件名称的订阅者列表
	 * @param evtName
	 * @return
	 */
	public List<EventProxy> getWatchList(String evtName){
		return watcher.get(evtName);
	}
	
	/**
	 * 事件响应逻辑
	 * 1.通过事件列表消费最早事件
	 * 2.获得当前事件的订阅者列表
	 * 3.路由策略的判断
	 * 4.订阅者响应
	 */
	public void response(){
		//1.eventList使用队列，消费后自动清除，同时满足并发
		//2.通过event事件获取key，然后通过key直接获取观察者提高遍历效率
		//3.目前使用生产者－消费者n对1模型
		//TOOD：如果消费者多的话，可以考虑n对n模型，同时可以考虑更改eventlist类型，使用双端队列窃取者模式
		try {
			System.out.println("PROCESSOR INFO:processor is listen...!");
			Event evt = eventList.take();
			System.out.println("PROCESSOR INFO:response is begin!");
			//获得当前监听列表
			List<EventProxy> list = watcher.get(evt.getName());
			//路由策略判断
			System.out.println(ROUTE);
			//广播机制
			if(ROUTE.equals(RouteAlgorithm.ROUTE_BROADCAST)){
				for(int j=0;j<list.size();j++){
					EventProxy wat = list.get(j);
					System.out.println("PROCESSOR INFO:Event " + evt.getName() + " is recive!");
					wat.recive(evt);
				}							
			}else{
				//分发算法
				System.out.println("Sum is " + list.size());
				int index = ROUTEMAP.get(ROUTE).getIndex(list.size());
				System.out.println("Index is " + index);
				EventProxy wat = list.get(index);
				System.out.println("PROCESSOR INFO:Event " + evt.getName() + " is recive!");
				wat.recive(evt);
			}
			//TODO:事件响应后的回调实现问题
		} catch (InterruptedException e) {
			System.out.println("触发事件获取失败...");
			e.printStackTrace();
		}
		
	}
	
	public void trigger(Event evt){
		//TODO:是否需要异步操作，需要测试，另外高并发的情况下是否存在问题
		System.out.println("PROCESSOR INFO:Event " + evt.getName() + " is trigger!");
		try {
			eventList.put(evt);
		} catch (InterruptedException e) {
			System.out.println("触发事件保存失败...");
			e.printStackTrace();
		}
		//之前是顺序响应，后来改成线程消费（即异步行为，存储触发事件即可）
		//response();
	}



}
