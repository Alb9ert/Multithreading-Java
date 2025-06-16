package src1;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main thread started!");

        // We create the thread objects
        Helper h1 = new Helper(4);
        Helper h2 = new Helper (5);
        Thread t1 = new Thread(h1);
        Thread t2 = new Thread(h2);

        System.out.println("t1 state: " + t1.getState());
        System.out.println("t2 state: " + t2.getState());


        // Fork-join approach
        // We start the threads (Forking)
        t1.start();
        t2.start();

        // We wait for the threads to finish (Joining)
        // Try catch block fordi join throws an exception
        try {
            t1.join();
            t2.join();
            int sum = h1.getSquare() + h2.getSquare();
            System.out.println("Result: " + sum);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Man thread ended!");
    }
}

class Helper implements Runnable {
    private int a = 0;
    private int square = 0;

    public Helper(int a) {
        this.a = a;
    }

    @Override
    public void run() {
        try {
            if (Thread.currentThread().getName().equals("Thread-0")) {
                System.out.println("sleeping... " + Thread.currentThread().getName());
                Thread.sleep(5000);
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getState());
        System.out.println("Hello from thread: " + Thread.currentThread().getName());
        this.square = a * a;
    }

    public int getSquare() {
        return square;
    }
}