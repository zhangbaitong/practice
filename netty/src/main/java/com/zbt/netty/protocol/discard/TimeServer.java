package com.zbt.netty.protocol.discard;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class TimeServer {
	
	//ChannelGroup使用
	static final ChannelGroup allChannels = new DefaultChannelGroup("time-server");

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		
		TimeServerHandler handler = new TimeServerHandler();
		
		//原始的
//		ChannelPipeline pipeline = bootstrap.getPipeline();
//		pipeline.addLast("handle", handler);
		//使用unittime的
		bootstrap.setPipelineFactory(new TimeServerPipelineFactory());
		
		
		
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		Channel channel = bootstrap.bind(new InetSocketAddress(8080));
		
		
		//ChannelGroup使用
		allChannels.add(channel);
		//waitForShutdownCommand();
		ChannelGroupFuture future = allChannels.close();
		future.awaitUninterruptibly();
		factory.releaseExternalResources();

	}

}
