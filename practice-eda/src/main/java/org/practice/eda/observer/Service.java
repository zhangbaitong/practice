package org.practice.eda.observer;

import java.util.Date;
import java.util.Observable;

public class Service extends Observable{
	
	private String name = "zhangtao";

	public String getName() {
		return name;
	}
	
	public void run(){
		System.out.println("I am running ...");
		setChanged();
		notifyObservers(new Date().toString());
	}

}
