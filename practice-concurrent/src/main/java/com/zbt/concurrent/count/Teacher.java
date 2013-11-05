package com.zbt.concurrent.count;

import java.util.concurrent.CountDownLatch;

public class Teacher implements Runnable{
    
    private CountDownLatch cdlatch;
    
    Teacher(CountDownLatch latch){
        this.cdlatch = latch;
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        try {
            System.out.println("teacher is waiting...");
            cdlatch.await();
            System.out.println("teacher is collecting......");
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}