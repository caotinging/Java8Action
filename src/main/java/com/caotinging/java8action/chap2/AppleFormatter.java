package com.caotinging.java8action.chap2;

import com.caotinging.java8action.chap1.Apple;

import java.util.List;

/**
 * @program: Java8Action
 * @description: 自定义输出苹果集合数据的格式--(行为参数化）
 * @author: CaoTing
 * @create: 2019/7/28
 */
public class AppleFormatter {

    public static void main(String[] args) {
        // 输出苹果的重量
        prettyPrintApple(Apple.getApples(), apple -> "An apple is " + apple.getWeight());
        // 输出苹果的颜色
        prettyPrintApple(Apple.getApples(), apple -> "An apple is " + apple.getColor());
    }

    public static void prettyPrintApple(List<Apple> apples, AppleAbstactFormatter formatter) {
        for (Apple apple : apples) {
            System.out.println(formatter.accept(apple));
        }
    }
}

interface AppleAbstactFormatter {
    String accept(Apple apple);
}