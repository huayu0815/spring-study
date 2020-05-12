package com.huayu.study.java.lock.mq.diy;

public class Consumer {

    private MQ topic ;

    public Consumer(MQ topic) {
        this.topic = topic;
    }

    public void receiver() throws InterruptedException {
        synchronized (topic) {
            while (topic.isEmpty()) {
                System.out.println("waiting for product");
                topic.notify();
                topic.wait();
            }
            String msg = topic.remove();
            System.out.println(msg);
            topic.notify();
        }
    }
}
