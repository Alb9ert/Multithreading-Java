package src4;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {
    final ReentrantLock lock = new ReentrantLock();
    private final Thread t1;
    private final Thread t2;

    public ReentrantLockExample() {
        t1 = new Thread(new Worker());
        t2 = new Thread(new Worker());
    }

    private void startThreads() {
        t1.start();
        t2.start();
    }

    public static void main(String[] args) {
        ReentrantLockExample example = new ReentrantLockExample();
        example.startThreads();
    }

    class Worker implements Runnable {
        @Override
        public void run() {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " is waiting for lock in method1");
            someWork();
            System.out.println(Thread.currentThread().getName() + " releasing lock in method1");
            lock.unlock();
        }

        private void someWork() {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " is waiting for lock in method2");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                System.out.println(Thread.currentThread().getName() + " releasing lock in method2");
                lock.unlock();
            }
        }
    }
}