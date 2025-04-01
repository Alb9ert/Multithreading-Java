package src2;

import java.util.concurrent.locks.ReentrantLock;


public class RaceCondition {
    static int counter = 0;
    final ReentrantLock lock = new ReentrantLock();
    private final Thread t1;
    private final Thread t2;

    public RaceCondition() {
        t1 = new Thread(new RaceCondition.Worker());
        t2 = new Thread(new RaceCondition.Worker());
    }

    private void startThreads() {
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        RaceCondition raceCondition = new RaceCondition();
        raceCondition.startThreads();
        System.out.println("Done");

        System.out.println("Counter: " + counter);

    }

    class Worker implements Runnable {

        @Override
        public void run() {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " increasing the counter");

            for (int i = 0; i < 100000; i++) {
                RaceCondition.counter++;
            }
            System.out.println(Thread.currentThread().getName() + " counter: " + RaceCondition.counter);
            lock.unlock();
        }

    }
}