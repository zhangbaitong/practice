package com.zbt.async;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.AsyncContext;

public class AsyncExecutor implements Runnable {
    private AsyncContext ctx = null;
    public AsyncExecutor(AsyncContext ctx){
        this.ctx = ctx;
    }

    public void run(){
        try {
            //等待，以模拟业务方法的执行
            Thread.sleep(1000 * 5);
            PrintWriter out = ctx.getResponse().getWriter();
            PrintUtil.println(out,"业务处理完毕的时间：" + new Date() + ".");
            out.flush();
            ctx.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
