package com.zbt.jdk7;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

import static java.nio.file.StandardWatchEventKinds.*;

import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;


public class NioWatchServiceTest {
	
	private WatchService watcher;
	
	public NioWatchServiceTest(Path path)throws IOException{  
        watcher = FileSystems.getDefault().newWatchService();  
        path.register(watcher, ENTRY_CREATE,ENTRY_DELETE,ENTRY_MODIFY);  
    }
	
	public void handleEvents() throws InterruptedException{  
        while(true){  
            WatchKey key = watcher.take();  
            for(WatchEvent<?> event : key.pollEvents()){  
                WatchEvent.Kind kind = event.kind();  
                  
                if(kind == OVERFLOW){//事件可能lost or discarded  
                    continue;  
                }  
                  
                WatchEvent<Path> e = (WatchEvent<Path>)event;  
                Path fileName = e.context();  
                  
                System.out.printf("Event %s has happened,which fileName is %s%n"  
                        ,kind.name(),fileName);  
            }  
            if(!key.reset()){  
                break;  
            }  
        }  
    }  

	public static void main(String args[]) throws IOException, InterruptedException{
		args = new String[1];
		args[0] = "/Users/xxx/test";
        if(args.length!=1){  
            System.out.println("请设置要监听的文件目录作为参数");  
            System.exit(-1);  
        }  
        new NioWatchServiceTest(Paths.get(args[0])).handleEvents();  
    } 

}
