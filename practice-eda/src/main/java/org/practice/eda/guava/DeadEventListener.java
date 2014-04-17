package org.practice.eda.guava;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.Subscribe;


/**
 * 如果EventBus发送的消息都不是订阅者关心的称之为Dead Event.
 * Listener waiting for the event that any message was posted but not delivered to anyone
 * 
 * 如果没有消息订阅者监听消息, EventBus将发送DeadEvent消息, 这时我们可以通过log的方式来记录这种状态
 *
 */
public class DeadEventListener {
	boolean notDelivered = false;  
	   
    @Subscribe  
    public void listen(DeadEvent event) {  
        notDelivered = true;  
    }  
   
    public boolean isNotDelivered() {  
        return notDelivered;  
    }  
}
