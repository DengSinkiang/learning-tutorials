package com.dxj.linkedlist;

import java.util.Scanner;

/**
 * @Description: 基于单链表 LRU 算法
 * @Author: dengxj
 * @Date: 2020/12/7 9:09
 * @Version: 1.0
 */
public class LRUBasedOnLinkedList<T> {

    /**
     * 默认链表容量
     */
    private static final int DEFAULT_CAPACITY = 10;

    /**
     * 头结点
     */
    private final Node<T> headNode;

    /**
     * 链表长度
     */
    private int length;

    /**
     * 链表容量
     */
    private final int capacity;

    private LRUBasedOnLinkedList() {
        this.headNode = new Node<>();
        this.capacity = DEFAULT_CAPACITY;
    }

    public LRUBasedOnLinkedList(int capacity) {
        this.headNode = new Node<>();
        this.capacity = capacity;
    }

    private void add(T data) {
        Node<T> preNode = findPreNode(data);

        // 链表中存在，删除原数据，再插入到链表的头部
        if (preNode != null) {
            deleteElemOptim(preNode);
            intsertElemAtBegin(data);
        } else {
            if (length >= this.capacity) {
                // 删除尾结点
                deleteElemAtEnd();
            }
            intsertElemAtBegin(data);
        }
    }

    /**
     * 删除preNode结点下一个元素
     *
     * @param preNode
     */
    private void deleteElemOptim(Node<T> preNode) {
        Node<T> temp = preNode.getNext();
        preNode.setNext(temp.getNext());
        temp = null;
        length--;
    }

    /**
     * 链表头部插入节点
     *
     * @param data
     */
    private void intsertElemAtBegin(T data) {
        Node<T> next = headNode.getNext();
        headNode.setNext(new Node<T>(data, next));
        length++;
    }

    /**
     * 获取查找到元素的前一个结点
     *
     * @param data
     * @return
     */
    private Node<T> findPreNode(T data) {
        Node<T> node = headNode;
        while (node.getNext() != null) {
            if (data.equals(node.getNext().getElement())) {
                return node;
            }
            node = node.getNext();
        }
        return null;
    }

    /**
     * 删除尾结点
     */
    private void deleteElemAtEnd() {
        Node<T> ptr = headNode;
        // 空链表直接返回
        if (ptr.getNext() == null) {
            return;
        }
        // 倒数第二个结点
        while (ptr.getNext().getNext() != null) {
            ptr = ptr.getNext();
        }

        Node<T> tmp = ptr.getNext();
        ptr.setNext(null);
        tmp = null;
        length--;
    }

    private void printAll() {
        Node<T> node = headNode.getNext();
        while (node != null) {
            System.out.print(node.getElement() + ",");
            node = node.getNext();
        }
        System.out.println();
    }

    public static class Node<T> {

        private T element;

        private Node<T> next;

        public Node(T element) {
            this.element = element;
        }

        Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }

        Node() {
            this.next = null;
        }

        T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        Node<T> getNext() {
            return next;
        }

        void setNext(Node<T> next) {
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LRUBasedOnLinkedList<Integer> list = new LRUBasedOnLinkedList<>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            list.add(sc.nextInt());
            list.printAll();
        }
    }
}
