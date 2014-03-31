package org.practice.eda.event;

public class Watcher implements Watchable{

	public void recive(Event evt) {
		System.out.println("watcher recive event " + evt.getName());
	}
	

}
