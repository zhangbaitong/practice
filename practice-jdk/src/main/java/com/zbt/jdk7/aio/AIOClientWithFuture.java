package com.zbt.jdk7.aio;

import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.AsynchronousSocketChannel;  
import java.util.concurrent.ExecutionException;  
  
public class AIOClientWithFuture {  
    private final AsynchronousSocketChannel client;  
      
    public AIOClientWithFuture() throws IOException{  
        client = AsynchronousSocketChannel.open();  
    }  
      
    public void sendMsg() throws InterruptedException, ExecutionException{  
    	client.connect(new InetSocketAddress("localhost",8888));
    	Thread.sleep(1000);
        client.write(ByteBuffer.wrap("test".getBytes())).get();
    }  
    public static void main(String...args) throws Exception{  
        AIOClientWithFuture client = new AIOClientWithFuture();  
        client.sendMsg();
    }  
}  
