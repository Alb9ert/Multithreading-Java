package src14;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionExample {
    private int counter = 0;
    static final ReentrantLock lock = new ReentrantLock();
    static final Condition conditionLock = lock.newCondition();


    public ConditionExample(int counter) {
        this.counter = counter;
    }

    public void addNumber(int number) {
        this.counter += number;
    }

    public int getCounter() {
        return counter;
    }

    public static void main(String[] args) {
        ConditionExample conditionExample = new ConditionExample(0);

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                for (int i = 0; i < 10; i++) {
                    conditionLock.await();
                    conditionExample.addNumber(1);
                    System.out.println("plusOne: " + conditionExample.getCounter());
                    conditionLock.signal();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }

        });

        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
                conditionLock.signal();

                for (int i = 0; i < 10; i++) {
                    conditionLock.await();
                    conditionExample.addNumber(5);
                    System.out.println("plusFive: " + conditionExample.getCounter());
                    conditionLock.signal();
                }
            } catch (InterruptedException ex) {
                throw new RuntimeException("fuck");
            } finally {
                lock.unlock();
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
