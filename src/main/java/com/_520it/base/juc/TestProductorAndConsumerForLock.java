package com._520it.base.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TestProductorAndConsumerForLock {
    public static void main(String[] args) {
        Clerk2 clerk = new Clerk2();

        Productor2 productor = new Productor2(clerk);
        Consumer2 consumer = new Consumer2(clerk);

        new Thread(productor,"生产者A").start();
        new Thread(consumer,"消费者B").start();

        new Thread(productor,"生产者C").start();
        new Thread(consumer,"消费者D").start();
    }

}

//店员
class Clerk2{

    private int products = 0;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    //进货
    public  void get(){
        lock.lock();
        try {
            while (products >= 1) {//不能使用if，避免虚假唤醒问题，应该总是使用在循环中
                System.out.println("产品已满.....");
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + ":" + ++products);
            condition.signalAll();

        }finally {
            lock.unlock();
        }

    }

    //卖货
    public  void sale(){
       lock.lock();
       try {
           while (products <= 0) {
               System.out.println("产品已买空.........");
               try {
                  condition.await();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
           System.out.println(Thread.currentThread().getName() + ":" + --products);
           condition.signalAll();
       }finally {
           lock.unlock();
       }

    }

}

//生产者
class Productor2 implements Runnable{

    private Clerk2 clerk;

    public Productor2(Clerk2 clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {

        for (int i = 0; i < 20; i++) {

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            clerk.get();
        }
    }
}


//消费者
class Consumer2 implements Runnable{

    private Clerk2 clerk;

    public Consumer2(Clerk2 clerk){
        this.clerk = clerk;
    }

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {

          /*  try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();

            }*/
            clerk.sale();
        }
    }
}