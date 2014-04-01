package org.practice.eda.event;

/**
 * 真正的消息执行业务
 * @author zhangbaitong
 *
 */
public class MessageActor {
	
	
	public void sendMessage(String message){
		System.out.println("I send message :" + message);
	}

	public static void main(String[] args) {
		MessageActor actor = new MessageActor();
		actor.sendMessage("test");
	}

}
