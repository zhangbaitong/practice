package com.zbt.concurrent.count;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestCountDownLatch {
	 
    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();
        
        CountDownLatch latch = new CountDownLatch(3);
        
        Student s1 = new Student(101, latch);
        Student s2 = new Student(102, latch);
        Student s3 = new Student(103, latch);
        Teacher t = new Teacher(latch);
        
        executor.execute(t);
        executor.execute(s1);
        executor.execute(s2);
        executor.execute(s3);
        
        executor.shutdown();
        
    }

}
