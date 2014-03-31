package org.practice.eda.event;

public class MessageActor {
	
	
	public void sendMessage(String message){
		System.out.println("I send message :" + message);
	}

	public static void main(String[] args) {
		MessageActor actor = new MessageActor();
		actor.sendMessage("test");

	}

}
