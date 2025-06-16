/*(5%) Write a Java program that uses a semaphore to
synchronize access to a common resource between multiple
threads. Assume you have 3 threads that need to access a
shared resource called SharedConfig which can only be
accessed by one thread at a time. */


package src8;

import java.util.concurrent.Semaphore;

public class SharedConfig {
    static int recourse = 0;
    static Semaphore semaphore = new Semaphore(1);
    public static void main(String[] args) {
        Thread t1 = new Thread(new Helper());
        Thread t2 = new Thread(new Helper());
        Thread t3 = new Thread(new Helper());

        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            System.out.println(recourse);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}

class Helper implements Runnable {


    @Override
    public void run() {
        System.out.println("Hello from " + Thread.currentThread().getName());
        try {
            SharedConfig.semaphore.acquire();
            SharedConfig.recourse++;

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            SharedConfig.semaphore.release();
        }
    }
}