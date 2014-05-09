package com.zbt.async;

import java.io.PrintWriter;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.servlet.AsyncContext;

public class BigPipeExecutors implements Runnable{
	
    private static int threadNumber = 5;
    
	private AsyncContext ctx = null;
	private String name = "default";
	
	BigPipeExecutors(AsyncContext ctx,String name){
		this.ctx = ctx;
		this.name = name;
	}
	public void run(){
		try {
			for(int i=0;i<50;i++){
				Thread.sleep(1000);
				PrintWriter out = ctx.getResponse().getWriter();
				PrintUtil.println(out,name + "业务处理完毕的时间：" + new Date() + ".");
				out.flush();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
    
    public static void excute(AsyncContext ctx){
        try {
        	//构造一个线程池
        	ThreadPoolExecutor threadPool = new ThreadPoolExecutor(threadNumber, threadNumber*2, 60 * 1,
        	TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(threadNumber),
        	new ThreadPoolExecutor.DiscardOldestPolicy());
        	for(int i=1;i<=5;i++){
        		String name = "Tread" + i;
        		threadPool.execute(new BigPipeExecutors(ctx,name));
        	}
        	//关闭连接
        	//ctx.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

