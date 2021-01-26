package com.dxj.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 实现3个线程 依次打印ABC，并且循环10次
 * @Author: dengxj
 * @Date: 2021/1/26 8:46
 * @Version: 1.0
 */
public class MyThread extends Thread {

    private final AtomicInteger synObj;
    private final String name;
    private final int flag;

    private int count = 0;

    public MyThread(AtomicInteger synObj, String name, int flag) {
        this.synObj = synObj;
        this.name = name;
        this.flag = flag;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (synObj) {
                if (synObj.get() % 3 == flag) {
                    synObj.set(synObj.get() + 1);
                    System.out.println(name);
                    count++;
                    synObj.notifyAll();
                    if (count == 10) {
                        break;
                    }
                } else {
                    try {
                        synObj.wait();
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        AtomicInteger synObj = new AtomicInteger(0);

        MyThread a = new MyThread(synObj, "A", 0);
        MyThread b = new MyThread(synObj, "B", 1);
        MyThread c = new MyThread(synObj, "C", 2);

        a.start();
        b.start();
        c.start();
    }
}

