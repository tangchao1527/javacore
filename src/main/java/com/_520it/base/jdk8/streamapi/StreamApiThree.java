package com._520it.base.jdk8.streamapi;


import com._520it.base.jdk8.lambda.Employee;
import org.junit.Rule;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/*
 * 一、 Stream 的操作步骤
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *
 * 3. 终止操作
 */
public class StreamApiThree {

    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66, Employee.Status.BUSY),
            new Employee(101, "张三", 18, 9999.99, Employee.Status.FREE),
            new Employee(103, "王五", 28, 3333.33, Employee.Status.VOCATION),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.BUSY),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
            new Employee(104, "赵六", 8, 7777.77, Employee.Status.FREE),
            new Employee(105, "田七", 38, 5555.55, Employee.Status.BUSY)
    );

    //3. 终止操作
	/*
		allMatch——检查是否匹配所有元素
		anyMatch——检查是否至少匹配一个元素
		noneMatch——检查是否没有匹配的元素
		findFirst——返回第一个元素
		findAny——返回当前流中的任意元素
		count——返回流中元素的总个数
		max——返回流中最大值
		min——返回流中最小值
	 */

	@Test
	public void test1(){
        boolean b = emps.stream()
                .allMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b);

        boolean b1 = emps.stream()
                .anyMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b1);

        boolean b2 = emps.stream()
                .noneMatch(e -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b2);
    }

    @Test
    public void test2(){
        Optional<Employee> employee = emps.stream()
                .sorted((x, y) -> -Double.compare(x.getSalary(), y.getSalary()))
                .findFirst();
        System.out.println(employee.get());

        Optional<Employee> optional = emps.stream()
                .filter(e -> e.getStatus().equals(Employee.Status.BUSY))
                .findAny();
        System.out.println(optional.get());
    }

    @Test
    public void test3(){
        long count = emps.stream()
                .filter(e -> e.getStatus().equals(Employee.Status.BUSY))
                .count();
        System.out.println(count);
        Optional<Integer> optional = emps.stream()
                .map(Employee::getAge)
                .max(Integer::compareTo);

        System.out.println(optional.get());

        Optional<Employee> optional1 = emps.stream()
                .min((x, y) -> Double.compare(x.getSalary(), y.getSalary()));
        System.out.println(optional1.get());
    }

    //注意：流进行了终止操作后，不能再次使用
    @Test
    public void test4(){
        Stream<Employee> stream = emps.stream()
                .filter((e) -> e.getStatus().equals(Employee.Status.FREE));

        long count = stream.count();

        stream.map(Employee::getSalary)
                .max(Double::compare);
    }

}
