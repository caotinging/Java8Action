package com.caotinging.java8action.chap6;

import com.caotinging.java8action.Dish;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @program: Java8Action
 * @description: 分组
 * @author: CaoTing
 * @date: 2019/12/12
 */
public class StreamGroup {

    private static final List<Dish> menu = Dish.menu;

    public static void main(String[] args) {
        //testGroupByType();
        //testGroupByCaloric();
        //testMultiGroup();
        //testCollectGroup();
        testGroupMapping();
    }

    /**
     * 对分类后的子组中的数据进行映射
     *
     * 这里将子组中的数据映射成热量等级显示
     */
    private static void testGroupMapping() {
        Map<Dish.Type, Set<CaloricLevel>> result = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.mapping(
                                dish -> {
                                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                                    else return CaloricLevel.FAT;
                                }, Collectors.toSet()
                        )));
        System.out.println(result);
    }

    /**
     * 按子组收集数据
     *
     * 对分类好的子组进行收集操作
     *
     * <p>
     *     result1：对菜单里的菜按照类型进行分类，然后统计每种类型下的总热量
     *     result2：对菜按照类型分类，然后分别统计每一类的数量
     * </p>
     */
    private static void testCollectGroup() {
        Map<Dish.Type, Integer> result1 = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.summingInt(Dish::getCalories)));

        System.out.println(result1);

        Map<Dish.Type, Long> result2 = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.counting()));

        System.out.println(result2);
    }

    /**
     * 多级分组
     *
     * 按菜的类型进行分类，并对每种类型下的菜按照能量进行分类。映射成菜名进行输出显示
     */
    private static void testMultiGroup() {
        Map<Dish.Type, Map<CaloricLevel, Set<String>>> result = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType,
                        Collectors.groupingBy(
                                dish -> {
                                    if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                                    else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                                    else return CaloricLevel.FAT;
                                }, Collectors.mapping(Dish::getName, Collectors.toSet())
                        )));

        System.out.println(result);
    }

    /**
     * 根据菜肴的能量分类
     */
    private static void testGroupByCaloric() {
        Map<CaloricLevel, Set<String>> result = menu.stream()
                .collect(Collectors.groupingBy(
                        dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }
                        , Collectors.mapping(Dish::getName, Collectors.toSet())));

        System.out.println(result);
    }

    /**
     * 测试按照菜单类型分类
     */
    private static void testGroupByType() {

        Map<Dish.Type, List<Dish>> preResult = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType));

        System.out.println(preResult);

        Map<Dish.Type, Set<String>> result = menu.stream()
                .collect(Collectors.groupingBy(Dish::getType, Collectors.mapping(Dish::getName, Collectors.toSet())));

        System.out.println(result);
    }

    public enum CaloricLevel { DIET, NORMAL, FAT }
}
