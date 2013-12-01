package com.zbt.concurrent.collections;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 好处：
 * 1. 读取快
 * 2. 写入快
 * 3. 随时随地写入
 * 
 * 对比：
 * bytebuffer
 * Read time :26ms
 * Write time :21ms
 * MappedByteBuffer
 * Read time :2ms
 * Write time :127ms
 * @author zbt
 *
 */
public class MappedByteBufferTest {

	public static void main(String[] args) throws Exception {
		int fileLength = 1024 * 14 * 1024;
		ByteBuffer byteBuf = ByteBuffer.allocate(fileLength);
        byte[] bbb = new byte[fileLength];
        FileInputStream fis = new FileInputStream("D:/Documents and Settings/zhangtao_jg/Desktop/test1g.txt");
        FileOutputStream fos = new FileOutputStream("D:/Documents and Settings/zhangtao_jg/Desktop/test-clone.txt");
        FileChannel fc = fis.getChannel();
       
        long timeStar = System.currentTimeMillis();//得到当前的时间

        //fc.read(byteBuf);//1 读取
        MappedByteBuffer mbb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fileLength);
        //mbb.force();//缓冲区是READ_WRITE模式下，缓冲区内容的修改强行写入文件
        //mbb.load();//将缓冲区的内容载入内存，并返回该缓冲区的引用
        //mbb.isLoaded();//缓冲区的内容是否在物理内存中

        long timeEnd = System.currentTimeMillis();//得到当前的时间

        System.out.println("Read time :" + (timeEnd - timeStar) + "ms");
        timeStar = System.currentTimeMillis();

        //fos.write(bbb);// 写入
        mbb.flip();

        timeEnd = System.currentTimeMillis();
        System.out.println("Write time :" + (timeEnd - timeStar) + "ms");
        fos.flush();
        fc.close();
        fis.close();
	}

}
