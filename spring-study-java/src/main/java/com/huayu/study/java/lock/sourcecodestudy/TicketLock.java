package com.huayu.study.java.lock.sourcecodestudy;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Ticket Lock 虽然解决了公平性的问题，但是多处理器系统上，每个进程/线程占用的处理器都在读写同一个变量serviceNum ，每次读写操作都必须在
 * 多个处理器缓存之间进行缓存同步，这会导致繁重的系统总线和内存的流量，大大降低系统整体的性能
 */
public class TicketLock {

    private AtomicInteger serverNum = new AtomicInteger();
    private AtomicInteger tickNum = new AtomicInteger();

    public int lock() {
        int myTickNum = tickNum.getAndIncrement();
        while(myTickNum != serverNum.get()) {
        }
        System.out.println("ThreadName:" + Thread.currentThread().getName());
        return myTickNum;
    }

    public void unlock(int myTickNum) {
        if (serverNum.get() == myTickNum) {
            serverNum.incrementAndGet();
        }
    }

    public static int i = 0;

    static class TestThread implements Runnable {

        private TicketLock lock;

        public TestThread(TicketLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            int myTicket  = lock.lock();
            try {
                i = i+1;
                System.out.println(i);
                Thread.sleep(100L);
            } catch (Exception  e) {
                System.out.println(e);
            }finally {
                lock.unlock(myTicket);
            }
        }
    }

    public static void main(String[] args) {
        TicketLock lock = new TicketLock();
        int i = 100;
        while(i-- !=0) {
            new Thread(new TicketLock.TestThread(lock)).start();
        }


    }
}
