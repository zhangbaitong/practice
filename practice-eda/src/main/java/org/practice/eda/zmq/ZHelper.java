package org.practice.eda.zmq;

import org.practice.eda.common.Log;
import org.practice.eda.event.Event;
import org.zeromq.ZMQ;

public class ZHelper {
	
	public static String URL_SEND = "tcp://localhost:5556";
	public static String URL_RECV = "tcp://localhost:5566";
	
	/**
	 * 消息发送实现（可以独立到客户端）
	 * @param evt
	 */
	public static void send(Event evt){
		System.out.println("send start");
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket publisher = context.socket(ZMQ.PUB);
		publisher.bind(URL_SEND);
		try {
			//初始化需要时间，不然导致第一条消息丢失
			Thread.sleep(1 * 1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        //(int i=0;i<1;i++){
        	String msg = evt.getName()+":"+evt.getMessage();
        	publisher.send(msg, 0);
        	Log.debug("send",msg);
//        	try {
//				Thread.sleep(1 * 1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
        //}
        publisher.close();
        context.term();
        System.out.println("send end ");
	}
	
	/**
	 * 消息接受实现（可以独立为客户端）
	 * @param actor
	 */
	public static void recive(ZProxy proxy){
		ZMQ.Context context = ZMQ.context(1);
		ZMQ.Socket subscriber = context.socket(ZMQ.SUB);
	    subscriber.connect(URL_RECV);
	    //定义过滤器，接受对应消息
	    String filter = ZBankPayActor.class.getName();
	    subscriber.subscribe(filter.getBytes(ZMQ.CHARSET));
	    while (!Thread.currentThread ().isInterrupted ()) {
	    	String key = subscriber.recvStr ();
	    	String msg = subscriber.recvStr ();
	    	Log.log("ZHelper recive",msg);
	    	proxy.action(msg);
	    }
	    subscriber.close ();
	    context.term ();
	}

}
