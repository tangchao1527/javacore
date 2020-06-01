package com._520it.base.jdk8.streamapi;

import com._520it.base.jdk8.lambda.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamApiTwo {
    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66),
            new Employee(101, "张三", 18, 9999.99),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    public static Stream<Character> filterCharacter(String str){
        List<Character> list = new ArrayList<>();

        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }

        return list.stream();
    }

    //2. 中间操作
	/*
		映射
		map——接收 Lambda ， 将元素转换成其他形式或提取信息。接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
		flatMap——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
	 */

	@Test
	public void test1(){

	    emps.stream()
            .map((e) -> e.getName())
            .forEach(System.out::println);
        System.out.println("--------------------");

        List<String> strs = Arrays.asList("aaa", "bbb", "ccc");

        strs.stream()
            .map(e -> e.toUpperCase())
            .forEach(System.out::println);

        System.out.println("-------------------");

        Stream<Stream<Character>> stream = strs.stream()
                .map(StreamApiTwo::filterCharacter);

        stream.forEach((sm) ->{
            sm.forEach(System.out::print);
        });

        System.out.println("------------------");

        Stream<Character> stream2 = strs.stream()
                .flatMap(StreamApiTwo::filterCharacter);

        stream2.forEach(System.out::print);
    }

    /*
    sorted()——自然排序
    sorted(Comparator com)——定制排序
 */
    @Test
    public void test2(){
        emps.stream()
            .map(Employee::getName)
            .sorted()
            .forEach(System.out::print);
        System.out.println("-----------------");
        emps.stream()
            .sorted((x,y) -> {
                if(x.getAge() == y.getAge()){
                   return x.getName().compareTo(y.getName());
                }else {
                    return Integer.compare(x.getAge(),y.getAge());
                }
            }).forEach(System.out::println);
    }

}
