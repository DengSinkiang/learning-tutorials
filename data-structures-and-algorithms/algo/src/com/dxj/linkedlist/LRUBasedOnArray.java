package com.dxj.linkedlist;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 基于数组 LRU 算法：1. 空间复杂度为 O(n) 2. 时间复杂度为 O(n) 3. 不支持 null 的缓存
 * @Author: dengxj
 * @Date: 2020/12/7 9:09
 * @Version: 1.0
 */
public class LRUBasedOnArray<T> {

    private static final int DEFAULT_CAPACITY = (1 << 3);

    private final int capacity;

    private int count;

    private final T[] value;

    private final Map<T, Integer> holder;

    private LRUBasedOnArray() {
        this(DEFAULT_CAPACITY);
    }

    private LRUBasedOnArray(int capacity) {
        this.capacity = capacity;
        value = (T[]) new Object[capacity];
        holder = new HashMap<>(capacity);
    }

    /**
     * 模拟访问某个值
     *
     * @param object
     */
    public void offer(T object) {
        if (object == null) {
            throw new IllegalArgumentException("该缓存容器不支持 null!");
        }
        Integer index = holder.get(object);
        if (index == null) {
            if (isFull()) {
                removeAndCache(object);
            } else {
                cache(object, count);
            }
        } else {
            update(index);
        }
    }

    /**
     * 若缓存中有指定的值，则更新位置
     *
     * @param end
     */
    private void update(int end) {
        T target = value[end];
        rightShift(end);
        value[0] = target;
        holder.put(target, 0);
    }

    /**
     * 缓存数据到头部，但要先右移
     *
     * @param object
     * @param end    数组右移的边界
     */
    private void cache(T object, int end) {
        rightShift(end);
        value[0] = object;
        holder.put(object, 0);
        count++;
    }

    /**
     * 缓存满的情况，踢出后，再缓存到数组头部
     *
     * @param object
     */
    private void removeAndCache(T object) {
        T key = value[--count];
        holder.remove(key);
        cache(object, count);
    }

    /**
     * end左边的数据统一右移一位
     *
     * @param end
     */
    private void rightShift(int end) {
        for (int i = end - 1; i >= 0; i--) {
            value[i + 1] = value[i];
            holder.put(value[i], i + 1);
        }
    }

    public boolean isContain(T object) {
        return holder.containsKey(object);
    }

    public boolean isEmpty() {
        return count == 0;
    }

    private boolean isFull() {
        return count == capacity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(value[i]);
            sb.append(" ");
        }
        return sb.toString();
    }

    static class TestLRUBasedArray {

        public static void main(String[] args) {
            testDefaultConstructor();
            testSpecifiedConstructor();
//            testWithException();
        }

        private static void testWithException() {
            LRUBasedOnArray<Integer> lru = new LRUBasedOnArray<>();
            lru.offer(null);
        }

        static void testDefaultConstructor() {
            System.out.println("======无参测试========");
            LRUBasedOnArray<Integer> lru = new LRUBasedOnArray<>();
            lru.offer(1);
            lru.offer(2);
            lru.offer(3);
            lru.offer(4);
            lru.offer(5);
            System.out.println(lru);
            lru.offer(6);
            lru.offer(7);
            lru.offer(8);
            lru.offer(9);
            System.out.println(lru);
        }

        static void testSpecifiedConstructor() {
            System.out.println("======有参测试========");
            LRUBasedOnArray<Integer> lru = new LRUBasedOnArray<>(4);
            lru.offer(1);
            System.out.println(lru);
            lru.offer(2);
            System.out.println(lru);
            lru.offer(3);
            System.out.println(lru);
            lru.offer(4);
            System.out.println(lru);
            lru.offer(2);
            System.out.println(lru);
            lru.offer(4);
            System.out.println(lru);
            lru.offer(7);
            System.out.println(lru);
            lru.offer(1);
            System.out.println(lru);
            lru.offer(2);
            System.out.println(lru);
        }
    }
}
