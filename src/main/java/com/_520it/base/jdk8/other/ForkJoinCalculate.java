package com._520it.base.jdk8.other;

import java.util.concurrent.RecursiveTask;

public class ForkJoinCalculate extends RecursiveTask<Long> {


    private long start;
    private long end;

    private static final long THRESHOLD = 1000000000L; //临界值

    public ForkJoinCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length =end -start;
        if (length <= THRESHOLD){
            long sum = 0;
            for (int i = 0; i <= end; i++) {
                sum +=i;
            }
            return sum;
        }else {

            long middle = (start + end)/2;
            ForkJoinCalculate forkJoinCalculate = new ForkJoinCalculate(start, middle);
            //拆分将子任务压入线程队列中
            forkJoinCalculate.fork();

            ForkJoinCalculate forkJoinCalculate1 = new ForkJoinCalculate(middle + 1, end);
            forkJoinCalculate1.fork();

            return forkJoinCalculate.join() + forkJoinCalculate1.join();
        }
    }
}
