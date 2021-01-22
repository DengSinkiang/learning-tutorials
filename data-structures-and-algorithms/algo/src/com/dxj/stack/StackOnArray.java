package com.dxj.stack;

/**
 * @Description: 基于数组实现栈
 * @Author: dengxj
 * @Date: 2020/12/4 9:35
 * @Version: 1.0
 */
public class StackOnArray {
    /**
     * 数组
     */
    private final String[] items;
    /**
     * 栈中元素个数
     */
    private int count;
    /**
     * 栈的⼤⼩
     */
    private final int n;

    /**
     * 初始化数组，申请⼀个⼤⼩为n的数组空间
     * @param n
     */
    public StackOnArray(int n) {
        this.items = new String[n];
        this.n = n;
        this.count = 0;
    }

    /**
     * ⼊栈操作
     * @param item
     * @return
     */
    public boolean push(String item) {
        // 数组空间不够了，直接返回 false，⼊栈失败。
        if (count == n) {
            return false;
        }
        // 将 item 放到下标为 count 的位置，并且 count 加⼀
        items[count] = item;
        ++count;
        return true;
    }

    /**
     * 出栈操作
     * @return
     */
    public String pop() {
        // 栈为空，则直接返回 null
        if (count == 0) {
            return null;
        }
        // 返回下标为 count-1 的数组元素，并且栈中元素个数 count 减⼀
        String tmp = items[count - 1];
        --count;
        return tmp;
    }
}
