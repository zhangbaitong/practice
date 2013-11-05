package com.zbt.netty.protocol.discard;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

public class TimeClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//String host = args[0];
		String host = "localhost";
		//int port = Integer.parseInt(args[1]);
		int port = Integer.parseInt("8080");
		ChannelFactory factory =
		new NioClientSocketChannelFactory (
		Executors.newCachedThreadPool(),
		Executors.newCachedThreadPool());
		ClientBootstrap bootstrap = new ClientBootstrap (factory);
		
		
//		TimeClientHandler handler = new TimeClientHandler();
//		bootstrap.getPipeline().addLast("handler", handler);
		bootstrap.setPipelineFactory(new TimeClientPipelineFactory());
		
		
		bootstrap.setOption("tcpNoDelay" , true);
		bootstrap.setOption("keepAlive", true);
		ChannelFuture future = bootstrap.connect (new InetSocketAddress(host, port));
		
		//关闭连接
		future.awaitUninterruptibly();
		if (!future.isSuccess()) {
			future.getCause().printStackTrace();
		}
		future.getChannel().getCloseFuture().awaitUninterruptibly();
		factory.releaseExternalResources();
		

	}

}
