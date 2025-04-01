package src6;

import java.util.concurrent.locks.ReentrantLock;


public class ATM {
    public static void main(String[] args) throws InterruptedException {
        BankAccount account = new BankAccount(500);

        Runnable depositTask = () -> account.deposit(100);
        Runnable withdrawalTask = () -> account.withdraw(200);

        Thread atm1 = new Thread(depositTask, "ATM 1");
        Thread atm2 = new Thread(withdrawalTask, "ATM 2");
        Thread atm3 = new Thread(depositTask, "ATM 3");
        Thread atm4 = new Thread(withdrawalTask, "ATM 4");
        Thread atm5 = new Thread(withdrawalTask, "ATM 5");

        atm1.start();
        atm2.start();
        atm3.start();
        atm4.start();
        atm5.start();

        atm1.join();
        atm2.join();
        atm3.join();
        atm4.join();
        atm5.join();

        System.out.println("Final balance: " + account.getBalance());
    }
}


class BankAccount {
    private int balance;
    private final ReentrantLock lock = new ReentrantLock();

    public BankAccount(int initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(int amount) {
        lock.lock();
        try {
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " deposited: " + amount + ", New Balance: " + balance);
        } finally {
            lock.unlock();
        }
    }

    public boolean withdraw(int amount) {
        lock.lock();
        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + " withdrew: " + amount + ", New Balance: " + balance);
                return true;
            } else {
                System.out.println(Thread.currentThread().getName() + " attempted to withdraw: " + amount + " but insufficient funds.");
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public int getBalance() {
        return balance;
    }
}
