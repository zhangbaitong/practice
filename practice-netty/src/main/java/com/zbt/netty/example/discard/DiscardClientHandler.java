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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelState;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.channel.WriteCompletionEvent;

/**
 * 操作客户端的通道channel
 */
public class DiscardClientHandler extends SimpleChannelUpstreamHandler {

    private static final Logger logger = Logger.getLogger(
            DiscardClientHandler.class.getName());

    private long transferredBytes;
    private final byte[] content;

    public DiscardClientHandler(int messageSize) {
        if (messageSize <= 0) {
            throw new IllegalArgumentException(
                    "messageSize: " + messageSize);
        }
        content = new byte[messageSize];
    }

    public long getTransferredBytes() {
        return transferredBytes;
    }

    @Override
    public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
    	//如果是插入操作则进行打印
        if (e instanceof ChannelStateEvent) {
            if (((ChannelStateEvent) e).getState() != ChannelState.INTEREST_OPS) {
                logger.info(e.toString());
            }
        }

        // 让SimpleChannelHandler调用实际的事件处理方法
        super.handleUpstream(ctx, e);
    }

    @Override
    public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) {
        // 发送初始信息
        generateTraffic(e);
    }

    @Override
    public void channelInterestChanged(ChannelHandlerContext ctx, ChannelStateEvent e) {
        // Keep sending messages whenever the current socket buffer has room.
        generateTraffic(e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        // Server is supposed to send nothing.  Therefore, do nothing.
    }

    @Override
    public void writeComplete(ChannelHandlerContext ctx, WriteCompletionEvent e) {
        transferredBytes += e.getWrittenAmount();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
        // Close the connection when an exception is raised.
        logger.log(
                Level.WARNING,
                "Unexpected exception from downstream.",
                e.getCause());
        e.getChannel().close();
    }

    private void generateTraffic(ChannelStateEvent e) {
        // 保持交替生成知道channel不可写
        // 当内部buffer满了后channel就变得不可写
        // 如果你还继续写，那么将会OutOfMemoryError
        Channel channel = e.getChannel();
        while (channel.isWritable()) {
            ChannelBuffer m = nextMessage();
            if (m == null) {
                break;
            }
            channel.write(m);
        }
    }

    private ChannelBuffer nextMessage() {
        return ChannelBuffers.wrappedBuffer(content);
    }
}
