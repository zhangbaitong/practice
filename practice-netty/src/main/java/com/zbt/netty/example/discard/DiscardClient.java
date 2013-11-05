/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.zbt.netty.example.discard;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

/**
 * 保持发送任意数据到指定地址
 */
public class DiscardClient {

    private final String host;
    private final int port;
    private final int firstMessageSize;

    public DiscardClient(String host, int port, int firstMessageSize) {
        this.host = host;
        this.port = port;
        this.firstMessageSize = firstMessageSize;
    }

    public void run() {
        // 配置客户端
        ClientBootstrap bootstrap = new ClientBootstrap(
                new NioClientSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));

        // 设置pipeline工厂
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new DiscardClientHandler(firstMessageSize));
            }
        });

        // 开始尝试连接
        ChannelFuture future = bootstrap.connect(new InetSocketAddress(host, port));

        // 等待直到连接关闭或者连接尝试失败
        future.getChannel().getCloseFuture().awaitUninterruptibly();

        // 退出时关闭线程池
        bootstrap.releaseExternalResources();
    }

    public static void main(String[] args) throws Exception {
    	String[] args2 = {"localhost","8080","100"};
    	args = args2;
        // 如果没有指定参数，则打印用法
        if (args.length < 2 || args.length > 3) {
            System.err.println(
                    "Usage: " + DiscardClient.class.getSimpleName() +
                    " <host> <port> [<first message size>]");
            return;
        }

        // 分析选项
        final String host = args[0];
        final int port = Integer.parseInt(args[1]);
        final int firstMessageSize;
        if (args.length == 3) {
            firstMessageSize = Integer.parseInt(args[2]);
        } else {
            firstMessageSize = 256;
        }

        new DiscardClient(host, port, firstMessageSize).run();
    }
}
