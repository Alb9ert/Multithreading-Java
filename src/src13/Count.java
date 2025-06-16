package src13;

import java.util.concurrent.locks.ReentrantLock;

public class Count {
    static final ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        Thread t1 = new Thread(new Helper());
        Thread t2 = new Thread(new Helper());

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


static class Helper implements Runnable {

    @Override
    public void run() {
        lock.lock();

        System.out.println("Hello from " + Thread.currentThread().getName());

        for (int i = 0; i < 100; i++) {
            System.out.println(i);
        }
        lock.unlock();

    }
}

}