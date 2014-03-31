package org.practice.eda.observer;

import java.util.Observable;
import java.util.Observer;

public class Watcher implements Observer{

	@Override
	public void update(Observable service, Object arg1) {
		System.out.println("Service " + ((Service)service).getName() + " is running... at " + arg1);
	}

}
