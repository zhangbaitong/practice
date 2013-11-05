package com.zbt.netty.protocol.discard;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.SimpleChannelHandler;


@ChannelPipelineCoverage("all")
public class TimeServerHandler extends SimpleChannelHandler{
	
	@Override
	public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		// TODO 自动生成的方法存根
		//super.channelOpen(ctx, e);
		//ChannelGroup使用
		TimeServer.allChannels.add(e.getChannel());
	}
	
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		// TODO 自动生成的方法存根
		//super.channelConnected(ctx, e);
//		try {
//			Channel ch = e.getChannel();
//			ChannelBuffer time= ChannelBuffers.buffer(4);
//			String timeStr = System.currentTimeMillis() / 1000 + "";
//			System.out.println("========:"+timeStr);
//			time.writeInt(Integer.parseInt(timeStr));
//			ChannelFuture f = ch.write(time);
//			f.addListener(new ChannelFutureListener() {
//				public void operationComplete(ChannelFuture future) {
//					Channel ch = future.getChannel();
//					ch.close();
//				}
//			});
//			
//		} catch (Exception e2) {
//			e2.printStackTrace();
//		}
		
		//使用unittime协议
		UnixTime time = new UnixTime(Integer.parseInt(System.currentTimeMillis() / 1000+""));
		ChannelFuture f = e.getChannel().write(time);
		f.addListener(ChannelFutureListener.CLOSE);
		
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
