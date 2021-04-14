package com.person.cache;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 实现 LRUCache 类：
 *  使得插入和查找时间复杂度都为O(1)
 */
class ListNode {
    Integer key;
    Integer value;
    ListNode next;
    ListNode prev;
    ListNode(){}
    ListNode(int key, int value){
        this.key = key;
        this.value = value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

public class LRUCache {
    private int capacity;
    private int size;
    private ListNode begin, end;

    // key:对象, value:在list中位置
    HashMap<Integer, ListNode> map ;
    /**
     * LRUCache(int capacity) 以正整数作为容量 capacity 初始化 LRU 缓存
     * @param capacity 容量
     */
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        map = new HashMap<>();
        begin = new ListNode();
        end = new ListNode();
        begin.next = end;
        end.prev = begin;
    }

    /**
     *
     * @param key
     * @return 若存在值则返回缓存值，否则返回-1
     */
    public int get(int key) {
        if (!map.containsKey(key)) {
            return -1;
        }

        ListNode node = map.get(key);
        addToHead(node);
        return node.value;
    }

    private void addToHead(ListNode node) {
        if (!map.containsKey(node.key)) {
            node.next = begin.next;
            node.prev = begin;
            begin.next.prev = node;
            begin.next = node;
        } else {
            node.prev.next = node.next;
            node.next.prev = node.prev;
            node.prev = begin;
            node.next = begin.next;
            begin.next.prev = node;
            begin.next = node;
        }
    }

    /**
     * void put(int key, int value) 如果关键字已经存在，则变更其数据值；如果关键字不存在，则插入该组「关键字-值」。当缓存容量达到上限时，它应该在写入新数据之前删除最久未使用的数据值，从而为新的数据值留出空间。
     * @param key
     * @param value
     */
    public void put(int key, int value) {
        if (map.containsKey(key)) {
            ListNode node = map.get(key);
            node.setValue(value);
            addToHead(node);
        } else {
            if (size < capacity) {
                ListNode node = new ListNode(key, value);
                addToHead(node);
                map.put(key, node);
                size++;
            } else {
                ListNode newNode = new ListNode(key, value);
                addToHead(newNode);
                map.put(key, newNode);
                // 删除末尾元素
                ListNode node = end.prev;
                map.remove(node);
                removeTail();
            }
        }

    }

    private void removeTail() {
        end.prev = end.prev.prev;
        end.prev.next = end;
    }
}
