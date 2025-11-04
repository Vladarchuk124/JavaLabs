package lab6;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Translator {
    private Map<String, String> dictionary;

    public Translator() {
        dictionary = new HashMap<>();
    }

    public void addWord(String english, String ukrainian) {
        dictionary.put(english.toLowerCase(), ukrainian.toLowerCase());
    }

    public String translatePhrase(String phrase) {
        String[] words = phrase.toLowerCase().split(" ");
        StringBuilder translated = new StringBuilder();

        for (String word : words) {
            String translation = dictionary.get(word);
            if (translation != null) {
                translated.append(translation).append(" ");
            } else {
                translated.append("[").append(word).append("] ");
            }
        }

        return translated.toString().trim();
    }
}

public class lab6 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Translator translator = new Translator();

        translator.addWord("hello", "привіт");
        translator.addWord("world", "світ");
        translator.addWord("cat", "кіт");
        translator.addWord("dog", "собака");

        System.out.println("Хочете додати власні слова до словника? (yes/no)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("yes")) {
            while (true) {
                System.out.print("Введіть англійське слово (або 'exit' для завершення): ");
                String eng = scanner.nextLine();
                if (eng.equalsIgnoreCase("exit")) break;

                System.out.print("Введіть переклад українською: ");
                String ukr = scanner.nextLine();
                translator.addWord(eng, ukr);
            }
        }

        System.out.print("Введіть фразу англійською для перекладу: ");
        String phrase = scanner.nextLine();

        String translated = translator.translatePhrase(phrase);
        System.out.println("Переклад: " + translated);
    }
}

