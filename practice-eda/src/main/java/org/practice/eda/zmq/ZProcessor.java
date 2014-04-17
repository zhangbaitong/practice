package org.practice.eda.zmq;

import org.practice.eda.common.Log;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;


/**
 * 基于于0MQ的总线实现
 * 其中使用两个消息流，一个是接受流，一个是发布流，目前流采用的是pub－sub，接受流后续可以采用req流
 * 0MQ在这里的好处是屏蔽了队列，锁，线程等待，寻址，分发的具体实现
 * @author zhangbaitong
 *
 */
public class ZProcessor implements Runnable{

	private static ZProcessor processor;
	
	private static Socket publisher;
	private static Socket reciver;
	private static Context context;
	
	public static void main(String[] args) {
		ZProcessor.getInstance();
	}
	
	private ZProcessor(){
        Context context = ZMQ.context(1);
        //接受客户端事件触发
        reciver =  context.socket(ZMQ.SUB);
        reciver.connect(ZHelper.URL_SEND);
        reciver.subscribe("".getBytes(ZMQ.CHARSET));
        //发布事件给订阅方
        publisher = context.socket(ZMQ.PUB);
        publisher.bind(ZHelper.URL_RECV);
	}
	
	public boolean isRunning = true;
	
	public void stop() {
		isRunning = false;
		reciver.close();
        publisher.close ();
        context.term ();
	}

	@Override
	/**
	 * 以独立线程的方式运行监听触发事件
	 */
	public void run() {
		Log.log("start running processor thread...");
		while (isRunning) {
			Log.debug("ZProcessor", "I am waiting....");
			//获得触发事件的信息
			String event = reciver.recvStr();
			Log.debug("ZProcessor","recive message-"+event);
			if(event != null && event.length() > 0){
				String[] msg = event.split(":");
				//List<ZEventProxy> list = getWatchList(msg[0]);
				//for(int i=0;i<list.size();i++){
					//ZEventProxy proxy = list.get(i);
					//发送指定事件的信息
					publisher.sendMore (msg[0]);//订阅方（过滤器）
					publisher.send (msg[1]);
					Log.debug("ZProcessor","send message-"+event);
				//}
			}
		}
		Log.log("ZProcessor", "stop running processor thread...");
	}

	/**
	 * 保证运行时单例模式
	 * @return
	 */
	public static ZProcessor getInstance(){
		if(processor == null){
			processor = new ZProcessor();
			new Thread(processor).start();
		}
		return processor;
		
	}
}
