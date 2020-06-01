package com._520it.base.jdk8.optional;

import com._520it.base.jdk8.lambda.Employee;
import org.junit.Test;

import java.util.Optional;

/*
 * 一、Optional 容器类：用于尽量避免空指针异常
 * 	Optional.of(T t) : 创建一个 Optional 实例
 * 	Optional.empty() : 创建一个空的 Optional 实例
 * 	Optional.ofNullable(T t):若 t 不为 null,创建 Optional 实例,否则创建空实例
 * 	isPresent() : 判断是否包含值
 * 	orElse(T t) :  如果调用对象包含值，返回该值，否则返回t
 * 	orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回 s 获取的值
 * 	map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回 Optional.empty()
 * 	flatMap(Function mapper):与 map 类似，要求返回值必须是Optional
 */
public class TestOptional {


    @Test
    public void test1(){
        Optional<Employee> optional = Optional.of(new Employee());
        System.out.println(optional.get());
    }

    @Test
    public void test2(){
        Optional<Employee> optional = Optional.ofNullable(new Employee());
        System.out.println(optional.get());

        Optional<Employee> optional2 = Optional.ofNullable(null);
        System.out.println(optional2.get());

        Optional<Object> optional3 = Optional.empty();
        System.out.println(optional3.get());
    }

    @Test
    public void test3(){
        Optional<Employee> optional = Optional.ofNullable(new Employee());

        if(optional.isPresent()){
            System.out.println(optional.get());
        }

        Employee employee = optional.orElse(new Employee("张三"));

        System.out.println(employee);

        Employee emp2 = optional.orElseGet(() -> new Employee("李四"));
        System.out.println(emp2);
    }

    @Test
    public void test4(){
        Optional<Employee> op = Optional.of(new Employee(101, "张三", 18, 9999.99));
        Optional<String> op2 = op.map(Employee::getName);
        System.out.println(op2.get());

        Optional<String> op3 = op.flatMap((e) -> Optional.of(e.getName()));
        System.out.println(op3.get());
    }
}
