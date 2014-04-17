package org.practice.eda.zmq;

import java.util.concurrent.atomic.AtomicInteger;

import org.practice.eda.common.Log;
import org.practice.eda.event.MessageActor;

public class ZProxyDemo implements ZProxy,Runnable{
	
	//存储记录响应次数
	//TODO:可以考虑在processor的调用层次做这件事情
	public AtomicInteger resultNum = new AtomicInteger();
	
	
	public int getResultNum(){
		return resultNum.get();
	}
	
	public static void main(String[] args) {
		new ZProxyDemo().run();
	}

	
	/**
	 * 订阅者的事件响应逻辑，同时记录响应次数
	 */
	public void action(String msg) {
		new MessageActor().sendMessage(msg);
		resultNum.getAndIncrement();
		Log.log("Proxy invoker number : " + this.getResultNum());
	}

	@Override
	public void run() {
		ZProxyDemo proxy = new ZProxyDemo();
		ZHelper.recive(proxy);
	}

}
