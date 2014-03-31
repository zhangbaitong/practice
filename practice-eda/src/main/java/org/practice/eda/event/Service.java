package org.practice.eda.event;


public class Service {
	
	private String name = "zhangtao";

	public String getName() {
		return name;
	}
	
	public void run(){
		System.out.println("I am running ...");
		Processor.trigger(new Event("client-test"));
	}

}
