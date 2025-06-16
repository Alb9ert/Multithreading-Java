package src16;

import src2.RaceCondition;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicExample {
    AtomicInteger counter = new AtomicInteger();

    public void addToCounter(int counter) {
        this.counter.addAndGet(counter);
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    public AtomicExample(AtomicInteger counter) {

    }

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(0);
        AtomicExample atomicExample = new AtomicExample(atomicInteger);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                atomicExample.addToCounter(1);
                System.out.println(Thread.currentThread().getName() + " with: " + atomicExample.getCounter());


            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                atomicExample.addToCounter(1);
                System.out.println(Thread.currentThread().getName() + " with: " + atomicExample.getCounter());


            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

}
