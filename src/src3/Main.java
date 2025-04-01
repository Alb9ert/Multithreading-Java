package src3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Main thread");

        int[] arr = {1, 2, 3, 4, 5, 6};

        Square s1 = new Square(arr);
        Square s2 = new Square(arr);
        Thread t1 = new Thread(s1);
        Thread t2 = new Thread(s2);

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
            System.out.println(t1.getName() + " Result: " + s1.getSquare());
            System.out.println(t2.getName() + " Result: " + s2.getSquare());
            int result = s1.getSquare() + s2.getSquare();
            System.out.println("Result: " + result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

class Square implements Runnable {
    int[]arr;
    int square = 0;

    public Square(int[] arr) {
        this.arr = arr;
    }

    public int getSquare() {
        return square;
    }

    @Override
    public void run() {
        System.out.println("Hello from " + Thread.currentThread().getName());

        int firstHalf[] = Arrays.copyOfRange(this.arr, 0, this.arr.length / 2);
        int secondHalf[] = Arrays.copyOfRange(this.arr, this.arr.length / 2, this.arr.length);

        if (Thread.currentThread().getName().equals("Thread-0")) {
            for(int num : firstHalf) {
                this.square += num * num;
            }
        } else {
            for(int num : secondHalf) {
                this.square += num * num;

            }
        }

    }
}