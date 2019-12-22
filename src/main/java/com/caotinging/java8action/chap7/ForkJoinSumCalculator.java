package com.caotinging.java8action.chap7;

import java.util.concurrent.RecursiveTask;

/**
 * @program: Java8Action
 * @description:
 * @author: CaoTing
 * @create: 2019/12/22
 */
public class ForkJoinSumCalculator extends RecursiveTask<Long> {

    private final long[] numbers;
    private final int start;
    private final int end;

    private static final long THRESHOLD = 10_000;

    private ForkJoinSumCalculator(long[] numbers, int start, int end) {
        this.numbers = numbers;
        this.start = start;
        this.end = end;
    }

    public ForkJoinSumCalculator(long[] numbers) {
        this(numbers,0,numbers.length);
    }

    /**
     * Fork 就是把一个大任务切分为若干子任务并行的执行，
     * Join 就是合并这些子任务的执行结果，最后得到这个大任务的结果
     * @Author: CaoTing
     * @Date: 2019/12/22
     */
    @Override
    protected Long compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            return computeSequentially();
        }

        ForkJoinSumCalculator leftTask =
                new ForkJoinSumCalculator(numbers, start, start + length/2);

        // 使用另一个ForkJoinPool线程异步执行新创建的子任务
        leftTask.fork();

        ForkJoinSumCalculator rightTask =
                new ForkJoinSumCalculator(numbers, start + length/2, end);

        // 继续在这个线程递归调用compute方法，可能会在上一步fork时再次分支
        Long rightResult = rightTask.compute();

        // 读取另一半子任务的结果，如果未完成会继续等待
        Long leftResult = leftTask.join();
        return leftResult+rightResult;
    }

    /**
     * 在子任务不再分裂时调用的计算方法
     * @Author: CaoTing
     * @Date: 2019/12/22
     */
    private Long computeSequentially() {
        long sum = 0;
        for (int i = start; i< end; i++) {
            sum += numbers[i];
        }
        return sum;
    }
}
