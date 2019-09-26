package com.caotinging.java8action.chap4;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: Java8Action
 * @description:
 * @author: CaoTing
 * @date: 2019/9/25
 */
public class StreamBasic {

    public static void main(String[] args) {
        System.out.println("==============使用java7的方式===============");
        long startTime1 = System.currentTimeMillis();

        System.out.println(getLowCaloricDishesNamesInJava7());

        long endTime1 = System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime1-startTime1)+"ms");

        System.out.println("==============使用java8的方式===============");
        long startTime2 = System.currentTimeMillis();

        System.out.println(getLowCaloricDishesNamesInJava8());

        long endTime2 = System.currentTimeMillis();
        System.out.println("程序运行时间： "+(endTime2-startTime2)+"ms");
    }

    /**
     * 使用java7 获取菜单中热量小于等于400的前三个
     * @return
     */
    static List<String> getLowCaloricDishesNamesInJava7() {

        List<Dish> dishes = new ArrayList<>();
        for (Dish dish : Dish.menu) {
            if (dish.getCalories() <= 400) {
                dishes.add(dish);
            }
        }
        
        // 排序
        dishes.sort(new Comparator<Dish>() {
            @Override
            public int compare(Dish o1, Dish o2) {
                return Integer.compare(o1.getCalories(), o2.getCalories());
            }
        });

        List<String> names = new ArrayList<>();
        // 获取前三个的菜肴的名字
        for (int i = 0; i < dishes.size(); i++) {
            if (i == 3) break;
            names.add(dishes.get(i).getName());
        }
        return names;
    }

    /**
     * 使用java8 获取菜单中热量小于等于400的前三个
     * @return
     */
    static List<String> getLowCaloricDishesNamesInJava8() {
        return Dish.menu.parallelStream()
                .filter(dish -> dish.getCalories() <= 400)
                .sorted(Comparator.comparingInt(Dish::getCalories))
                .limit(3)
                .map(Dish::getName)
                .collect(Collectors.toList());
    }
}
