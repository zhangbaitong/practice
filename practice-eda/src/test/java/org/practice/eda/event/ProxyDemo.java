package org.practice.eda.event;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订阅者测试样例
 * @author zhangbaitong
 *
 */
public class ProxyDemo implements EventProxy{
	
	//存储记录响应次数
	//TODO:可以考虑在processor的调用层次做这件事情
	public AtomicInteger resultNum = new AtomicInteger();
	
	/**
	 * 订阅者的事件响应逻辑，同时记录响应次数
	 */
	public void recive(Event evt) {
		new MessageActor().sendMessage(evt.getMessage());
		resultNum.getAndIncrement();
	}
	
	public int getResultNum(){
		return resultNum.get();
	}
	
	public static void main(String[] args) {
		ProxyDemo proxy = new ProxyDemo();
		proxy.recive(new Event(""));
		System.out.println(proxy.getResultNum());
	}

}
