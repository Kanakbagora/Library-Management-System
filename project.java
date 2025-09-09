import java.util.ArrayList;
import java.util.Scanner;

// Book class
class Book {
    private int bookID;
    private String title;
    private String author;
    private boolean availability;

    // Constructor
    public Book(int bookID, String title, String author, boolean availability) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.availability = availability;
    }

    // Getters & Setters
    public int getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return availability;
    }

    public void setAvailable(boolean availability) {
        this.availability = availability;
    }

    @Override
    public String toString() {
        return "BookID: " + bookID +
               ", Title: " + title +
               ", Author: " + author +
               ", Available: " + (availability ? "Yes" : "No");
    }
}

// Borrower class
class Borrower {
    private int borrowerID;
    private String name;
    private ArrayList<Book> borrowedBooks;

    // Constructor
    public Borrower(int borrowerID, String name) {
        this.borrowerID = borrowerID;
        this.name = name;
        this.borrowedBooks = new ArrayList<>();
    }

    // Getters
    public int getBorrowerID() {
        return borrowerID;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Add borrowed book
    public void borrowBook(Book book) {
        borrowedBooks.add(book);
    }

    // Return borrowed book
    public void returnBook(Book book) {
        borrowedBooks.remove(book);
    }

    @Override
    public String toString() {
        return "BorrowerID: " + borrowerID + ", Name: " + name;
    }
}

// Main Library System
public class LibrarySystem {
    private static ArrayList<Book> books = new ArrayList<>();
    private static ArrayList<Borrower> borrowers = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n--- Library System Menu ---");
            System.out.println("1. Add Book");
            System.out.println("2. View Books");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. Search Book");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addBook();
                case 2 -> viewBooks();
                case 3 -> borrowBook();
                case 4 -> returnBook();
                case 5 -> searchBook();
                case 6 -> System.out.println("Exiting... Goodbye!");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);
    }

    // Add Book
    private static void addBook() {
        System.out.print("Enter Book ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Author: ");
        String author = sc.nextLine();

        books.add(new Book(id, title, author, true));
        System.out.println("Book added successfully!");
    }

    // View Books
    private static void viewBooks() {
        if (books.isEmpty()) {
            System.out.println("No books available.");
            return;
        }
        for (Book book : books) {
            System.out.println(book);
        }
    }

    // Borrow Book
    private static void borrowBook() {
        System.out.print("Enter Borrower ID: ");
        int borrowerID = sc.nextInt();
        sc.nextLine();
        Borrower borrower = findBorrower(borrowerID);

        if (borrower == null) {
            System.out.print("Borrower not found. Enter name to register: ");
            String name = sc.nextLine();
            borrower = new Borrower(borrowerID, name);
            borrowers.add(borrower);
        }

        System.out.print("Enter Book ID to borrow: ");
        int bookID = sc.nextInt();
        sc.nextLine();
        Book book = findBook(bookID);

        if (book == null) {
            System.out.println("Book not found!");
        } else if (!book.isAvailable()) {
            System.out.println("Book is already borrowed!");
        } else {
            book.setAvailable(false);
            borrower.borrowBook(book);
            System.out.println(borrower.getName() + " borrowed: " + book.getTitle());
        }
    }

    // Return Book
    private static void returnBook() {
        System.out.print("Enter Borrower ID: ");
        int borrowerID = sc.nextInt();
        sc.nextLine();
        Borrower borrower = findBorrower(borrowerID);

        if (borrower == null) {
            System.out.println("Borrower not found!");
            return;
        }

        System.out.print("Enter Book ID to return: ");
        int bookID = sc.nextInt();
        sc.nextLine();
        Book book = findBook(bookID);

        if (book == null) {
            System.out.println("Book not found!");
        } else if (!borrower.getBorrowedBooks().contains(book)) {
            System.out.println("This borrower didn't borrow this book!");
        } else {
            book.setAvailable(true);
            borrower.returnBook(book);
            System.out.println("Book returned successfully!");
        }
    }

    // Search Book
    private static void searchBook() {
        System.out.print("Enter Title or Author to search: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword) || book.getAuthor().toLowerCase().contains(keyword)) {
                System.out.println(book);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No books found matching the search.");
        }
    }

    // Utility: Find Book by ID
    private static Book findBook(int id) {
        for (Book book : books) {
            if (book.getBookID() == id) {
                return book;
            }
        }
        return null;
    }

    // Utility: Find Borrower by ID
    private static Borrower findBorrower(int id) {
        for (Borrower borrower : borrowers) {
            if (borrower.getBorrowerID() == id) {
                return borrower;
            }
        }
        return null;
    }
}
