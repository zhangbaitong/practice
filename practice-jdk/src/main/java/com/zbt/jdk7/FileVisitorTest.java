package com.zbt.jdk7;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;


/**
 * preVisitDirectory - 一个路径被访问时调用
 * PostVisitDirectory - 一个路径的所有节点被访问后调用。如果有错误发生，exception会传递给这个方法
 * visitFile - 文件被访问时被调用。该文件的文件属性被传递给这个方法
 * visitFileFailed - 当文件不能被访问时，此方法被调用。Exception被传递给这个方法。
 * 
 * FileVisitResult有四种
 * CONTINUE –继续
 * TERMINATE –终止，这次遍历结束了
 * SKIP_SUBTREE –子树（当前路径的子目录）不再遍历了
 * SKIP_SIBLINGS –兄弟节点（同级别目录）不再访问了。

可以通过这些返回值来控制遍历文件树的流程
 * @author zhangbaitong
 *
 */
public class FileVisitorTest extends SimpleFileVisitor<Path> {

	private void find(Path path){  
        System.out.printf("访问-%s:%s%n",(Files.isDirectory(path)?"目录":"文件"),path.getFileName());  
    }  
    @Override  
    public FileVisitResult visitFile(Path file,BasicFileAttributes attrs){  
        find(file);  
        return FileVisitResult.CONTINUE;  
    }  
     
    @Override  
    public FileVisitResult preVisitDirectory(Path dir,BasicFileAttributes attrs){  
        find(dir);  
        return FileVisitResult.CONTINUE;  
    }  
      
    @Override  
    public FileVisitResult visitFileFailed(Path file,IOException e){  
        System.out.println(e);  
        return FileVisitResult.CONTINUE;  
    }  
      
    public static void main(String[] args) throws IOException{
		args = new String[1];
		args[0] = "/Users/zhangtao";
        if(args.length!=1){  
            System.out.println("请输入一个文件路径作为参数");  
            System.exit(-1);  
        }  
        Files.walkFileTree(Paths.get( args[0]), new FileVisitorTest());
        //遍历的时候跟踪链接  
//        EnumSet opts = EnumSet.of(FileVisitOption.FOLLOW_LINKS);  
//        try {  
//        	Files.walkFileTree(Paths.get( args[0]), opts, Integer.MAX_VALUE, new FileVisitorTest());  
//        } catch (IOException e) {  
//        	System.err.println(e);  
//        }  
    } 

}
