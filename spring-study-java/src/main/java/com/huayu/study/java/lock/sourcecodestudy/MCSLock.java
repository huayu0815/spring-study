package com.huayu.study.java.lock.sourcecodestudy;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

public class MCSLock {

    private class MCSNode {
        private volatile MCSNode next;//第一次没有设置volatile,导致死循环
        private volatile Boolean isBlock = true;
    }

    private volatile MCSNode queue; // 指向最后一个申请锁的MCSNode
    private AtomicReferenceFieldUpdater<MCSLock, MCSNode> updater = AtomicReferenceFieldUpdater
            .newUpdater(MCSLock.class, MCSNode.class, "queue");

    public MCSNode lock() {
        MCSNode currentThread = new MCSNode();
        MCSNode predecessor = updater.getAndSet(this, currentThread);//step 1
        if (predecessor != null) {
            predecessor.next = currentThread; //step2
            while (currentThread.isBlock) {
                //System.out.println("ThreadNameCircle:" + Thread.currentThread().getName());
            }
        } else {
            currentThread.isBlock = false;
        }
        System.out.println("ThreadNameWork:" + Thread.currentThread().getName());
        return currentThread;
    }

    public void unlock(MCSNode currentThread) {
        if (currentThread.isBlock) {
            return;
        }
        if (currentThread.next == null) {
            if (updater.compareAndSet(this, currentThread, null)) {
                return;
            } else {
                // 突然有人排在自己后面了，可能还不知道是谁，下面是等待后续者
                // 这里之所以要忙等是因为：step 1执行完后，step 2可能还没执行完
                while (currentThread.next == null) { // step 5
                }
            }
        }
        currentThread.next.isBlock = false;
        currentThread.next = null;

    }

    public static int i = 0;

    static class TestThread implements Runnable {

        private MCSLock lock;

        public TestThread(MCSLock lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            MCSNode MCSNode = lock.lock();
            try {
                i = i+1;
                System.out.println(i);
                Thread.sleep(20L);
            } catch (Exception  e) {
                System.out.println(e);
            }finally {
                lock.unlock(MCSNode);
            }
        }
    }

    public static void main(String[] args) {
        MCSLock lock = new MCSLock();
        int i = 100;
        while(i-- !=0) {
            new Thread(new MCSLock.TestThread(lock)).start();
        }


    }
}
