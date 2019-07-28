package com.caotinging.java8action.chap1;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Java8Action
 * @description: 使用java7的实现方法筛选苹果
 * @author: CaoTing
 * @create: 2019/7/28
 */
public class FilterApples7 {

    public static void main(String[] args) {
        List<Apple> apples = Apple.getApples();
        System.out.println("绿苹果有：" + filterGreenApples(apples));
        System.out.println("重苹果有：" + filterHeavyApples(apples));
    }

    public static List<Apple> filterGreenApples(List<Apple> inventory){
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory){
            if ("green".equals(apple.getColor())) {
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterHeavyApples(List<Apple> inventory){
        List<Apple> result = new ArrayList<>();
        for (Apple apple: inventory){
            if ((apple.getWeight() > 150)) {
                result.add(apple);
            }
        }
        return result;
    }
}
