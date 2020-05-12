package com.huayu.study.java.lock.mq.diy;

public class Producer {

    private MQ topic ;

    public Producer(MQ topic) {
        this.topic = topic;
    }

    public void sendMsg(String msg) throws InterruptedException {
        synchronized (topic) {
            while (topic.isFull()) {
                System.out.println("waiting for consumer");
                topic.notify();
                topic.wait();
            }
            topic.add(msg);
            topic.notify();
        }
    }
}
