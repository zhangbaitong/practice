package org.practice.eda.event;

import org.practice.eda.common.Log;

/**
 * 真正的消息执行业务
 * @author zhangbaitong
 *
 */
public class MessageActor {
	
	
	public void sendMessage(String message){
		Log.log("MessageActor","I send message :" + message);
	}

	public static void main(String[] args) {
		MessageActor actor = new MessageActor();
		actor.sendMessage("test");
	}

}
