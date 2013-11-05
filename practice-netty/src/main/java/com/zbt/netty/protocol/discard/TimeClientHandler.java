package com.zbt.netty.protocol.discard;

import java.util.Date;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import static org.jboss.netty.buffer.ChannelBuffers.*;

//静态导入方式
//import static org.jboss.netty.buffer.ChannelBuffers.*;

//ChannelBuffer dynamicBuf = dynamicBuffer(256);
//ChannelBuffer ordinaryBuf = buffer(1024);

@ChannelPipelineCoverage("one")
public class TimeClientHandler extends SimpleChannelHandler{
	
	private final ChannelBuffer buf = dynamicBuffer();
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		// TODO 自动生成的方法存根
		//super.messageReceived(ctx, e);
		
		//时间协议
//		ChannelBuffer buf = (ChannelBuffer) e.getMessage();
//		long currentTimeMillis = buf.readInt() * 1000L;
//		System.out.println(new Date(currentTimeMillis));
//		e.getChannel().close();
		
		//判断长度的时间
//		ChannelBuffer m = (ChannelBuffer) e.getMessage();
//		buf.writeBytes(m);
//		if (buf.readableBytes() >= 4) {
//			long currentTimeMillis = buf.readInt() * 1000L;
//			System.out.println(new Date(currentTimeMillis));
//			e.getChannel().close();
//		}
		
		//使用unittime协议
		UnixTime m = (UnixTime) e.getMessage();
		System.out.println(m);
		e.getChannel().close();
		
		
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		// TODO 自动生成的方法存根
		//super.exceptionCaught(ctx, e);
		e.getCause().printStackTrace();
		e.getChannel().close();
	}

}
