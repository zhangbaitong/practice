package com.zbt.pjax.controler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PjaxServlet extends HttpServlet{
	
	private static final long serialVersionUID = 3126380265252003575L;

	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException{  
		 res.setContentType("text/html");  
		 PrintWriter out = res.getWriter();
		 String url = req.getRequestURL().toString();
		 if(url.endsWith("1")){
			 out.println("<h1>Hello I am Html facade!<h1>");
			 out.println("<div class=\"container\" id=\"pjax-container\">");
			 out.println("Go to <a href=\"2\">second page</a>");
			 out.println("</div>");
		 }else if(url.endsWith("2")){
			 out.println("<h1>Hello I am Html facade!<h1>");
			 out.println("<div class=\"container\" id=\"pjax-container\">");
			 out.println("Go to <a href=\"3\">last page</a>");
			 out.println("</div>");
		 }else{
			 out.println("<h1>Hello I am Html facade!<h1>");
			 out.println("<div class=\"container\" id=\"pjax-container\">");
			 out.println("Go to <a href=\"1\">first</a>");
			 out.println("</div>");
		 }
	}
	
}
