package com.zbt.jdk7.aio;

import java.net.InetSocketAddress;  
import java.nio.ByteBuffer;  
import java.nio.channels.AsynchronousSocketChannel;  
import java.nio.channels.CompletionHandler;  
  
public class AIOClientWithHandler {  
    private final AsynchronousSocketChannel client ;  
      
    public AIOClientWithHandler() throws Exception{  
       client = AsynchronousSocketChannel.open();  
    }  
      
    public void start()throws Exception{  
        client.connect(new InetSocketAddress("127.0.0.1",8888),null,new CompletionHandler<Void,Void>() {  
            @Override  
            public void completed(Void result, Void attachment) {  
                try {
                    client.write(ByteBuffer.wrap("test".getBytes())).get();  
                } catch (Exception ex) {  
                    ex.printStackTrace();  
                }  
            }  
  
            @Override  
            public void failed(Throwable exc, Void attachment) {  
                exc.printStackTrace();  
            }  
        });  
    }  
      
    public static void main(String args[])throws Exception{  
        new AIOClientWithHandler().start();
        Thread.sleep(2000);
    }  
}  
