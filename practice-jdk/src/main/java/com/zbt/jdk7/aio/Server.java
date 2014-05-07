package com.zbt.jdk7.aio;

import java.io.IOException;  
import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.AsynchronousServerSocketChannel;  
import java.nio.channels.AsynchronousSocketChannel;  
import java.util.concurrent.ExecutionException;  
import java.util.concurrent.Future;  
import java.util.concurrent.TimeUnit;  
import java.util.concurrent.TimeoutException;  
  
public class Server {  
    private AsynchronousServerSocketChannel server;  
      
    public Server()throws IOException{  
        server = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(8888));  
    }  
      
    public void start() throws InterruptedException, ExecutionException, TimeoutException{
    	System.out.println("server:before accept");
    	
        Future<AsynchronousSocketChannel> future = server.accept();  
        System.out.println("server:after accept");
        
        System.out.println("server:before future.get");
        
        AsynchronousSocketChannel socket = future.get();  
        System.out.println("server:after future.get");
         
        ByteBuffer readBuf = ByteBuffer.allocate(1024);  
        socket.read(readBuf).get(100, TimeUnit.SECONDS);  
          
        System.out.printf("Receiver:%s%n",new String(readBuf.array()));  
    }  
      
    public static void main(String args[]) throws Exception{
    	Server server = new Server();
    	System.out.println("server start------");
    	while(true){
    		System.out.println("do it =========");
    		server.start();  
    	}
    }  
}  