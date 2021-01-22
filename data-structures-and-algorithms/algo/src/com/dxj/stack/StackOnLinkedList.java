package com.dxj.stack;

/**
 * @Description: 基于链表实现栈
 * @Author: dengxj
 * @Date: 2020/12/4 9:40
 * @Version: 1.0
 */
public class StackOnLinkedList {

    private Node top = null;
    public void push(int value) {
        Node newNode = new Node(value, null);
        // 判断是否栈空
        if (top != null) {
            newNode.next = top;
        }
        top = newNode;
    }

    /**
     * 用 -1 表示栈中没有数据
     * @return
     */
    public int pop() {
        if (top == null) {
            return -1;
        }
        int value = top.data;
        top = top.next;
        return value;
    }

    public void printAll() {
        Node p = top;
        while (p != null) {
            System.out.print(p.data + " ");
            p = p.next;
        }
        System.out.println();
    }

    private static class Node {
        private final int data;
        private Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }

        public int getData() {
            return data;
        }
    }
}