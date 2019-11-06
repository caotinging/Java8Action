package com.caotinging.java8action.chap5;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @program: Java8Action
 * @description: 数值流-构建勾股数
 * @author: CaoTing
 * @date: 2019/11/1
 */
public class NumberStream {

    public static void main(String[] args) {

        /*
         * 1.确定好a的值
         * 2.将a的值映射过去匹配生产的b值
         * 3.过滤符合的b值的条件为：a*a+b*b的平方根为整数
         * 4.剩下就是整合起来进行显示了
         */
        Stream<int[]> stream = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(
                        a -> IntStream.rangeClosed(a, 100)
                                .filter(b -> Math.sqrt(a * a + b * b) % 1 == 0)
                                .mapToObj(b -> new int[]{a, b, (int) Math.sqrt(a * a + b * b)})
                );

        /*
         * 1.确定好a的值和b的值
         * 2.根据a。b的值推导出符合条件的c的值
         * 3.判断是否符合条件
         */
        Stream<double[]> stream2 = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100)
                        .mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)})
                        .filter(t -> t[2] % 1 == 0)
                );

        stream.limit(5)
                .forEach(t -> System.out.println(t[0] + ", " + t[1] + ", " + t[2]));

        System.out.println("=====================================");

        stream2.limit(5)
                .forEach(t -> System.out.println((int)t[0] + ", " + (int)t[1] + ", " + (int)t[2]));
    }
}
