package com.zbt.async;

import java.io.PrintWriter;

public class PrintUtil {
	
	public static void println(PrintWriter out,String msg){
		//这里在多线程的时候需要考虑并发问题，目前现象是无规则输出
		out.println(msg);
		//必须输出一个html结构才能更好的展现，因为HTML的解析依赖浏览器，否则浏览器会认为是一个输出直到它碰到下一个html标签为止
		out.println("<br>");
	}

	public static void main(String[] args) {

	}

}
