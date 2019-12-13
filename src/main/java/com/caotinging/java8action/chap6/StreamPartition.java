package com.caotinging.java8action.chap6;

import com.caotinging.java8action.Dish;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @program: Java8Action
 * @description: 分区
 * @author: CaoTing
 * @date: 2019/12/13
 */
public class StreamPartition {

    private static final List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
        //testPartVeg();
        //testPartAndGroup();
        //testPartAndMaxCal();
        //testMultiPart();
        //testCountPart();
        testIfPrime(10);
    }

    /**
     * 将到指定参数为止范围的数字按质数和非质数区分
     * @param n
     */
    private static void testIfPrime(int n) {
        Map<Boolean, List<Integer>> result = IntStream.rangeClosed(2, n).boxed()
                .collect(Collectors.partitioningBy(StreamPartition::isPrime));
        System.out.println(result);
    }

    /**
     * 检查n是否为质数 rangeClosed：包含结束值
     * @param n
     * @return
     */
    private static boolean isPrime(int n) {
        int candidateRoot = (int) Math.sqrt((double) n);
        return IntStream.rangeClosed(2,candidateRoot)
                .noneMatch(i -> candidateRoot % 2 == 0);
    }

    /**
     * 检查n是否为质数，性能稍微差点 range:不包括结束值（即n本身
     * @param n
     * @return
     */
    private static boolean isTwoPrime(int n) {
        return IntStream.range(2,n)
                .noneMatch(i -> n % i == 0);
    }

    /**
     * 获取荤菜和素菜的数量
     */
    private static void testCountPart() {
        Map<Boolean, Long> result = menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian,
                        Collectors.counting()));
        System.out.println(result);
    }

    /**
     * 实现多级分区
     *
     * 获取荤菜中热量高于500和低于500的两部分菜肴
     */
    private static void testMultiPart() {

        Map<Boolean, Map<Boolean, List<Dish>>> result = menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian,
                        Collectors.partitioningBy(dish -> dish.getCalories() > 500)));

        System.out.println(result);

        Map<Boolean, Map<Boolean, Set<String>>> result1 = menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian,
                        Collectors.partitioningBy(dish -> dish.getCalories() > 500,
                                Collectors.mapping(Dish::getName, Collectors.toSet()))));

        System.out.println(result1);

    }

    /**
     * 获取分区中素菜和荤菜中热量最高的菜
     */
    private static void testPartAndMaxCal() {
        Map<Boolean, Dish> result = menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)),
                                Optional::get)));

        System.out.println(result);

        System.out.println("荤菜中热量最高的是:"+result.get(false));
    }

    /**
     * 对分区后的子组再分类
     *
     * 获取荤菜，并按类型分好类
     */
    private static void testPartAndGroup() {

        Map<Boolean, Map<Dish.Type, Set<String>>> result1 = menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian,
                        Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toSet()))));

        System.out.println(result1);

        Set<String> fishNames = result1.get(false).get(Dish.Type.FISH);
        System.out.println("荤菜中鱼类菜有："+fishNames);

    }

    /**
     * 将菜单中的菜肴分为素食和非素食两部分
     */
    private static void testPartVeg() {

        Map<Boolean, Set<String>> result = menu.stream()
                .collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.mapping(Dish::getName, Collectors.toSet())));

        System.out.println(result);

        Set<String> vegNames = result.get(true);
        System.out.println("素菜有:"+vegNames);

        Set<String> notVegNames = result.get(false);
        System.out.println("荤菜有:"+notVegNames);

        Set<String> vegDish = menu.stream().filter(Dish::isVegetarian).map(Dish::getName).collect(Collectors.toSet());
        System.out.println("素菜有:"+vegDish);
    }

}
