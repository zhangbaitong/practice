package org.practice.eda.guava;

import junit.framework.TestCase;

import com.google.common.eventbus.EventBus;

public class Test extends TestCase{
	
	
	public void testShouldReceiveEvent() throws Exception {
	    // given  
	    EventBus eventBus = new EventBus("test");  
	    EventListener listener = new EventListener();  
	   
	    eventBus.register(listener);  
	   
	    // when  
	    eventBus.post(new OurTestEvent(200));  
	   
	    // then  
	    assertEquals(200,listener.getLastMessage()); 
	}  
	
	public void testShouldReceiveMultipleEvents() throws Exception {  
		   
	    // given  
	    EventBus eventBus = new EventBus("test");  
	    MultipleListener multiListener = new MultipleListener();  
	   
	    eventBus.register(multiListener);  
	   
	    // when  
	    eventBus.post(new Integer(100));  
	    eventBus.post(new Long(800));  
	   
	    // then  
	    assertEquals(100, multiListener.getLastInteger().intValue());
	    assertEquals(800L,multiListener.getLastLong().longValue());  
	}  
	
	public void testShouldDetectEventWithoutListeners() throws Exception {  
		   
	    // given  
	    EventBus eventBus = new EventBus("test");  
	   
	    DeadEventListener deadEventListener = new DeadEventListener();  
	    eventBus.register(deadEventListener);  
	   
	    // when  
	    eventBus.post(new OurTestEvent(200));  
	   
	    assertTrue(deadEventListener.isNotDelivered());  
	} 
	
	public void testShouldGetEventsFromSubclass() throws Exception {  
		   
	    // given  
	    EventBus eventBus = new EventBus("test");  
	    IntegerListener integerListener = new IntegerListener();  
	    NumberListener numberListener = new NumberListener();  
	    eventBus.register(integerListener);  
	    eventBus.register(numberListener);  
	   
	    // when  
	    eventBus.post(new Integer(100));  
	   
	    // then  
	    assertEquals(100,integerListener.getLastMessage().intValue());  
	    assertEquals(100,numberListener.getLastMessage());  
	   
	    //when  
	    eventBus.post(new Long(200L));  
	   
	    // then  
	    // this one should has the old value as it listens only for Integers  
	    assertEquals(100,integerListener.getLastMessage().intValue());  
	    assertEquals(200L,numberListener.getLastMessage());  
	 }  

}
