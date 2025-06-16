package src10;



public class Stadium {
    public static void main(String[] args) {
        Counter counter = new Counter();

        Runnable turnstileTask = () -> {
            for (int i = 0; i < 10; i++) {
                counter.increment(Thread.currentThread().getName());
            }
        };

        Thread t1 = new Thread(turnstileTask, "Turnstile 1");
        Thread t2 = new Thread(turnstileTask, "Turnstile 2");
        Thread t3 = new Thread(turnstileTask, "Turnstile 3");

        t1.start();
        t2.start();
        t3.start();
    }
}

class Counter {
    private int count = 0;

    public synchronized void increment(String turnstile) {
        count++;
        System.out.println("Ticket sold at " + turnstile + ", Amount: " + count);
    }

    public int getCount() {
        return count;
    }
}