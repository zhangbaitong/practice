package com.zbt.netty.protocol.discard;

import static org.jboss.netty.buffer.ChannelBuffers.buffer;
import static org.jboss.netty.channel.Channels.fireChannelDisconnected;
import static org.jboss.netty.channel.Channels.pipeline;
import static org.jboss.netty.channel.Channels.write;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineCoverage;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;


@ChannelPipelineCoverage("all")
public class TimeEncoder extends SimpleChannelHandler{
	
	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		// TODO 自动生成的方法存根
		//super.writeRequested(ctx, e);
		UnixTime time = (UnixTime) e.getMessage();
		ChannelBuffer buf = buffer(4);
		buf.writeInt(time.getValue());
		Channels.write(ctx, e.getFuture(), buf);
		//另一种写法
//		ChannelPipeline pipeline = pipeline();
//		write(ctx, e.getFuture(), buf);
//		fireChannelDisconnected(ctx);
	}

}
