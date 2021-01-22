package com.dxj.queue;

/**
 * @Description: 用数组实现队列
 * @Author: dengxj
 * @Date: 2020/12/15 8:20
 * @Version: 1.0
 */
public class QueueBasedOnDynamicArray {
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
     * 申请一个大小为 capacity 的数组
     * @param capacity
     */
    public QueueBasedOnDynamicArray(int capacity) {
        items = new String[capacity];
        n = capacity;
    }

    /**
     * 入队操作，将 item 放入队尾
     * @param item
     * @return
     */
    public boolean enqueue(String item) {
        // tail == n 表示队列末尾没有空间了
        if (tail == n) {
            // tail ==n && head==0，表示整个队列都占满了
            if (head == 0) {
                return false;
            }
            // 数据搬移
            if (tail - head >= 0) {
                System.arraycopy(items, head, items, 0, tail - head);
            }
            // 搬移完之后重新更新 head 和 tail
            tail -= head;
            head = 0;
        }

        items[tail] = item;
        tail++;
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
        // 为了让其他语言的同学看的更加明确，把--操作放到单独一行来写了
        String ret = items[head];
        ++head;
        return ret;
    }

    /**
     * 打印
     */
    public void printAll() {
        for (int i = head; i < tail; ++i) {
            System.out.print(items[i] + " ");
        }
        System.out.println();
    }
}
