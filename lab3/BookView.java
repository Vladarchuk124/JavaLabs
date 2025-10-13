package lab3;

import java.util.List;

public class BookView {
    public void showBooks(List<Book> books) {
        if (books.isEmpty()) {
            System.out.println("Sorry, no books found.");
        } else {
            books.forEach(System.out::println);
        }
    }

    public void showMessage(String message) {
        System.out.println(message);
    }
}
