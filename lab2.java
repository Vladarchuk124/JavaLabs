import java.util.*;
import java.text.*;

class JournalEntry {
    private String lastName;
    private String firstName;
    private Date birthDate;
    private String phone;
    private String address;

    public JournalEntry(String lastName, String firstName, Date birthDate, String phone, String address) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        return "Student: " + lastName + " " + firstName +
                ", Birth date: " + sdf.format(birthDate) +
                ", Phone: " + phone +
                ", Address: " + address;
    }
}

public class lab2 {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<JournalEntry> journal = new ArrayList<>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    public static void main(String[] args) {
        sdf.setLenient(false);
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add entry");
            System.out.println("2. Show all entries");
            System.out.println("0. Exit");
            System.out.print("Your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1" -> addEntry();
                case "2" -> showEntries();
                case "0" -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice, try again.");
            }
        }
    }

    private static void addEntry() {
        String lastName = inputWithValidation("Last name: ", "[A-Za-zА-Яа-яІіЇїЄєҐґ]+",
                "Last name must contain only letters!");
        String firstName = inputWithValidation("First name: ", "[A-Za-zА-Яа-яІіЇїЄєҐґ]+",
                "First name must contain only letters!");
        Date birthDate = inputDate("Birth date (dd.MM.yyyy): ");
        String phone = inputWithValidation("Phone (at least 10 digits): ", "\\d{10,}",
                "Phone must contain at least 10 digits!");
        String address = inputWithValidation("Address: ", ".+", "Address cannot be empty!");

        JournalEntry entry = new JournalEntry(lastName, firstName, birthDate, phone, address);
        journal.add(entry);
        System.out.println("Entry added successfully!");
    }

    private static void showEntries() {
        if (journal.isEmpty()) {
            System.out.println("No entries yet.");
        } else {
            System.out.println("\n=== Curator's Journal ===");
            for (int i = 0; i < journal.size(); i++) {
                System.out.println((i + 1) + ". " + journal.get(i));
            }
        }
    }

    private static String inputWithValidation(String prompt, String regex, String errorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.matches(regex)) {
                return input;
            }
            System.out.println(errorMessage);
        }
    }

    private static Date inputDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                return sdf.parse(input);
            } catch (ParseException e) {
                System.out.println("Invalid date format! Use dd.MM.yyyy");
            }
        }
    }
}
