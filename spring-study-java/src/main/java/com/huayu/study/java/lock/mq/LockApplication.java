package com.huayu.study.java.lock.mq;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockApplication {

    public static void main(String[] args) throws IOException {
        LinkedList<String> queue = Lists.newLinkedList();
        int maxSize = 10;
        Lock lock = new ReentrantLock();
        Condition readCondition = lock.newCondition();
        Condition writeCondition = lock.newCondition();

        Thread producer = new Thread(() -> {
            for (int i= 0; i<100; i++) {
                try {
                    lock.lock();
                    if (queue.size() >= maxSize) {
                        System.out.println("waiting for consumer");
                        writeCondition.await();
                    }
                    queue.add("msg" + i);
                    readCondition.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

        });

        Thread consumer = new Thread(() -> {
            for (int i= 0; i<100; i++) {
                try {
                    lock.lock();
                    if (queue.size() <= 0) {
                        System.out.println("waiting for product");
                        readCondition.await();
                    }
                    System.out.println(queue.remove());
                    writeCondition.signal();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }

        });

        producer.start();
        consumer.start();


        System.in.read();
    }
}
