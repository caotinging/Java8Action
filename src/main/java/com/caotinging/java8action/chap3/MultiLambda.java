package com.caotinging.java8action.chap3;

import java.util.function.Function;

/**
 * @program: Java8Action
 * @description: 复合lambda
 * @author: CaoTing
 * @date: 2019/9/11
 */
public class MultiLambda {

    public static void main(String[] args) {
        Function<String, String> addHeader = Letter::addHeader;
        changeLetter(addHeader.andThen(Letter::checkSpelling).andThen(Letter::addFooter));
    }

    static void changeLetter(Function<String,String> function) {
        String s = "labda";
        System.out.println(function.apply(s));
    }

    public static class Letter{
        public static String addHeader(String text){
            return "From caotinging: " + text;
        }
        public static String addFooter(String text){
            return text + " Kind regards";
        }
        public static String checkSpelling(String text){
            return text.replaceAll("labda", "lambda");
        }
    }
}