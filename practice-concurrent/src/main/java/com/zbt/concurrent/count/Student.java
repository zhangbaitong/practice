package com.zbt.concurrent.count;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Student implements Runnable {
    
    private int num;
    private CountDownLatch cdlatch;
    
    Student(int num,CountDownLatch latch){
        this.num = num;
        this.cdlatch = latch;
    }

    @Override
    public void run() {
        doExam();
        try {
            TimeUnit.SECONDS.sleep(new Random().nextInt(10));
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Student "+num+" finished!");
        cdlatch.countDown();
        System.out.println("1111111111111111 "+num+" finished!");
    }
    
    private void doExam(){
        System.out.println("Student "+num+" is doing the exam!");
    }
    
}
