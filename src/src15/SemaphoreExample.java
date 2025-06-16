package src15;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    public static Semaphore semaphore = new Semaphore(5);

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Helper()).start();

        }

    }


    static class Helper implements Runnable {
        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.println("Hello from " + Thread.currentThread().getName());
                for (long  i = 0; i < 1000000000; i++) {
                    i++;
                }
                for (long  i = 0; i < 1000000000; i++) {
                    i++;
                }
                for (long  i = 0; i < 1000000000; i++) {
                    i++;
                }
                semaphore.release();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }
}


