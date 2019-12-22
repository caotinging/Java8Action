package com.caotinging.java8action.chap7;

import java.util.stream.Stream;

/**
 * @program: Java8Action
 * @description: 测试比较并行流性能
 * @author: CaoTing
 * @create: 2019/12/22
 */
public class ParallelStreams {

    /**
     * 利用顺序流实现获取累加值
     * @Author: CaoTing
     * @Date: 2019/12/22
     */
    public static long sequentialSum(long n) {
        return Stream.iterate(1L,i->i+1)
                .limit(n)
                .reduce(0L,Long::sum);
    }

    /**
     * 使用传统迭代方式实现
     * @Author: CaoTing
     * @Date: 2019/12/22
     */
    public static long iterativeSum(long n) {
        long sum = 0L;
        for (long i = 0; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    /**
     * 使用并行流实现
     * @Author: CaoTing
     * @Date: 2019/12/22
     */
    public static long parallelSum(long n) {
        return Stream.iterate(1L, i->i+1)
                .limit(n)
                .parallel()
                .reduce(0L,Long::sum);

    }
}
