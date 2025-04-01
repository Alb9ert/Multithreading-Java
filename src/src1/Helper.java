package src1;

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