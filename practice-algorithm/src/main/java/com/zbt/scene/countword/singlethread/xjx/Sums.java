package com.zbt.scene.countword.singlethread.xjx;


import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

import static java.util.Arrays.asList;

public class Sums {
    
    public static void main(String[] args) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        List<Future<Long>> results = executor.invokeAll(asList(
            new Sum(0, 100), new Sum(101, 1000), new Sum(1001, 10000), new Sum(10001, 1000000)
        ));
        executor.shutdown();

        long sum = 0;
        for (Future<Long> result : results) {
            sum += result.get();
        }
        System.out.println(sum);
    }
}

class Sum implements Callable<Long> {
    private final long from;
    private final long to;
    
    Sum(long from, long to) {
        this.from = from;
        this.to = to;
    }
    
    public Long call() {
        long acc = 0;
        for (long i = from; i <= to; i++) {
            acc = acc + i;
        }
        return acc;
    }                
}
