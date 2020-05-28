package com._520it.base.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TestReadWriteLock {

    public static void main(String[] args) {
        final ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                readWriteLockDemo.set((int)Math.random()*101);
            }
        },"Write").start();

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLockDemo.get();
                }
            }).start();
        }

    }

}

class ReadWriteLockDemo{
    private int number = 0;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    //读
    public void get(){
        lock.readLock().lock();

        try {
            System.out.println(Thread.currentThread().getName() + ":" + number);
        }finally {
            lock.readLock().unlock();
        }
    }

    //写
    public void set(int number){
        lock.writeLock().lock();

        try {
            System.out.println(Thread.currentThread().getName());
            this.number = number;
        }finally {
            lock.writeLock().unlock();
        }
    }

}