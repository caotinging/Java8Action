package com.caotinging.java8action.chap5;

/**
 * @program: Java8Action
 * @description:
 * @author: CaoTing
 * @date: 2019/10/31
 */
public class Trader {
    private final String name;
    private final String city;

    public Trader(String n, String c) {
        this.name = n;
        this.city = c;
    }

    public String getName() {
        return this.name;
    }

    public String getCity() {
        return this.city;
    }

    public String toString() {
        return "Trader:" + this.name + " in " + this.city;
    }
}
