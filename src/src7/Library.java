package src7;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReadWriteLock;

public class Library {
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private Book[] books;

    public Library(int numBooks) {
        books = new Book[numBooks];
    }

    public void checkAvailability(int bookId) {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + " checked availability of book " + books[bookId].getTitle() + ": " + (books[bookId].isAvalaibility() ? "Available" : "Not Available"));
        } finally {
            lock.readLock().unlock();
        }
    }

    public void changeAvailability(int bookId, boolean isAvailable) {
        lock.writeLock().lock();
        try {
            books[bookId].setAvalaibility(isAvailable);
            System.out.println(Thread.currentThread().getName() + " changed availability of book " + books[bookId].getTitle() + " to: " + (isAvailable ? "Available" : "Not Available"));
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void addBook(int bookId, Book book) {
        books[bookId] = book;
    }

    public static void main(String[] args) throws InterruptedException {
        Library library = new Library(2);
        library.addBook(0, new Book("Harry Potter", true));
        library.addBook(1, new Book("Star Wars", false));

        Thread student1 = new Thread(new StudentTask(library, 0), "Student 1");
        Thread student2 = new Thread(new StudentTask(library, 1), "Student 2");
        Thread librarian = new Thread(new LibrarianTask(library, 0), "Librarian");

        student1.start();
        student2.start();
        librarian.start();

        student1.join();
        student2.join();
        librarian.join();
    }
}

class Book {
    private String title;
    private boolean avalaibility;

    public Book(String title, boolean avalaibility) {
        this.title = title;
        this.avalaibility = avalaibility;
    }

    public String getTitle() {
        return title;
    }

    public boolean isAvalaibility() {
        return avalaibility;
    }

    public void setAvalaibility(boolean avalaibility) {
        this.avalaibility = avalaibility;
    }
}

class StudentTask implements Runnable {
    private final Library library;
    private final int bookId;

    public StudentTask(Library library, int bookId) {
        this.library = library;
        this.bookId = bookId;
    }

    @Override
    public void run() {
        library.checkAvailability(bookId);
    }
}

class LibrarianTask implements Runnable {
    private final Library library;
    private final int bookId;

    public LibrarianTask(Library library, int bookId) {
        this.library = library;
        this.bookId = bookId;
    }

    @Override
    public void run() {
        library.changeAvailability(bookId, false);
        library.changeAvailability(bookId, true);
    }
}