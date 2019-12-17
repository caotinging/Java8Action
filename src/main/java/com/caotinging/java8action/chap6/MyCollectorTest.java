package com.caotinging.java8action.chap6;

import com.caotinging.java8action.Dish;

import java.util.List;

/**
 * @program: Java8Action
 * @description:
 * @author: CaoTing
 * @date: 2019/12/17
 */
public class MyCollectorTest {

    private static final List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
        List<String> result = menu.stream()
                .map(Dish::getName)
                .collect(new MyToListCollector<>());

        System.out.println(result);
    }

}
