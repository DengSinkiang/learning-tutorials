package com.dxj.queue;

/**
 * @Description: 用数组实现循环队列
 * @Author: dengxj
 * @Date: 2020/12/15 8:36
 * @Version: 1.0
 */
public class CircularQueue {
    /**
     * 数组：items
     */
    private final String[] items;
    /**
     * 数组大小：n
     */
    private final int n;
    /**
     * head 表示队头下标
     */
    private int head = 0;
    /**
     * tail 表示队尾下标
     */
    private int tail = 0;
    /**
     * 申请一个大小为capacity的数组
     * @param capacity
     */
    public CircularQueue(int capacity) {
        items = new String[capacity];
        n = capacity;
    }

    /**
     * 入队
     * @param item
     * @return
     */
    public boolean enqueue(String item) {
        // 队列满了
        if ((tail + 1) % n == head) {
            return false;
        }
        items[tail] = item;
        tail = (tail + 1) % n;
        return true;
    }

    /**
     * 出队
     * @return
     */
    public String dequeue() {
        // 如果 head == tail 表示队列为空
        if (head == tail) {
            return null;
        }
        String ret = items[head];
        head = (head + 1) % n;
        return ret;
    }

    public void printAll() {
        if (0 == n) {
            return;
        }
        for (int i = head; i % n != tail; i = (i + 1) % n) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }
}
