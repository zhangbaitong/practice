package com.zbt.async;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.AsyncContext;

public class BigPipeExecutor implements Runnable {
    private AsyncContext ctx = null;
    public BigPipeExecutor(AsyncContext ctx){
        this.ctx = ctx;
    }

    public void run(){
        try {
        	for(int i=0;i<50;i++){
        		//等待十秒钟，以模拟业务方法的执行
        		Thread.sleep(1000);
        		PrintWriter out = ctx.getResponse().getWriter();
        		long sid = Thread.currentThread().getId();
        		PrintUtil.println(out,sid + "业务处理完毕的时间：" + i + ": " + new Date() + ".");
        		out.flush();
        	}
        	ctx.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
