package org.practice.eda.event;

import org.practice.eda.common.Log;

public class BankPayActor implements Runnable{
	
	/**
	 * 下订单业务
	 */
	public void makeOrder(){
		Log.log("I have make an order!");
	}
	
	/**
	 * 支付业务
	 */
	public void payAction(){
		Log.log("I have pay success!");
		//付款成功后触发信息发送事件
		Event event = new Event(BankPayActor.class.getName());
		event.setMessage("pay action finished!");
		Processor.getInstance().trigger(event);
	}
	
	/**
	 * 独立运行测试业务行为
	 * @param args
	 */
	public static void main(String[] args){
		BankPayActor actor = new BankPayActor();
		actor.run();
	}

	/**
	 * 提供多线程能力以供测试
	 */
	@Override
	public void run() {
		BankPayActor actor = new BankPayActor();
		actor.makeOrder();
		actor.payAction();		
	}

}
