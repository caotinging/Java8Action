package com.caotinging.java8action.chap5;

import com.caotinging.java8action.chap4.Dish;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: Java8Action
 * @description: 流的映射
 * @author: CaoTing
 * @date: 2019/10/10
 */
public class StreamMap {

    private static List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
        System.out.println("获取菜单中所有菜的名字为新的集合");
        mapDishName();
        System.out.println("==========================");

        System.out.println("获取菜单中所有菜名的长度列表");
        mapNameLenth();
        System.out.println("==========================");
    }

    private static void mapNameLenth() {
        System.out.println(menu);
        List<Integer> collect = menu.stream().map(Dish::getName)
                .map(String::length)
                .collect(Collectors.toList());
        System.out.println(collect);
    }

    private static void mapDishName() {
        System.out.println(menu);
        List<String> dishNames = menu.stream().map(Dish::getName).collect(Collectors.toList());
        System.out.println(dishNames);
    }

}
