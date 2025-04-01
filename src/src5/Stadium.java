package src5;

public class Stadium {
    static int counter = 0;
    private final Thread t1;
    private final Thread t2;
    private final Thread t3;

    public Stadium() {
        t1 = new Thread(new Stadium.Worker());
        t2 = new Thread(new Stadium.Worker());
        t3 = new Thread(new Stadium.Worker());

    }

    private void startThreads() {
        t1.start();
        t2.start();
        t3.start();

        try {
            t1.join();
            t2.join();
            t3.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Stadium stadium = new Stadium();
        stadium.startThreads();
        System.out.println("Result: " + counter);
    }

    class Worker implements Runnable {
        @Override
        public void run () {
            synchronized (this) {
                for (int i = 0; i < 5; i++) {
                    counter++;
                }
                System.out.print("Hello from " + Thread.currentThread().getName() + "incriment with: " + counter + "\n");
            }

        }
    }

}
