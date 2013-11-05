package com.zbt.netty.test;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;


/**
 * Caused by: java.lang.OutOfMemoryError: Direct buffer memory
	at java.nio.Bits.reserveMemory(Bits.java:633)
	
	
	询问了Netty的作者Trustin Lee：

	ChannelFactories are never meant to be created many times. 
	JVM is poor at managing direct buffers, so there’s no way to fix this problem without using JNI. 
	One possible workaround would be call System.gc() explicitly, but I’m not sure it’s a reliable workaround.
	
	看起来是JVM对direct buffer管理的问题，Netty估计也没有直接的办法解决，
	只能静态化ChannelFactory规避，以减少泄露：

	private static final NioClientSocketChannelFactory channelFactory = …;
	
	
	测试结果：
	Exception in thread "main" org.jboss.netty.channel.ChannelException: Failed to create a selector.
	at org.jboss.netty.channel.socket.nio.AbstractNioSelector.openSelector(AbstractNioSelector.java:337)
	
	Caused by: java.io.IOException: Unable to establish loopback connection
	at sun.nio.ch.PipeImpl$Initializer.run(Unknown Source)
	
	Caused by: java.net.SocketException: No buffer space available (maximum connections reached?): connect
	at sun.nio.ch.Net.connect(Native Method)
	
	评论：
	使用方式不对，　ChannelFactory本来就是个工厂，一个就够了

	另外，写client其实没必要用netty，　eda的style很多时候不适合，难用还是其次。
	嗯，赞同，ChannelFactory作为工厂，确实只需一个，但因为采用扩展点加载机制，
	有时候希望关闭ChannelFactory，比如需要动态修改工厂的一些设置，现在static掉实际上是不允许关闭。

	对于Netty的Client，因项目而异，如果是典型的单向C/S结构，在客户端采用事件驱动架构(EDA)确实不好，
	EDA只适合服务器端接收消息，但因Dubbo的Remoting是全双工的，不仅客户端可以向服务器端发请求，
	服务器端也可以向客户端发请求(基于长连接推送)，所以客户端用EDA风格可以使Client和Server行为一致。
	
	

	
 * @author zhangtao_jg
 *
 */
public class OutMemClient {
    public static void main(String[] args) throws Exception {
    	
    	
    	for (int i = 0; i < Integer.MAX_VALUE; i ++) {//
	    		ChannelFactory channelFactory = new NioClientSocketChannelFactory(
	    				Executors.newCachedThreadPool(), 
	    				Executors.newCachedThreadPool());
	    		ClientBootstrap bootstrap = new ClientBootstrap(channelFactory);
	    		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
	                public ChannelPipeline getPipeline() throws Exception {
	                    return Channels.pipeline();
	                }
	    		});
	    		//ChannelFuture future = bootstrap.connect(new InetSocketAddress("localhost",8080));
	    		ChannelFuture future = bootstrap.connect(new InetSocketAddress("10.1.7.182",12000));
	    		future.await(); // or future.addListener(new ChannelFutureListener() { … });
	    		Channel channel = future.getChannel();
	    		channel.close();
	    		// FIXME NioClientSocketChannelFactory direct buffer memory leak
	    		channelFactory.releaseExternalResources(); // or bootstrap.releaseExternalResources();
    		}
    	
    }

}
