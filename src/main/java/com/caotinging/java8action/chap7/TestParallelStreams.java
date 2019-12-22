package com.caotinging.java8action.chap7;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.function.Function;
import java.util.stream.LongStream;

/**
 * @program: Java8Action
 * @description: 测试流性能框架
 * @author: CaoTing
 * @create: 2019/12/22
 */
public class TestParallelStreams {

    public static void main(String[] args) {
        System.out.println("forkJoinSum sum done in:" +
                measureSumPerf(TestParallelStreams::forkJoinSum, 100_000_000) + " msecs");

        /*
         * forkJoinSum sum done in:373 msecs
         */
    }

    public static long forkJoinSum(long n) {
        long[] numbers = LongStream.rangeClosed(1, n).toArray();
        ForkJoinTask<Long> task = new ForkJoinSumCalculator(numbers);
        return new ForkJoinPool().invoke(task);
    }

    public static void main2(String[] args) {
        System.out.println("Parallel range sum done in:" +
                measureSumPerf(BetterParallelStreams::parallelRangedSum, 100_000_000) + " msecs");

        System.out.println("Sequential sum done in:" +
                measureSumPerf(BetterParallelStreams::rangedSum, 100_000_000) + " msecs");

        /*
         * Parallel range sum done in:7 msecs
         * Sequential sum done in:49 msecs
         */
    }

    public static void main1(String[] args) {
        System.out.println("Sequential sum done in:" +
                measureSumPerf(ParallelStreams::sequentialSum, 100_000_000) + " msecs");

        System.out.println("Iterative sum done in:" +
                measureSumPerf(ParallelStreams::iterativeSum, 100_000_000) + " msecs");

        System.out.println("Parallel sum done in: " +
                measureSumPerf(ParallelStreams::parallelSum, 100_000_000) + " msecs" );

        /*
         * Sequential sum done in:746 msecs
         * Iterative sum done in:26 msecs
         * Parallel sum done in: 5371 msecs
         */
    }

    private static long measureSumPerf(Function<Long, Long> adder, long n) {
        long fastest = Long.MAX_VALUE;
        for (int i = 0; i < 10; i++) {

            long start = System.nanoTime();
            long sum = adder.apply(n);

            long duration = (System.nanoTime() - start) / 1_000_000;
            //System.out.println("Result: " + sum);
            if (duration < fastest) fastest = duration;
        }
        return fastest;
    }
}
