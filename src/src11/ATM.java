package src11;

import java.util.concurrent.locks.ReentrantLock;

public class ATM {
    private final ReentrantLock lock = new ReentrantLock();
    private int balance = 0;

    public int getBalance() {
        return balance;
    }

    public ATM(int balance) {
        this.balance = balance;
    }

    public void withdraw(int amount) {
        lock.lock();

        try {
            if (this.balance - amount >= 0) {
                this.balance -= amount;
                System.out.println("Amount withdrawed: " + amount + ", Bank balance: " + this.balance);
            } else {
                System.out.println("Invalid withdrawal amount");
            }

        } finally {
            lock.unlock();
        }
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            this.balance += amount;
            System.out.println("Amount deposited: " + amount + ", Bank balance: " + this.balance);
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ATM balance = new ATM(0);
        System.out.println("Balance init at: " + balance.getBalance());
        Thread atm1 = new Thread(() -> {
            balance.withdraw(5);
            balance.deposit(10);
        });

        Thread atm2 = new Thread(() -> {
            balance.withdraw(5);
            balance.deposit(10);
        });

        Thread atm3 = new Thread(() -> {
            balance.withdraw(5);
            balance.deposit(10);
        });

        atm1.start();
        atm2.start();
        atm3.start();

    }
}
