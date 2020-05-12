package com.huayu.study.java.lock.mq.diy;

import com.google.common.collect.Lists;

import java.util.LinkedList;

public class MQ {

    /**
     * 消息容器
     */
    private LinkedList<String> container  = Lists.newLinkedList();

    /**
     * 最大消息容量
     */
    private int maxSize = 10;

    public void add(String msg) {
        container.add(msg);
    }

    public String remove() {
        return container.remove();
    }

    public Boolean isEmpty() {
        return container.size() == 0;
    }

    public Boolean isFull() {
        return container.size() == maxSize;
    }


}
