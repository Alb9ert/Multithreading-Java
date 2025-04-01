package src2;

public class RaceCondition {
    static int counter = 0;
    public static void main(String[] args) {
        Thread t1 = new Thread(new Worker());
        Thread t2 = new Thread(new Worker());

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Counter: " + counter);

    }
}

class Worker implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "increasing the counter");

        for (int i = 0; i < 1000000; i++) {
            RaceCondition.counter++;
        }
        System.out.println(Thread.currentThread().getName() + " counter: " + RaceCondition.counter);
    }

}