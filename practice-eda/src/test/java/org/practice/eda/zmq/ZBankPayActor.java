package org.practice.eda.zmq;

import org.practice.eda.common.Log;
import org.practice.eda.event.Event;

public class ZBankPayActor implements Runnable{
	
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
		Event event = new Event(ZBankPayActor.class.getName());
		event.setMessage("pay action finished!");
		ZHelper.send(event);
	}
	
	/**
	 * 独立运行测试业务行为
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{
		ZBankPayActor actor = new ZBankPayActor();
		actor.run();
	}
	

	/**
	 * 提供多线程能力以供测试
	 */
	@Override
	public void run() {
		ZBankPayActor actor = new ZBankPayActor();
		actor.makeOrder();
		actor.payAction();		
	}

}

