package com.caotinging.java8action.chap6;

import com.caotinging.java8action.Dish;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @program: Java8Action
 * @description: 用流收集数据-收集器
 * @author: CaoTing
 * @create: 2019/11/18
 */
public class StreamCollect {

    public static final List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
        //test2();
        //test3();
        //testReduce();
        testReplaceJoining();
    }

    /**
     * 哪些reducing收集器语句可以替代：
     * String shortMenu = menu.stream().map(Dish::getName).collect(joining());
     */
    private static void testReplaceJoining() {

        String result1 = menu.stream()
                .map(Dish::getName)
                .reduce((m1, m2) -> m1 +"," + m2)
                .get();

        System.out.println(result1);

        String result = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining(","));

        System.out.println(result);

        String result2 = menu.stream()
                .collect(Collectors.reducing("", Dish::getName, (d1, d2) -> d1 + "," + d2));

        System.out.println(result2);
    }

    /**
     * reducing(初始值,转换函数,归约函数)
     * 一个参数的版本为：reducing(自定义归约函数) 初始值=第一项的值 转换函数=恒等函数
     * @Author: CaoTing
     * @Date: 2019/12/1
     */
    private static void testReduce() {
        // 总热量
        Integer sumCal = menu.stream()
                .collect(Collectors.reducing(0, Dish::getCalories, (a, b) -> a + b));

        System.out.println(sumCal);

        // 最高热量
        Optional<Dish> maxCal = menu.stream()
                .collect(Collectors.reducing((a, b) -> a.getCalories() > b.getCalories() ? a : b));

        System.out.println(maxCal);
    }

    /**
     * 连接字符串
     * @Author: CaoTing
     * @Date: 2019/12/1
     */
    private static void test3() {
        String names = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining());

        System.out.println(names);

        String names1 = menu.stream()
                .map(Dish::getName)
                .collect(Collectors.joining(","));

        System.out.println(names1);
    }

    /**
     * 数一数菜单里有多少种菜
     * @Author: CaoTing
     * @Date: 2019/11/18
     */
    public static void test1() {
        long count = menu.stream().count();
        Long count1 = menu.stream().collect(Collectors.counting());

        System.out.println(count);
        System.out.println(count1);
    }

    /**
     * 查找流中的最大值最小值
     * @Author: CaoTing
     * @Date: 2019/12/1
     */
    public static void test2() {
        // 获取热量最高的菜
        Optional<Dish> maxCalDish = menu.stream()
                .collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)));

        System.out.println(maxCalDish);

        Optional<Integer> menuMaxCal = menu.stream()
                .map(Dish::getCalories)
                .reduce(Integer::max);

        System.out.println(menuMaxCal);

        // 获取菜单所有菜的总热量
        Integer allCal = menu.stream().mapToInt(Dish::getCalories).sum();

        System.out.println(allCal);

        Integer allCal1 = menu.stream()
                .collect(Collectors.summingInt(Dish::getCalories));

        System.out.println(allCal1);

        // 获取菜单中菜的平均热量
        Double aveCal = menu.stream()
                .collect(Collectors.averagingInt(Dish::getCalories));

        System.out.println(aveCal);

        // 一个操作就数出菜单中元素的个数，并得到热量总和、平均值、最大值和最小值：

        IntSummaryStatistics collect = menu.stream()
                .collect(Collectors.summarizingInt(Dish::getCalories));

        collect.getAverage();

        System.out.println(collect);

    }
}
