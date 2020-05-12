package com.huayu.study.java.lock.sourcecodestudy;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class CLHLock {

    private class CLHNode {
        private volatile Boolean isLock = true;
    }

    private volatile CLHNode tail; // 指向最后一个申请锁的MCSNode
    private AtomicReferenceFieldUpdater<CLHLock, CLHNode> updater = AtomicReferenceFieldUpdater
            .newUpdater(CLHLock.class, CLHNode.class, "tail");

    public CLHNode lock() {
        CLHNode currentThread = new CLHNode();
        CLHNode preNode = updater.getAndSet(this, currentThread);//step 1
        if (preNode != null) {
            while (preNode.isLock) {
                //System.out.println("ThreadNameCircle:" + Thread.currentThread().getName());
            }
        }
        System.out.println("ThreadNameWork:" + Thread.currentThread().getName());
        return currentThread;
    }

    public void unlock(CLHNode currentThread) {
        if (!updater.compareAndSet(this, currentThread, null)) {
            currentThread.isLock = false;
        }

    }

    public static int i = 0;

    static class TestThread implements Runnable {

        private CLHLock lock;

        public TestThread(CLHLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            CLHNode clhNode = lock.lock();
            try {
                i = i + 1;
                System.out.println(i);
                Thread.sleep(20L);
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                lock.unlock(clhNode);
            }
        }
    }

    public static void main(String[] args) {
        CLHLock lock = new CLHLock();
        int i = 100;
        while (i-- != 0) {
            new Thread(new CLHLock.TestThread(lock)).start();
        }


    }
}
