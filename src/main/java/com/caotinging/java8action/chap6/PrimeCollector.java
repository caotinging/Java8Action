package com.caotinging.java8action.chap6;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;

/**
 * @program: Java8Action
 * @description: 质数收集器
 * @author: CaoTing
 * @date: 2019/12/18
 */
public class PrimeCollector implements Collector<Integer, Map<Boolean, List<Integer>>, Map<Boolean,List<Integer>>> {
    @Override
    public Supplier<Map<Boolean, List<Integer>>> supplier() {
        return () -> new HashMap<Boolean, List<Integer>>() {
            {
                put(true, new ArrayList<>());
                put(false,new ArrayList<>());
            }
        };
    }

    @Override
    public BiConsumer<Map<Boolean, List<Integer>>, Integer> accumulator() {
        return ((booleanListMap, integer) -> booleanListMap.get(isPrime(booleanListMap.get(true), integer)).add(integer));
    }

    @Override
    public BinaryOperator<Map<Boolean, List<Integer>>> combiner() {
        return (booleanListMap, booleanListMap2) -> {
             booleanListMap.get(true).addAll(booleanListMap2.get(true));
             booleanListMap.get(false).addAll(booleanListMap2.get(false));
             return booleanListMap;
        };
    }

    @Override
    public Function<Map<Boolean, List<Integer>>, Map<Boolean, List<Integer>>> finisher() {
        return Function.identity();
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Collections.unmodifiableSet(EnumSet.of(Characteristics.IDENTITY_FINISH));
    }

    /**
     * 判断给定参数candidate是否为质数，通过与小于该数的所有质数的平方根进行整除判断
     *
     * 因为所有的非质数（合数）都可以分解为质数的乘积，
     *
     * 因此 == 一个数如果是质数，那么这个数除以任何质数都不会是整除
     * @param acc
     * @param candidate
     * @return
     */
    private Boolean isPrime(List<Integer> acc, Integer candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return takeWhile(acc, i -> i > candidateRoot)
                .stream()
                .noneMatch(integer -> candidate % integer == 0);
    }

    /**
     * 按照指定的谓词截取list
     * @param accs
     * @param p
     * @param <A>
     * @return
     */
    private static <A> List<A> takeWhile(List<A> accs, Predicate<A> p) {
        for (int i = 0; i < accs.size(); i++) {
            if (p.test(accs.get(i))) {
                return accs.subList(0,i);
            }
        }
        return accs;
    }
}
