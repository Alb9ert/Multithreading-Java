package src12;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Library {
    private Book[] books;
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    public Library(Book[] books) {
        this.books = books;
    }

    public void readBooks(String student) {
        System.out.println(student + " is reading books");
        rwLock.readLock().lock();
        try {
            for (Book book : books) {
                System.out.println(student + " reads: " + book.title + " - Available: " + book.availability);
            }
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void updateBook(Book book, boolean availability, String librarian) {
        System.out.println(librarian + " is updating book: " + book.title);
        rwLock.writeLock().lock();
        try {
            for (int i = 0; i < books.length; i++) {
                if (book == books[i]) {
                    books[i].availability = availability;
                    System.out.println(librarian + " updated " + book.title + " availability to " + availability);
                }
            }
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public static void main(String[] args) {
        Book potterOne = new Book("Harry Potter", true);
        Book fire = new Book("Fire", false);

        Library library = new Library(new Book[]{potterOne, fire});

        Thread student1 = new Thread(() -> library.readBooks("Student-1"), "Student-1");
        Thread student2 = new Thread(() -> library.readBooks("Student-2"), "Student-2");
        Thread librarian = new Thread(() -> library.updateBook(fire, true, "Librarian"), "Librarian");

        student1.start();
        student2.start();
        librarian.start();

        // Wait for all threads to finish before exiting main
        try {
            student1.join();
            student2.join();
            librarian.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Book {
    String title;
    Boolean availability;

    public Book(String title, Boolean availability) {
        this.title = title;
        this.availability = availability;
    }
}
