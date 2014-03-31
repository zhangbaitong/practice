package org.practice.eda.observer;

import org.practice.eda.observer.Service;
import org.practice.eda.observer.Watcher;


public class ObserverTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Service service = new Service();
		
		service.addObserver(new Watcher());
		
		service.run();
		
	}

}
