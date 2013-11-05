package com.zbt.netty.protocol.discard;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;


@ChannelPipelineCoverage("all")
public class DiscardServerHandler extends SimpleChannelHandler{
	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		//DISCARD protocol
		//super.messageReceived(ctx, e);
		
		//discard
		
//		ChannelBuffer buf = (ChannelBuffer) e.getMessage();
//		while(buf.readable()) {
//			System.out.println((char) buf.readByte());
//		}
		
		//echo
		
		Channel ch = e.getChannel();
		ch.write(e.getMessage());
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
			throws Exception {
		// TODO 自动生成的方法存根
		//super.exceptionCaught(ctx, e);
		e.getCause().printStackTrace();
		Channel ch = e.getChannel();
		ch.close();
	}

}
