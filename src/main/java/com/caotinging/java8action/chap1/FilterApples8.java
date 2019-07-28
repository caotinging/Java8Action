package com.caotinging.java8action.chap1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @program: Java8Action
 * @description: 使用lambda实现筛选苹果
 * @author: CaoTing
 * @create: 2019/7/28
 */
public class FilterApples8 {

    public static void main(String[] args) {
        List<Apple> apples = Apple.getApples();
        System.out.println("绿苹果有：" + filterApples(apples, (Apple a) -> "green".equals(a.getColor())));
        System.out.println("重苹果有：" + filterApples(apples, (Apple a) -> a.getWeight() > 150));

        System.out.println("重的绿苹果：" + apples.stream().filter(a -> a.getWeight() > 150).collect(Collectors.toList()));
    }

    public static List<Apple> filterApples(List<Apple> apples, Predicate<Apple> p) {
        List<Apple> result = new ArrayList<>();
        for (Apple apple: apples){
            if (p.test(apple)) {
                result.add(apple);
            }
        }
        return result;
    }
}
