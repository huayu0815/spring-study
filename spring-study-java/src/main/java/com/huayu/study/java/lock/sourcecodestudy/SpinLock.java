package com.huayu.study.java.lock.sourcecodestudy;

import java.util.concurrent.atomic.AtomicReference;

public class SpinLock {


    private AtomicReference<Thread> owner = new AtomicReference<>();

    public void lock() {
        Thread thread = Thread.currentThread();
        while(!owner.compareAndSet(null, thread)) {
        }
        System.out.println("ThreadName:" + Thread.currentThread().getName());
    }

    public void unlock() {
        Thread thread = Thread.currentThread();
        owner.compareAndSet(thread, null);
    }

    public static int i = 0;

    static class TestThread implements Runnable {

        private SpinLock lock;

        public TestThread(SpinLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            lock.lock();
            try {
                i = i+1;
                System.out.println(i);
                Thread.sleep(100L);
            } catch (Exception  e) {
                System.out.println(e);
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        SpinLock spinLock = new SpinLock();
        int i = 100;
        while(i-- !=0) {
            new Thread(new TestThread(spinLock)).start();
        }


    }
}
