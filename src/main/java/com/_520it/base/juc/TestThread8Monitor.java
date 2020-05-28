package com._520it.base.juc;

public class TestThread8Monitor {

    /**
     * 题目：判断打印的 "one" or "two" ？
     * 1. 两个普通同步方法，两个线程，标准打印， 打印? //one  two
     *
     *
     * @param args
     */
    public static void main(String[] args) {

         final Number num1 = new Number();
         final Number num2 = new Number();

        new Thread(new Runnable() {
            @Override
            public void run() {
                num1.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                num2.getTwo();
            }
        }).start();


    }

}

class Number{

    public synchronized void getOne(){
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }

    public synchronized void getTwo(){
        System.out.println("two");
    }

    public void getThree(){
        System.out.println("three");
    }
}