package com.zbt.netty.protocol.discard;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class TimeServerPipelineFactory implements ChannelPipelineFactory{

	public ChannelPipeline getPipeline() throws Exception {
		//使用unittime协议
		return Channels.pipeline(new TimeEncoder(),new TimeServerHandler());
	}

}
