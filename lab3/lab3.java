package lab3;

import java.util.*;

public class lab3 {
    public static void main(String[] args) {
        List<Book> books = Arrays.asList(
                new Book("The City", "V. Pidmohylnyi", "Kyiv", 1928, 320, 250),
                new Book("The Tiger Trappers", "I. Bahrianyi", "Kharkiv", 1944, 290, 210),
                new Book("The Kaidash Family", "I. Nechui-Levytskyi", "Lviv", 1879, 180, 150),
                new Book("Zakhar Berkut", "I. Franko", "Kyiv", 1883, 240, 180),
                new Book("The Black Council", "P. Kulish", "Kyiv", 1857, 300, 200),
                new Book("Maria", "U. Samchuk", "Kharkiv", 1934, 250, 190),
                new Book("The Garden of Gethsemane", "I. Bahrianyi", "Lviv", 1950, 350, 260),
                new Book("Intermezzo", "M. Kotsiubynskyi", "Vinnytsia", 1908, 80, 100),
                new Book("Marusia", "H. Kvitka-Osnovianenko", "Kharkiv", 1834, 120, 130),
                new Book("The Forest Song", "Lesya Ukrainka", "Kyiv", 1911, 150, 170)
        );

        BookView view = new BookView();
        BookController controller = new BookController(books, view);

        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Show all books");
            System.out.println("2. List books by author");
            System.out.println("3. List books by publisher");
            System.out.println("4. List books published after a certain year");
            System.out.println("5. Sort books by publishers");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> controller.showAllBooks();
                case 2 -> {
                    System.out.print("Enter author: ");
                    String author = sc.nextLine();
                    controller.listByAuthor(author);
                }
                case 3 -> {
                    System.out.print("Enter publisher: ");
                    String publisher = sc.nextLine();
                    controller.listByPublisher(publisher);
                }
                case 4 -> {
                    System.out.print("Enter year: ");
                    int year = sc.nextInt();
                    controller.listAfterYear(year);
                }
                case 5 -> controller.sortByPublisher();
                case 0 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice!");
            }

        } while (choice != 0);
    }
}
