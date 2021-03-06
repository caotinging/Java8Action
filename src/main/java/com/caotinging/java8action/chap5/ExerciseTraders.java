package com.caotinging.java8action.chap5;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @program: Java8Action
 * @description: 流的使用-练习
 * @author: CaoTing
 * @date: 2019/10/31
 */
public class ExerciseTraders {

    private static Trader raoul = new Trader("Raoul", "Cambridge");
    private static Trader mario = new Trader("Mario","Milan");
    private static Trader alan = new Trader("Alan","Cambridge");
    private static Trader brian = new Trader("Brian","Cambridge");

    private static List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    public static void main(String[] args) {
        System.out.println("(1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。");
        test1();

        System.out.println("(2) 交易员都在哪些不同的ۡ市工作过？");
        test2();

        System.out.println("(3) 查找所有来自于剑桥的交易员，并按姓名排序。");
        test3();

        System.out.println("(4) 返回所有交易员的姓名字符串，按字母顺序排序。");
        test4();

        System.out.println("(5) 有没有交易员是在米兰工作的？");
        test5();

        System.out.println("(6) 打印生活在剑桥的交易员的所有交易额。");
        test6();

        System.out.println("(7) 所有交易中，最高的交易额是多少？");
        test7();

        System.out.println("(8) 找到交易额最小的交易。");
        test8();
    }

    /**
     * (8) 找到交易额最小的交易。
     */
    private static void test8() {
        Optional<Integer> result = transactions.stream()
                .map(Transaction::getValue)
                .reduce(Integer::min);

        System.out.println(result.orElse(0));

        Optional<Transaction> min = transactions.stream()
                .min(Comparator.comparing(Transaction::getValue));
    }

    /**
     * (7) 所有交易中，最高的交易额是多少？
     */
    private static void test7() {
        Integer result = transactions.stream()
                .map(Transaction::getValue)
                .reduce(0, Integer::max);

        System.out.println(result);
    }

    /**
     * (6) 打印生活在剑桥的交易员的所有交易额。
     */
    private static void test6() {
        transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        // 这里是总金额
        Integer result2 = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .reduce(0, Integer::sum);
    }

    /**
     * (5) 有没有交易员是在米兰工作的？
     */
    private static void test5() {
        Optional<Transaction> result = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Milan"))
                .findAny();

        if(result.isPresent()) {
            System.out.println(result.get());
        } else {
            System.out.println("没有");
        }

        // 或者如下
        boolean result2 = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
    }

    /**
     * (4) 返回所有交易员的姓名字符串，按字母顺序排序。
     */
    private static void test4() {
        String result = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted(String::compareTo)
                .reduce("", (s1, s2) -> s1 + s2);

        System.out.println(result);

        /*
         * 请注意，此解决方案效率不高（所有字符串都被反复连接，每次迭代的时候都要建立一个新
         * 的String对象）。下一章中，你将看到一个更为高效的解决方案，它像下面这样使用joining（其
         * 内部会用到StringBuilder）：
         */
        String result2 = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted(String::compareTo)
                .collect(Collectors.joining());
    }

    /**
     * (3) 查找所有来自于剑桥的交易员，并按姓名排序。
     */
    private static void test3() {
        System.out.println("方法一：");
        List<Trader> result1 = transactions.stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .sorted(Comparator.comparing(t -> t.getTrader().getName()))
                .map(Transaction::getTrader)
                .distinct()
                .collect(Collectors.toList());

        result1.forEach(System.out::println);

        System.out.println("方法二：");
        List<Trader> result2 = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(Comparator.comparing(Trader::getName))
                .collect(Collectors.toList());

        result2.forEach(System.out::println);
    }

    /**
     * (2) 交易员都在哪些不同的ۡ市工作过？
     */
    private static void test2(){
        System.out.println("方法一：");
        List<String> result1 = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(Collectors.toList());

        result1.forEach(System.out::println);

        // 这里还有一个新招：你可以去掉distinct()，改用toSet()，这样就会把流转换为集合并互不重复。
        System.out.println("方法二：");
        Set<String> result2 = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .collect(Collectors.toSet());

        result2.forEach(System.out::println);
    }

    /**
     * (1) 找出2011年发生的所有交易，并按交易额排序（从低到高）。
     */
    private static void test1(){
        List<Transaction> result = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(Comparator.comparingInt(Transaction::getValue))
                // .sorted(Comparator.comparingInt(Transaction::getValue).reversed()) 代表逆序，默认从小到大 逆序从大到小
                .collect(Collectors.toList());

        result.forEach(System.out::println);
    }
}
