package com.caotinging.java8action.chap6;

import com.caotinging.java8action.Dish;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @program: Java8Action
 * @description:
 * @author: CaoTing
 * @date: 2019/12/17
 */
public class MyCollectorTest {

    private static final List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
        System.out.println("==========测试toList=============");
        List<String> result = menu.stream()
                .map(Dish::getName)
                .collect(new MyToListCollector<>());

        System.out.println(result);
        System.out.println("=================================");


        System.out.println("测试质数收集器");
        Map<Boolean, List<Integer>> resultMap = IntStream.range(2, 100)
                .boxed()
                .collect(new PrimeCollector());

        System.out.println(resultMap);
    }

}
