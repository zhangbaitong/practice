package org.practice.eda.event;

public class EventTest {
	
	public static void main(String[] args) {
		//添加message发送的订阅者
		
		
		//-----------------
		BankPayActor actor = new BankPayActor();
		actor.makeOrder();
		//payAction方法希望通过事件触发信息发送功能
		actor.payAction();
	}

}
