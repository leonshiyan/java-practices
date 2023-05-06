package SpellChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SpellChecker {
    public static void main(String[] args) {
        Set<String> dictionary = new HashSet<>();

        try {
            // Read words from file and store them in the set
        	Scanner filein = new Scanner(new File("src/SpellChecker/words.txt"));
            while (filein.hasNext()) {
                String word = filein.next().toLowerCase();
                dictionary.add(word);
            }
            filein.close();

            // Check the size of the set
            System.out.println("Dictionary size: " + dictionary.size());

            // Check if a word is in the dictionary
            String wordToCheck = "hello";
            if (dictionary.contains(wordToCheck.toLowerCase())) {
                System.out.println(wordToCheck + " is spelled correctly.");
            } else {
                System.out.println(wordToCheck + " is misspelled.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }
}
