package com.caotinging.java8action.chap7;

import java.util.stream.LongStream;

/**
 * @program: Java8Action
 * @description: 优化后的并行流性能比较
 * @author: CaoTing
 * @create: 2019/12/22
 */
public class BetterParallelStreams {

    /**
     * 顺序流
     * @Author: CaoTing
     * @Date: 2019/12/22
     */
    public static long rangedSum(long n) {
        return LongStream.rangeClosed(1L,n)
                .reduce(0L,Long::sum);
    }

    public static long parallelRangedSum(long n) {
        return LongStream.rangeClosed(1L,n)
                .parallel()
                .sum();
    }

}
