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
