package com.caotinging.java8action.chap5;

import com.caotinging.java8action.chap4.Dish;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @program: Java8Action
 * @description: 流的归约-折叠计算
 * @author: CaoTing
 * @date: 2019/10/28
 */
public class StreamReduce {

    private static List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
        test1();
    }

    /**
     * 怎样用map和reduce方法数一数流中有多少个菜呢？
     */
    private static void test1() {
        List<Integer> list = menu.stream().map(dish -> 1).collect(Collectors.toList());
        System.out.println(list);

        Optional<Integer> sum = menu.stream().map(dish -> 1).reduce(Integer::sum);
        sum.ifPresent(sum1 -> System.out.println("菜总数: "+ sum1));

        // 内置count方法可用来计算流中元素个数
        long count = menu.stream().count();
        System.out.println("菜总数: "+count);
    }
}
