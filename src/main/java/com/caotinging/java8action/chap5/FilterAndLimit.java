package com.caotinging.java8action.chap5;

import com.caotinging.java8action.chap4.Dish;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: Java8Action
 * @description: 筛选和切片
 * @author: CaoTing
 * @date: 2019/10/10
 */
public class FilterAndLimit {

    private static List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
        System.out.println("筛选所有素菜");
        filterForVeg();
        System.out.println("=================");

        System.out.println("筛选各异的偶数");
        filterDistinct();
        System.out.println("=================");

        System.out.println("筛选能量高于300的前三道菜");
        filterForHighCalories();
        System.out.println("=================");

        System.out.println("跳过卡路里高于300的前两道菜返回剩余的其他菜");
        skipForHighCalories();
        System.out.println("=================");
    }

    // 跳过卡路里高于300的前两道菜返回剩余的其他菜
    private static void skipForHighCalories() {
        System.out.println(menu);

        List<Dish> collect = menu.stream().filter(dish -> dish.getCalories() > 300)
                .sorted(Comparator.comparing(Dish::getCalories, Comparator.reverseOrder()))
                .skip(2)
                .collect(Collectors.toList());

        System.out.println(collect);
    }

    /**
     * 筛选能量高于300的前三道菜
     *
     * Comparator.comparing(Dish::getCalories,Comparator.reverseOrder())
     * 为按卡路里降序排序，默认升序
     */
    private static void filterForHighCalories() {
        System.out.println(menu);

        List<Dish> highCalories = menu.stream()
                .filter(dish -> dish.getCalories() > 300)
                .sorted(Comparator.comparing(Dish::getCalories,Comparator.reverseOrder()))
                .limit(3)
                .collect(Collectors.toList());

        System.out.println(highCalories);
    }

    // 筛选偶数且各不相同
    private static void filterDistinct() {
        List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);
        System.out.println(numbers);
        List<Integer> collect = numbers.stream()
                .filter(integer -> integer % 2 == 0)
                .distinct().collect(Collectors.toList());
        System.out.println(collect);
    }

    // 筛选所有素菜
    private static void filterForVeg() {
        System.out.println(menu);

        List<Dish> vegList = menu.stream().filter(Dish::isVegetarian)
                .collect(Collectors.toList());

        System.out.println(vegList);
    }
}
