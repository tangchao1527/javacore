package com._520it.jvm.base;

import com._520it.common.domain.MyObject;

/**
 * @description: jvm垃圾回收机制
 * @author: superman
 * @create: 2020/07/03 09:56
 */
public class MyDomeOne {

    public static void main(String[] args) {
        System.out.println("==============引用计数算法=================");
        //这段代码是用来验证引用计数算法不能检测出循环引用。最后面两句将object1和object2赋值为null，
        // 也就是说object1和object2指向的对象已经不可能再被访问，但是由于它们互相引用对方，
        // 导致它们的引用计数器都不为0，那么垃圾收集器就永远不会回收它们。
        MyObject object1 = new MyObject();
        MyObject object2 = new MyObject();
        object1.object = object2;
        object2.object = object1;
        object1 = null;
        object2 = null;

        System.out.println("=================================");

        System.out.println("Xmx=" + Runtime.getRuntime().maxMemory() / 1024.0 / 1024 + "M");    //系统的最大空间
        System.out.println("free mem=" + Runtime.getRuntime().freeMemory() / 1024.0 / 1024 + "M");  //系统的空闲空间
        System.out.println("total mem=" + Runtime.getRuntime().totalMemory() / 1024.0 / 1024 + "M");  //当前可用的总空间
    }
}
