package org.practice.eda.event;


public class Client {

	public static void main(String[] args) {
		Watcher wat = (Watcher)new Watcher();
		Processor.addWacher(new Event("client-test"), wat);
		Service service = new Service();
		service.run();

	}

}
