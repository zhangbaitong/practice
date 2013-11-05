package com.zbt.netty.protocol.discard;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class TimeClientPipelineFactory implements ChannelPipelineFactory{
	
	public ChannelPipeline getPipeline() {
//		ChannelPipeline pipeline = Channels.pipeline();
//		pipeline.addLast("handler", new TimeClientHandler());
//		return pipeline;
		
		//使用unittime协议
		return Channels.pipeline(new TimeDecoder(),new TimeClientHandler());
	}

}
