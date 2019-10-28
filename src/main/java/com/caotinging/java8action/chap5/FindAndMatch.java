package com.caotinging.java8action.chap5;

import com.caotinging.java8action.chap4.Dish;

import java.util.List;
import java.util.Optional;

/**
 * @program: Java8Action
 * @description: 查找和匹配
 * @author: CaoTing
 * @date: 2019/10/28
 */
public class FindAndMatch {

    private static List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
        testAnyMatch();
        testAllMatch();
        testNoneMatch();

        // 使用并行流来展示两者的区别
        testFindAny();
        testFindFirst();
    }

    private static void testFindFirst() {
        Optional<Dish> dish = menu.parallelStream().filter(Dish::isVegetarian).findFirst();
        dish.ifPresent(dish1 -> System.out.println("菜单里第一道素菜："+dish1.getName()));
    }

    /**
     * 找到菜单中的任一素菜
     */
    private static void testFindAny() {
        Optional<Dish> dish = menu.parallelStream().filter(Dish::isVegetarian).findAny();
        dish.ifPresent(dish1 -> System.out.println("菜单里任意一道素菜："+dish1.getName()));
    }

    /**
     * 检查菜单中是否有素菜
     */
    private static void testAnyMatch() {
        if(menu.stream().anyMatch(Dish::isVegetarian)) {
            System.out.println("菜单中有素菜");
        }
    }

    private static void testAllMatch() {
        if (menu.stream().allMatch(c -> c.getCalories() < 700)) {
            System.out.println("菜单中所有菜的卡路里都低于700");
        }
    }

    private static void testNoneMatch() {
        if (menu.stream().noneMatch( c -> c.getCalories() >= 1000)) {
            System.out.println("菜单里没有菜的卡路里高于1000");
        }
    }
}
