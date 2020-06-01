package com._520it.base.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/*
 * 一、缓冲区（Buffer）：在 Java NIO 中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
 *
 * 根据数据类型不同（boolean 除外），提供了相应类型的缓冲区：
 * ByteBuffer
 * CharBuffer
 * ShortBuffer
 * IntBuffer
 * LongBuffer
 * FloatBuffer
 * DoubleBuffer
 *
 * 上述缓冲区的管理方式几乎一致，通过 allocate() 获取缓冲区
 *
 * 二、缓冲区存取数据的两个核心方法：
 * put() : 存入数据到缓冲区中
 * get() : 获取缓冲区中的数据
 *
 * 三、缓冲区中的四个核心属性：
 * capacity : 容量，表示缓冲区中最大存储数据的容量。一旦声明不能改变。
 * limit : 界限，表示缓冲区中可以操作数据的大小。（limit 后数据不能进行读写）
 * position : 位置，表示缓冲区中正在操作数据的位置。
 *
 * mark : 标记，表示记录当前 position 的位置。可以通过 reset() 恢复到 mark 的位置
 *
 * 0 <= mark <= position <= limit <= capacity
 *
 * 四、直接缓冲区与非直接缓冲区：
 * 非直接缓冲区：通过 allocate() 方法分配缓冲区，将缓冲区建立在 JVM 的内存中
 * 直接缓冲区：通过 allocateDirect() 方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 */
public class TestBuffer {
    public static void main(String[] args) {
       // test1();
       // test2();
        test3();
    }


    public static void test1(){
        String str = "abcde";

        ByteBuffer bu = ByteBuffer.allocate(1024);

        System.out.println("-------------allocate()----------");
        System.out.println(bu.position());//0
        System.out.println(bu.limit());//1024
        System.out.println(bu.capacity());//1024


        bu.put(str.getBytes());
        System.out.println("-------------put()----------");
        System.out.println(bu.position());//5
        System.out.println(bu.limit());//1024
        System.out.println(bu.capacity());//1024

        bu.flip();
        System.out.println("-------------flip()----------");
        System.out.println(bu.position());//0
        System.out.println(bu.limit());//5
        System.out.println(bu.capacity());//1024

        System.out.println("-------------get()----------");
        byte[] bytes = new byte[bu.limit()];
        bu.get(bytes);
        System.out.println(new String(bytes,0,bytes.length));
        System.out.println(bu.position());//5
        System.out.println(bu.limit());//5
        System.out.println(bu.capacity());//1024

        //可重复读
        bu.rewind();
        System.out.println("-------------rewind()----------");
        System.out.println(bu.position());//0
        System.out.println(bu.limit());//5
        System.out.println(bu.capacity());//1024

        //6. clear() : 清空缓冲区. 但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
        bu.clear();
        System.out.println("-----------------clear()----------------");
        System.out.println(bu.position());
        System.out.println(bu.limit());
        System.out.println(bu.capacity());
        System.out.println((char)bu.get());


        //指定位置，position不会移动位置,put()同理
        System.out.println("-----------------get(int index)----------------");
        System.out.println(bu.position());
        System.out.println(bu.get(2));
        System.out.println(bu.position());

    }


    public static void test2() {

        String str ="abcdef";

        ByteBuffer buf =  ByteBuffer.allocate(1024);
        buf.put(str.getBytes());
        buf.flip();
        byte[] bytes = new byte[buf.limit()];

        buf.get(bytes,0,2);
        System.out.println(new String(bytes,0,bytes.length));
        System.out.println(buf.position());
        System.out.println("---------------------------------");
        //标记
        buf.mark();
        buf.get(bytes,2,2);
        System.out.println(new String(bytes,2,2));
        System.out.println(buf.position());
        System.out.println("---------------------------------");


        //reset()：恢复到mark的位置
        buf.reset();
        System.out.println(buf.position());
        //判断缓冲区中是否还有剩余数据
        if(buf.hasRemaining()){
            //获取缓冲区中可以操作的数量
            System.out.println(buf.remaining());
        }

    }

    public static void test3(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println(buffer.isDirect());

        ByteBuffer buffer2 = ByteBuffer.allocateDirect(1024);
        System.out.println(buffer2.isDirect());
    }
}
