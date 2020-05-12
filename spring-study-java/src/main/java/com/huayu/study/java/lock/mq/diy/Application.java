package com.huayu.study.java.lock.mq.diy;

public class Application {

    public static void main(String[] args) throws InterruptedException {
        MQ topic = new MQ();
        Producer productor = new Producer(topic);
        Consumer consumer = new Consumer(topic);

        for (int i=0; i<15; i++)  {
            final int number = i;
            new Thread(() -> {
                try {
                    productor.sendMsg("msg"  + number);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        for (int i=0; i<15; i++)  {
            new Thread(() -> {
                try {
                    consumer.receiver();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

        Thread.sleep(100000L);

    }


}
