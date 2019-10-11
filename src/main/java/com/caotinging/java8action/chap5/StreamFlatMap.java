package com.caotinging.java8action.chap5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @program: Java8Action
 * @description: 流的扁平化
 * @author: CaoTing
 * @date: 2019/10/11
 */
public class StreamFlatMap {



    public static void main(String[] args) {
        test1();
    }

    /**
     *  给定两个数字列表，如何返回所有的数对呢？
     *  例如，给定列表[1, 2, 3]和列表[3, 4]，
     *  应该返回[(1, 3), (1, 4), (2, 3), (2, 4), (3, 3), (3, 4)]。
     *  为简单起见，你可以用有两个元素的数组来代表数对。
     */
    private static void test3() {
        int[] intArr1 = new int[]{1,2,3};
        int[] intArr2 = new int[]{3,4};


    }

    /**
     * 对于一张单词表，如何返回一张列表，列出里面各不相同的字符呢？
     * 例如，给定单词列表
     * ["Hello","World"]
     * 期待返回列表
     * ["H","e","l", "o","W","r","d"]
     */
    private static void test2 () {
        String[] helloArr = new String[]{"hello", "world"};
        Stream<String> stream = Arrays.stream(helloArr);
        System.out.println("== 对于一张单词表，如何返回一张列表，列出里面各不相同的字符呢？");

        // string 数组 实现
        List<String> collect = Arrays.stream(helloArr)
                .map(arr -> arr.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(collect+"==");

        // List实现
        List<String> words = new ArrayList<>();
        words.add("hello");
        words.add("world");

        List<String> collect1 = words.stream()
                .map(word -> word.split(""))
                .flatMap(Arrays::stream)
                .distinct()
                .collect(Collectors.toList());

        System.out.println(collect1+"==");
    }

    /**
     *  给定一个数字列表，如何返回一个由每个数的平方构成的数据列表呢？
     *  例如，给定[1, 2, 3, 4, 5]
     *  应该返回[1, 4, 9, 16, 25]。
     */
    private static void test1() {
        int[] numberArr = new int[]{1,2,3,4,5};

        List<Integer> collect = Arrays.stream(numberArr)
                .map(number -> number * number)
                .boxed() // 将IntStream变成Stream<Integer>然后才能执行toList
                .collect(Collectors.toList());

        System.out.println(collect+"==");

        int[] results = IntStream.rangeClosed(1, 5)
                .map(number -> number * number)
                .toArray();

        System.out.println(Arrays.toString(results) +"results");
    }

}
