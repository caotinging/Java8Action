package com.caotinging.java8action.chap3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @program: Java8Action
 * @description: lambda随机排序策略
 * @author: CaoTing
 * @date: 2019/9/17
 */
public class Sorting {

    public static void main(String[] args) {

        // 初始化一组测试数据
        List<Apple> inventory1 = new ArrayList<>(Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red")));

        List<Apple> inventory2 = new ArrayList<>(Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red")));

        List<Apple> inventory3 = new ArrayList<>(Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red")));

        // 1.第一种排序方式
        System.out.println("=============第一种排序方式===============");
        inventory1.sort(new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getWeight().compareTo(o2.getWeight());
            }
        });

        System.out.println(inventory1);

        // 2.第二种排序方式
        System.out.println("=============第二种排序方式===============");
        inventory2.sort((Apple a1,Apple a2) -> a1.getWeight().compareTo(a2.getWeight()));
        System.out.println(inventory2);

        // 3.第三种方式
        System.out.println("=============第三种排序方式===============");
        inventory3.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(inventory3);

        // 扩展：如果weight相等，红苹果就排前面
        System.out.println("扩展");
        List<Apple> inventory4 = new ArrayList<>(Arrays.asList(new Apple(80, "green"), new Apple(155, "green"), new Apple(120, "red"), new Apple(120,"green")));
        inventory4.sort(Comparator.comparing(Apple::getWeight).thenComparing(Apple::getColor));
        System.out.println(inventory4);
    }

    public static class Apple {
        private Integer weight = 0;
        private String color = "";

        public Apple(Integer weight, String color){
            this.weight = weight;
            this.color = color;
        }

        public Integer getWeight() {
            return weight;
        }

        public void setWeight(Integer weight) {
            this.weight = weight;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public String toString() {
            return "Apple{" +
                    "color='" + color + '\'' +
                    ", weight=" + weight +
                    '}';
        }
    }
}
