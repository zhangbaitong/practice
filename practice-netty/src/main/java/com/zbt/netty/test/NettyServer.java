package com.zbt.netty.test;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.zbt.netty.example.discard.DiscardServer;

public class NettyServer {

	 private final int port;

	    public NettyServer(int port) {
	        this.port = port;
	    }

	    public void run() {
	        // Configure the server.
	        ServerBootstrap bootstrap = new ServerBootstrap(
	                new NioServerSocketChannelFactory(
	                        Executors.newCachedThreadPool(),
	                        Executors.newCachedThreadPool()));

	        // Set up the pipeline factory.
	        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
	            public ChannelPipeline getPipeline() throws Exception {
	                return Channels.pipeline(new DiscardServerHandler());
	            }
	        });

	        // Bind and start to accept incoming connections.
	        bootstrap.bind(new InetSocketAddress(port));
	    }

	    public static void main(String[] args) throws Exception {
	        int port;
	        if (args.length > 0) {
	            port = Integer.parseInt(args[0]);
	        } else {
	            port = 8080;
	        }
	        new DiscardServer(port).run();
	    }

}
