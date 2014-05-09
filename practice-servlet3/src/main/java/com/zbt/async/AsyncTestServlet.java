package com.zbt.async;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/async", asyncSupported = true)
public class AsyncTestServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setContentType("text/html;charset=UTF-8");
		PrintWriter out = resp.getWriter();
		PrintUtil.println(out,"进入Servlet的时间：" + new Date() + ".");
		out.flush();

		// 在子线程中执行业务调用，并由其负责输出响应，主线程退出
		AsyncContext ctx = req.startAsync();
		ctx.addListener(new AsyncListener() { 
		    public void onComplete(AsyncEvent asyncEvent) throws IOException { 
		        System.out.println("onComplete");
		    }
			public void onTimeout(AsyncEvent event) throws IOException {
				System.out.println("onTimeout");
			}
			public void onError(AsyncEvent event) throws IOException {
				System.out.println("onError");
			}
			public void onStartAsync(AsyncEvent event) throws IOException {
				System.out.println("onStartAsync");
			} 
		}); 

		new Thread(new AsyncExecutor(ctx)).start();

		out.println("结束Servlet的时间：" + new Date() + ".");
		out.println("<br>");
		out.flush();
	}
}
