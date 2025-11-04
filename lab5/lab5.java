import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class lab5 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice = -1;
        BookController controller = new BookController(BookSamples.getBooks(), new BookView());

        while (choice != 0) {
            System.out.println("\n=== ГОЛОВНЕ МЕНЮ ===");
            System.out.println("1. Рядок з максимальною кількістю слів у файлі");
            System.out.println("2. Simple OOP (книги, серіалізація)");
            System.out.println("3. Шифрування/дешифрування файлу");
            System.out.println("4. Підрахунок частоти тегів на сторінці");
            System.out.println("0. Вихід");
            System.out.print("Ваш вибір: ");

            try {
                choice = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Помилка: введіть ціле число!");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            switch (choice) {
                case 1 -> MaxWordsInLine.run();
                case 2 -> SimpleOOPMenu.run(controller);
                case 3 -> CryptoDemo.run();
                case 4 -> TagFrequency.run();
                case 0 -> System.out.println("Завершення програми...");
                default -> System.out.println("Невірний вибір!");
            }
        }
    }
}

/* ==================== ЗАВДАННЯ 1 ==================== */
class MaxWordsInLine {
    public static void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введіть шлях до файлу: ");
        String filePath = sc.nextLine();

        try {
            String maxLine = getLineWithMaxWords(filePath);
            if (maxLine == null)
                System.out.println("Файл порожній або не знайдено рядків.");
            else
                System.out.println("Рядок з максимальною кількістю слів:\n" + maxLine);
        } catch (IOException e) {
            System.out.println("Помилка роботи з файлом: " + e.getMessage());
        }
    }

    public static String getLineWithMaxWords(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        String maxLine = null;
        int maxWords = -1;

        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty()) continue;
                int count = trimmed.split("\\s+").length;
                if (count > maxWords) {
                    maxWords = count;
                    maxLine = line;
                }
            }
        }
        return maxLine;
    }
}

/* ==================== ЗАВДАННЯ 2 ==================== */
class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title, author, publisher;
    private int year, pages;
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
        return String.format("%-20s %-20s %-15s %4d %5d стр. %.2f грн",
                title, author, publisher, year, pages, price);
    }
}

class BookView {
    public void showBooks(List<Book> books) {
        if (books == null || books.isEmpty())
            System.out.println("Список порожній.");
        else
            books.forEach(System.out::println);
    }

    public void showMessage(String msg) {
        System.out.println(msg);
    }
}

class BookController {
    private List<Book> books;
    private BookView view;

    public BookController(List<Book> books, BookView view) {
        this.books = new ArrayList<>(books);
        this.view = view;
    }

    public void showAllBooks() { view.showBooks(books); }

    public void listByAuthor(String author) {
        view.showBooks(books.stream()
                .filter(b -> b.getAuthor().equalsIgnoreCase(author))
                .collect(Collectors.toList()));
    }

    public void listByPublisher(String publisher) {
        view.showBooks(books.stream()
                .filter(b -> b.getPublisher().equalsIgnoreCase(publisher))
                .collect(Collectors.toList()));
    }

    public void listAfterYear(int year) {
        view.showBooks(books.stream()
                .filter(b -> b.getYear() > year)
                .collect(Collectors.toList()));
    }

    public void sortByPublisher() {
        books.sort(Comparator.comparing(Book::getPublisher));
        view.showBooks(books);
    }

    public List<Book> getBooks() { return books; }

    public void setBooks(List<Book> books) {
        this.books = books;
        view.showMessage("Дані успішно завантажено з файлу.");
    }
}

class BookFileManager {
    public static void saveBooks(List<Book> books, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(books);
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Book> loadBooks(String fileName)
            throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Book>) ois.readObject();
        }
    }
}

class BookSamples {
    public static List<Book> getBooks() {
        return Arrays.asList(
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
    }
}

class SimpleOOPMenu {
    public static void run(BookController controller) {
        Scanner sc = new Scanner(System.in);
        int ch = -1;
        while (ch != 0) {
            System.out.println("\n=== МЕНЮ КНИГ ===");
            System.out.println("1. Показати всі книги");
            System.out.println("2. Список книг за автором");
            System.out.println("3. Список книг за видавництвом");
            System.out.println("4. Книги після року");
            System.out.println("5. Сортувати за видавництвом");
            System.out.println("6. Зберегти в файл");
            System.out.println("7. Завантажити з файлу");
            System.out.println("0. Назад");
            System.out.print("Ваш вибір: ");

            try {
                ch = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Введіть число!");
                sc.nextLine();
                continue;
            }
            sc.nextLine();

            try {
                switch (ch) {
                    case 1 -> controller.showAllBooks();
                    case 2 -> {
                        System.out.print("Автор: ");
                        controller.listByAuthor(sc.nextLine());
                    }
                    case 3 -> {
                        System.out.print("Видавництво: ");
                        controller.listByPublisher(sc.nextLine());
                    }
                    case 4 -> {
                        System.out.print("Рік: ");
                        controller.listAfterYear(sc.nextInt());
                        sc.nextLine();
                    }
                    case 5 -> controller.sortByPublisher();
                    case 6 -> {
                        System.out.print("Файл для збереження: ");
                        BookFileManager.saveBooks(controller.getBooks(), sc.nextLine());
                        System.out.println("Дані збережено.");
                    }
                    case 7 -> {
                        System.out.print("Файл для читання: ");
                        controller.setBooks(BookFileManager.loadBooks(sc.nextLine()));
                    }
                    case 0 -> System.out.println("Повернення до головного меню...");
                    default -> System.out.println("Невірний вибір!");
                }
            } catch (Exception e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }
}

/* ==================== ЗАВДАННЯ 3 ==================== */
class EncryptionWriter extends FilterWriter {
    private final int key;
    protected EncryptionWriter(Writer out, char keyChar) {
        super(out);
        this.key = keyChar;
    }

    @Override
    public void write(int c) throws IOException {
        super.write(c + key);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = off; i < off + len; i++)
            cbuf[i] = (char) (cbuf[i] + key);
        super.write(cbuf, off, len);
    }
}

class DecryptionReader extends FilterReader {
    private final int key;
    protected DecryptionReader(Reader in, char keyChar) {
        super(in);
        this.key = keyChar;
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int count = super.read(cbuf, off, len);
        if (count == -1) return -1;
        for (int i = off; i < off + count; i++)
            cbuf[i] = (char) (cbuf[i] - key);
        return count;
    }
}

class CryptoDemo {
    public static void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Вхідний файл: ");
        String inFile = sc.nextLine();
        System.out.print("Зашифрований файл: ");
        String encFile = sc.nextLine();
        System.out.print("Розшифрований файл: ");
        String decFile = sc.nextLine();
        System.out.print("Ключовий символ: ");
        char key = sc.nextLine().charAt(0);

        try {
            encrypt(inFile, encFile, key);
            System.out.println("Файл зашифровано → " + encFile);

            decrypt(encFile, decFile, key);
            System.out.println("Файл розшифровано → " + decFile);
        } catch (IOException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static void encrypt(String inFile, String outFile, char key) throws IOException {
        try (FileInputStream fis = new FileInputStream(inFile);
             FileOutputStream fos = new FileOutputStream(outFile)) {
            int b;
            while ((b = fis.read()) != -1) {
                fos.write((b + key) & 0xFF); 
            }
        }
    }

    private static void decrypt(String inFile, String outFile, char key) throws IOException {
        try (FileInputStream fis = new FileInputStream(inFile);
             FileOutputStream fos = new FileOutputStream(outFile)) {
            int b;
            while ((b = fis.read()) != -1) {
                fos.write((b - key) & 0xFF); 
            }
        }
    }
}

/* ==================== ЗАВДАННЯ 4 ==================== */
class TagFrequency {
    public static void run() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Введіть URL (наприклад, https://example.com): ");
        String urlStr = sc.nextLine();

        try {
            String html = download(urlStr);
            Map<String, Integer> map = countTags(html);

            System.out.println("\nТеги за назвою:");
            map.keySet().stream().sorted()
                    .forEach(tag -> System.out.printf("%-15s : %d%n", tag, map.get(tag)));

            System.out.println("\nТеги за частотою:");
           map.entrySet().stream()
                    .sorted(Comparator.<Map.Entry<String, Integer>>comparingInt(Map.Entry::getValue)
                    .thenComparing(Map.Entry::getKey))
                    .forEach(e -> System.out.printf("%-15s : %d%n", e.getKey(), e.getValue()));


        } catch (IOException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    private static String download(String urlStr) throws IOException {
        StringBuilder sb = new StringBuilder();
        URL url = new URL(urlStr);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append("\n");
        }
        return sb.toString();
    }

    private static Map<String, Integer> countTags(String html) {
        Map<String, Integer> freq = new HashMap<>();
        Pattern p = Pattern.compile("<\\s*([a-zA-Z][a-zA-Z0-9]*)[^>]*>");
        Matcher m = p.matcher(html);
        while (m.find()) {
            String tag = m.group(1).toLowerCase();
            freq.put(tag, freq.getOrDefault(tag, 0) + 1);
        }
        return freq;
    }
}
