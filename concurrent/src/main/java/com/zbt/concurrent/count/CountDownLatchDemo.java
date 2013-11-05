package com.zbt.concurrent.count;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//beginSignal的count=0时，runner线程开始运行，直到endSignal的count=0时结束。
public class CountDownLatchDemo {

    private static int PLAYER_NUM = 10;
    
    public static void main(String[] args) throws InterruptedException {
        
        final CountDownLatch beginSignal = new CountDownLatch(1);
        final CountDownLatch endSignal = new CountDownLatch(PLAYER_NUM);
        
        ExecutorService executorService = Executors.newFixedThreadPool(PLAYER_NUM);
        
        System.out.println("before Game Start");
        for(int i=0;i<PLAYER_NUM;i++){
            final int num = i+1;
            Runnable runner = new Runnable(){

                public void run() {
                    // TODO Auto-generated method stub
                    try {
                    	System.out.println("No. "+num+" is waiting...");
                        beginSignal.await();
                        System.out.println("No. "+num+" begin running");
                        Thread.sleep((long) (Math.random() * 10000));
                        System.out.println("No." + num + " arrived");
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }finally{
                        endSignal.countDown();
                    }
                }
                
            };
            executorService.execute(runner);
        }
        Thread.sleep(3000);//给出准备时间
        System.out.println("Game Start");
        beginSignal.countDown();
        System.out.println("---In the middle of the game---");
        try {
            endSignal.await();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            System.out.println("Game Over!");
            executorService.shutdown();
        }

    }

}