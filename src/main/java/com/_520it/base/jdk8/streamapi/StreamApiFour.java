package com._520it.base.jdk8.streamapi;

import com._520it.base.jdk8.lambda.Employee;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class StreamApiFour {

    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 79, 6666.66, Employee.Status.BUSY),
            new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
            new Employee(103, "王五", 28, 3333.33, Employee.Status.VOCATION),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.BUSY),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
            new Employee(105, "田七", 38, 5555.55, Employee.Status.BUSY)
    );

    //3. 终止操作
	/*
    归约
     reduce(T identity, BinaryOperator)
     reduce(BinaryOperator)
     ——可以将流中元素反复结合起来，得到一个值。
	 */
	@Test
    public void test1(){
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);
        System.out.println("--------------------");

        Optional<Double> reduce = emps.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(reduce.get());
    }

    //需求：搜索名字中 “六” 出现的次数
    @Test
    public void test2(){
        Optional<Integer> optional = emps.stream()
                .map(Employee::getName)
                .flatMap(StreamApiTwo::filterCharacter)
                .map(name -> {
                    if ((char) name == '六') {
                        return 1;
                    } else {
                        return 0;
                    }
                }).reduce(Integer::sum);

        System.out.println(optional.get());
    }

    //collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
    @Test
    public void test3(){
        List<String> list = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        list.forEach(System.out::print);

        System.out.println("\n--------------------");

        Set<String> set = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        set.forEach(System.out::print);

        System.out.println("\n--------------------");

        HashSet<String> hase = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        hase.forEach(System.out::print);
    }

    @Test
    public void test4(){

        Optional<Double> optional = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.maxBy(Double::compare));
        System.out.println(optional.get());

        Optional<Double> optional2 = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.minBy((x,y) -> Double.compare(x,y)));
        System.out.println(optional2.get());

        Double sum = emps.stream()
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println(sum);

        Double averag = emps.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(averag);

        Long count = emps.stream()
                .collect(Collectors.counting());
        System.out.println(count);

        System.out.println("---------------------");

        DoubleSummaryStatistics dss = emps.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println(dss.getMax()+"  "+dss.getMin()+"  "+dss.getAverage()+"  "+dss.getCount());

    }

    /**
     * 分组
     */
    @Test
    public void test5(){
        Map<Employee.Status, List<Employee>> resultmap = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println(resultmap);
    }

    /**
     * 多级分组
     */
    @Test
    public void test6(){
        Map<Employee.Status, Map<String, List<Employee>>> resultmap = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
                    if (e.getAge() > 60) {
                        return "老年";
                    } else if (e.getAge() > 35) {
                        return "中年";
                    } else {
                        return "未成年";
                    }
                })));

        System.out.println(resultmap);
    }

    /**
     * 分区
     */
    @Test
    public void test7(){
        Map<Boolean, List<Employee>> map = emps.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalary() > 5000));
        System.out.println(map);
    }

    @Test
    public void test8(){
        String str = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(",","[","']"));
        System.out.println(str);
    }

    @Test
    public void test9(){
        Optional<Double> optional = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.reducing(Double::sum));

        System.out.println(optional.get());
    }
}
