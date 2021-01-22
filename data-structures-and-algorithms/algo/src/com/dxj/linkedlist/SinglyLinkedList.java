package com.dxj.linkedlist;

/**
 * @Description: 单链表的插入、删除、查找操作；链表中存储的是int类型的数据
 * @Author: dengxj
 * @Date: 2020/12/7 9:09
 * @Version: 1.0
 */
public class SinglyLinkedList<T> {

    private Node<T> head = null;

    public Node<T> findByValue(T value) {
        Node<T> p = head;
        while (p != null && p.element != value) {
            p = p.next;
        }

        return p;
    }

    public Node<T> findByIndex(int index) {
        Node<T> p = head;
        int pos = 0;
        while (p != null && pos != index) {
            p = p.next;
            ++pos;
        }

        return p;
    }

    /**
     * 无头结点 表头部插入 这种操作将于输入的顺序相反，逆序
     * @param value
     */
    public void insertToHead(T value) {
        Node<T> newNode = new Node<T>(value, null);
        insertToHead(newNode);
    }

    public void insertToHead(Node<T> newNode) {
        if (head == null) {
            head = newNode;
        } else {
            newNode.next = head;
            head = newNode;
        }
    }

    /**
     * 顺序插入 链表尾部插入
     *
     * @param value
     */
    public void insertTail(T value) {
        Node<T> newNode = new Node<T>(value, null);
        //空链表，可以插入新节点作为head，也可以不操作
        if (head == null) {
            head = newNode;
        } else {
            Node<T> q = head;
            while (q.next != null) {
                q = q.next;
            }
            newNode.next = q.next;
            q.next = newNode;
        }
    }

    public void insertAfter(Node<T> p, T value) {
        Node<T> newNode = new Node<T>(value, null);
        insertAfter(p, newNode);
    }

    public void insertAfter(Node<T> p, Node<T> newNode) {
        if (p == null) {
            return;
        }

        newNode.next = p.next;
        p.next = newNode;
    }

    public void insertBefore(Node<T> p, T value) {
        Node<T> newNode = new Node<T>(value, null);
        insertBefore(p, newNode);
    }

    public void insertBefore(Node<T> p, Node<T> newNode) {
        if (p == null) {
            return;
        }
        if (head == p) {
            insertToHead(newNode);
            return;
        }

        Node<T> q = head;
        while (q != null && q.next != p) {
            q = q.next;
        }

        if (q == null) {
            return;
        }

        newNode.next = p;
        q.next = newNode;

    }

    public void deleteByNode(Node<T> p) {
        if (p == null || head == null) {
            return;
        }

        if (p == head) {
            head = head.next;
            return;
        }

        Node<T> q = head;
        while (q != null && q.next != p) {
            q = q.next;
        }

        if (q == null) {
            return;
        }

        q.next = q.next.next;
    }

    public void deleteByValue(T value) {
        if (head == null) {
            return;
        }

        Node<T> p = head;
        Node<T> q = null;
        while (p != null && p.element != value) {
            q = p;
            p = p.next;
        }

        if (p == null) {
            return;
        }

        if (q == null) {
            head = head.next;
        } else {
            q.next = q.next.next;
        }

        // 可重复删除指定value的代码
        /*
           if (head != null && head.data == value) {
           head = head.next;
           }
           Node pNode = head;
           while (pNode != null) {
           if (pNode.next.data == data) {
           pNode.next = pNode.next.next;
           continue;
           }
           pNode = pNode.next;
           }
         */
    }

    public void printAll() {
        Node<T> p = head;
        while (p != null) {
            System.out.print(p.element + " ");
            p = p.next;
        }
        System.out.println();
    }

    /**
     * 判断true or false
     *
     * @param left
     * @param right
     * @return
     */
    public boolean tfResult(Node<T> left, Node<T> right) {
        Node<T> l = left;
        Node<T> r = right;

        boolean flag = true;
        System.out.println("left_:" + l.element);
        System.out.println("right_:" + r.element);
        while (l != null && r != null) {
            if (l.element == r.element) {
                l = l.next;
                r = r.next;
            } else {
                flag = false;
                break;
            }

        }

        System.out.println("什么结果");
        return flag;
       /* if (l==null && r==null){
           System.out.println("什么结果");
           return true;
        }else{
           return false;
        }*/
    }

    /**
     * 判断是否为回文
     *
     * @return
     */
    public boolean palindrome() {
        if (head == null) {
            return false;
        } else {
            System.out.println("开始执行找到中间节点");
            Node<T> p = head;
            Node<T> q = head;
            if (p.next == null) {
                System.out.println("只有一个元素");
                return true;
            }
            while (q.next != null && q.next.next != null) {
                p = p.next;
                q = q.next.next;

            }

            System.out.println("中间节点" + p.element);
            System.out.println("开始执行奇数节点的回文判断");
            Node<T> leftLink;
            Node<T> rightLink;
            if (q.next == null) {
                //　p 一定为整个链表的中点，且节点数目为奇数
                rightLink = p.next;
                leftLink = inverseLinkList(p).next;
                System.out.println("左边第一个节点" + leftLink.element);
                System.out.println("右边第一个节点" + rightLink.element);

            } else {
                //p q　均为中点
                rightLink = p.next;
                leftLink = inverseLinkList(p);
            }
            return tfResult(leftLink, rightLink);

        }
    }

    /**
     * 带结点的链表翻转
     *
     * @param p
     * @return
     */
    public Node<T> inverseLinkListHead(Node<T> p) {

        //　head　为新建的一个头结点
        Node<Integer> head = new Node<>(1111, null);

        // p 为原来整个链表的头结点,现在 head 指向整个链表
        head.next = (Node<Integer>) p;

        // 带头结点的链表翻转等价于从第二个元素开始重新头插法建立链表
        Node<T> cur = p.next;
        p.next = null;
        Node<T> next;
        while (cur != null) {
            next = cur.next;
            cur.next = (Node<T>) head.next;
            head.next = (Node<Integer>) cur;
            System.out.println("first " + head.element);
            cur = next;
        }

        //　返回左半部分的中点之前的那个节点
        //　从此处开始同步像两边比较
        return (Node<T>) head;

    }

    /**
     * 无头结点的链表翻转
     *
     * @param p
     * @return
     */
    public Node<T> inverseLinkList(Node<T> p) {

        Node<T> pre = null;
        Node<T> r = head;
        System.out.println("z---" + r.element);
        Node<T> next = null;
        while (r != p) {
            next = r.next;

            r.next = pre;
            pre = r;
            r = next;
        }

        r.next = pre;
        //　返回左半部分的中点之前的那个节点
        //　从此处开始同步像两边比较
        return r;

    }

    public Node<T> createNode(T value) {
        return new Node<T>(value, null);
    }

    public static class Node<T> {
        private final T element;
        private Node<T> next;

        Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }

        public T getElement() {
            return element;
        }
    }

    public static void main(String[] args) {

        SinglyLinkedList<Integer> link = new SinglyLinkedList<>();
        System.out.println("hello");
        // int[] data = {1};
        // int[] data = {1,2};
        // int[] data = {1,2,3,1};
        int[] data = {1, 2, 5};
        // int[] data = {1,2,2,1};
        // int[] data = {1,2,5,2,1};
        // int[] data = {1, 2, 5, 2, 1};

        for (int datum : data) {
            // link.insertToHead(data[i]);
            link.insertTail(datum);
        }
        link.printAll();
        Node<Integer> p = link.inverseLinkListHead(link.head);
        while (p != null) {
            System.out.println("aa" + p.element);
            p = p.next;
        }

        System.out.println("打印原始:");
        link.printAll();
        if (link.palindrome()) {
            System.out.println("回文");
        } else {
            System.out.println("不是回文");
        }
    }

}
