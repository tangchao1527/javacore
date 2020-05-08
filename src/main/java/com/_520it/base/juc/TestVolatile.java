package com._520it.base.juc;

/**
 * @Author: superman
 * @Date: 2020/5/8 9:28
 */
public class TestVolatile {


    /**
     * 一、volatile 关键字：当多个线程进行操作共享数据时，可以保证内存中的数据可见。
     * 					  相较于 synchronized 是一种较为轻量级的同步策略。
     *
     * 注意：
     * 1. volatile 不具备“互斥性”
     * 2. volatile 不能保证变量的“原子性”
     */
    public static void main(String[] args) {

        Mythread mythread = new Mythread();
        new Thread(mythread).start();

        while (true){
            if (mythread.isFlag()){
                System.out.println("退出循环");
                break;
            }
        }
    }


}
 class Mythread implements Runnable{
     private volatile    boolean flag = false;

     @Override
     public void run() {

         try {
             Thread.sleep(1000);
         } catch (InterruptedException e) {
         }

         flag = true;

         System.out.println("flag=" + isFlag());

     }

     public boolean isFlag() {
         return flag;
     }

     public void setFlag(boolean flag) {
         this.flag = flag;
     }
}