package lab3;

import java.util.*;
import java.util.stream.Collectors;

public class BookController {
    private List<Book> books;
    private BookView view;

    public BookController(List<Book> books, BookView view) {
        this.books = books;
        this.view = view;
    }

    public void listByAuthor(String author) {
        List<Book> result = books.stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList());
        view.showBooks(result);
    }

    public void listByPublisher(String publisher) {
        List<Book> result = books.stream()
                .filter(b -> b.getPublisher().equalsIgnoreCase(publisher))
                .collect(Collectors.toList());
        view.showBooks(result);
    }

    public void listAfterYear(int year) {
        List<Book> result = books.stream()
                .filter(b -> b.getYear() > year)
                .collect(Collectors.toList());
        view.showBooks(result);
    }

    public void sortByPublisher() {
        books.sort(Comparator.comparing(Book::getPublisher));
        view.showBooks(books);
    }

    public void showAllBooks() {
        view.showBooks(books);
    }
}
