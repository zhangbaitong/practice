package com.zbt.jdk7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Fibonacci extends RecursiveTask<Integer> {  
    final int n;  
  
    Fibonacci(int n) {  
        this.n = n;  
    }  
  
    private int compute(int small) {  
        final int[] results = { 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89 };  
        return results[small];  
    }  
  
    public Integer compute() {  
        if (n <= 10) {  
            return compute(n);  
        }  
        Fibonacci f1 = new Fibonacci(n - 1);  
        Fibonacci f2 = new Fibonacci(n - 2);
        //invokeAll(f1,f2);
        System.out.println("fork new thread for " + (n - 1));  
        f1.fork();  
        System.out.println("fork new thread for " + (n - 2));  
        f2.fork();  
        Integer result = f1.join() + f2.join();
        System.out.println("result is " + result);
        return result;
    }
    public static void main(String[] args) {
    	ForkJoinPool fjPool = new ForkJoinPool(10);  
        fjPool.invoke(new Fibonacci(11));  
	}
}  