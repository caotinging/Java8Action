package com.caotinging.java8action.chap5;

import java.util.stream.Stream;

/**
 * @program: Java8Action
 * @description: 生成无限流
 * @author: CaoTing
 * @date: 2019/11/6
 */
public class UnlimitedStream {

    public static void main(String[] args) {
        test1();
    }

    /**
     * 数列中数字和其后面数字组成的元组构成的序列：
     * (0, 1), (1, 1), (1, 2), (2, 3), (3, 5), (5, 8), (8, 13), (13, 21) …
     * 方法生成斐波那契元组序列中的前20个元素。
     */
    public static void test1() {
        // 斐波那契元组序列
        Stream.iterate(new int[]{0,1}, array->new int[]{array[1],array[0]+array[1]})
                .limit(20)
                .forEach((t -> System.out.println("(" + t[0] + "," + t[1] +")")));

        // 这串元组序列的第一个元素即为斐波那契序列
        Stream.iterate(new int[]{0,1}, array->new int[]{array[1],array[0]+array[1]})
                .limit(10)
                .map(t->t[0])
                .forEach(System.out::println);
    }
}
