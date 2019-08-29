package com.caotinging.java8action.chap3;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: Java8Action
 * @description: 使用函数式接口
 * @author: CaoTing
 * @date: 2019/8/29
 */
public class Lambdas {

    /**
     * jdk8中已经实现了一个这样的函数式接口 Predicate (这里只是作为演示）
     * @param <T>
     */
    @FunctionalInterface
    public interface Predicate<T>{
        boolean test(T t);
    }

    private static <T> List<T> filter(List<T> list, Predicate<T> p) {
        List<T> results = new ArrayList<>();
        for(T s: list){
            if(p.test(s)){
                results.add(s);
            }
        }
        return results;
    }

    public static void main(String[] args) {
        List<String> listOfStrings = new ArrayList<>();
        listOfStrings.add("123");
        listOfStrings.add(null);

        List<String> nonEmpty = filter(listOfStrings, (String s) -> !s.isEmpty());
        System.out.println(nonEmpty);
    }



}
