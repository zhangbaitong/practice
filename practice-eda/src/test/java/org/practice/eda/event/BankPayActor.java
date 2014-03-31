package org.practice.eda.event;

public class BankPayActor {
	
	public void makeOrder(){
		System.out.println("I have make an order!");
	}
	
	public void payAction(){
		System.out.println("I have pay success!");
		//付款成功后触发信息发送事件
		Processor.trigger(new Event(BankPayActor.class.getName()));
	}
	
	public static void main(String[] args){
		BankPayActor actor = new BankPayActor();
		actor.makeOrder();
		actor.payAction();
	}

}
