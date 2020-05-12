package com.huayu.study.java.lock.mq;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;

import java.util.LinkedList;

public class SynchronizedApplication {

    private LinkedList<String> queue = Lists.newLinkedList();

    private int maxSize = 10;

    public static void main(String[] args) throws InterruptedException {
        SynchronizedApplication application = new SynchronizedApplication();
        new Thread(application.new Producer()).start();
        new Thread(application.new Consumer()).start();
        Thread.sleep(10000L);

    }

    class Producer implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            synchronized (queue) {
                for(int i=0;i<100;i++) {
                    if (queue.size() >= maxSize) {
                        System.out.println("waiting for consumer");
                        queue.notify();
                        queue.wait();
                    }
                    queue.add("msg" + i);
                    queue.notify();
                }
            }
        }
    }

    class Consumer implements Runnable {

        @SneakyThrows
        @Override
        public void run() {
            synchronized (queue) {
                for(int i=0;i<100;i++) {
                    if (queue.size() <=0 ) {
                        System.out.println("waiting for product");
                        queue.notify();
                        queue.wait();
                    }
                    String msg = queue.remove();
                    System.out.println(msg);;
                    queue.notify();
                }
            }
        }
    }

}
