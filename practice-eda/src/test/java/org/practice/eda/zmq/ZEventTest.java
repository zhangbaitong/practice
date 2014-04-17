package org.practice.eda.zmq;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import junit.framework.TestCase;

import org.practice.eda.common.Log;

public class ZEventTest extends TestCase {
	
	public static void main(String[] args) {
		
		//启动总线进程
		ZProcessor.getInstance();
		Log.log("-----------ZProcessor start-------------------");
		//启动服务进程
		ExecutorService proxyService = Executors.newCachedThreadPool();
		int total = 2;
		for(int i=0;i<total;i++){
			proxyService.execute(new ZProxyDemo());
		}
		try {
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.log("-----------service start-------------------");
		//启动业务进程
		ExecutorService bizService = proxyService;//Executors.newCachedThreadPool();
		int total2 = 3;
		for(int j=0;j<total2;j++){
			try {
				Thread.sleep(2 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			bizService.execute(new ZBankPayActor());
			Log.log("-----------biz start-----index-"+j+"-------------");
		}
	}

}
