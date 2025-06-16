package src9;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class OddAndEven {
    private static int counter = 1;
    private static final int MAX = 100;
    private static final ReentrantLock lock = new ReentrantLock();
    private static final Condition oddTurn = lock.newCondition();
    private static final Condition evenTurn = lock.newCondition();
    private static boolean isOddTurn = true; // Start with odd thread

    public static void main(String[] args) {
        System.out.println("Main thread started!");

        Thread t1 = new Thread(new Helper(true), "OddThread");
        Thread t2 = new Thread(new Helper(false), "EvenThread");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Main thread ended!");
    }

    static class Helper implements Runnable {
        private final boolean isOdd;

        public Helper(boolean isOdd) {
            this.isOdd = isOdd;
        }

        @Override
        public void run() {
            while (true) {
                lock.lock();
                try {
                    if (counter > MAX) break;

                    if (isOdd) {
                        while (!isOddTurn) {
                            oddTurn.await();
                        }
                        if (counter > MAX) break;
                        System.out.println("Odd: " + counter);
                        counter++;
                        isOddTurn = false;
                        evenTurn.signal();
                    } else {
                        while (isOddTurn) {
                            evenTurn.await();
                        }
                        if (counter > MAX) break;
                        System.out.println("Even: " + counter);
                        counter++;
                        isOddTurn = true;
                        oddTurn.signal();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }
    }
}
