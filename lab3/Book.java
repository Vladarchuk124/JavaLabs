package lab3;

public class Book {
    private String title;
    private String author;
    private String publisher;
    private int year;
    private int pages;
    private double price;

    public Book(String title, String author, String publisher, int year, int pages, double price) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.year = year;
        this.pages = pages;
        this.price = price;
    }

    public String getAuthor() { return author; }
    public String getPublisher() { return publisher; }
    public int getYear() { return year; }

    @Override
    public String toString() {
        return String.format("%-20s %-15s %-15s %4d %5d page. %.2f uah",
                title, author, publisher, year, pages, price);
    }
}
