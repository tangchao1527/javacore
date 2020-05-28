package com._520it.base.jdk8;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/*
 * Java8 内置的四大核心函数式接口
 *
 * Consumer<T> : 消费型接口
 * 		void accept(T t);
 *
 * Supplier<T> : 供给型接口
 * 		T get();
 *
 * Function<T, R> : 函数型接口
 * 		R apply(T t);
 *
 * Predicate<T> : 断言型接口
 * 		boolean test(T t);
 *
 */
public class TestLambda3 {


    @Test
    public void test1(){
        happy(1000,(m) -> System.out.println("每次去消费："+m+"元"));
    }
    public void happy(double money, Consumer<Double> con){
        con.accept(money);
    }

    /**
     * 产生指定个数的整数，并放入集合中
     * @param num
     * @param sup
     * @return
     */
    public List<Integer> getNumList(int num, Supplier<Integer> sup){
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < num; i++) {
            Integer n = sup.get();
            list.add(n);
        }
        return list;
    }

    /**
     * 供给型接口
     */
    @Test
    public void test2(){
        List<Integer> numList = getNumList(10, () -> (int) (Math.random() * 100));
        for (Integer num:numList) {
            System.out.println(num);
        }
    }

    //需求：用于处理字符串
    public String strHandler(String str, Function<String,String> fun){
        return fun.apply(str);
    }

    @Test
    public void test3(){
        String newstr1 = strHandler("\t\t\t 尚硅谷牛皮  ", (str) -> str.trim());
        System.out.println(newstr1);

        String newstr2 = strHandler("abcd", str2 -> str2.toUpperCase());
        System.out.println(newstr2);
    }

    @Test
    public void test4(){
        List<String> list = Arrays.asList("hello", "world", "hi", "girl");
        List<String> stringList = filterStr(list, x -> x.length() > 3);
        for (String str:stringList) {
            System.out.println(str);
        }
    }

    /**
     * 将满足条件的字符串，放入集合中
     * @param list
     * @param pre
     * @return
     */
    public List<String> filterStr(List<String> list, Predicate<String> pre){
        List<String> strList = new ArrayList<>();

        for (String str:list) {
            if(pre.test(str)){
                strList.add(str);
            }
        }
        return strList;
    }
}
