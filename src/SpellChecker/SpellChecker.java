package SpellChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JFileChooser;

/**
 * A simple spell checker program that checks the spelling of words in a given file
 * against a dictionary and suggests possible correct spellings for misspelled words.
 */
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

            // Let the user select an input file
            File inputFile = getInputFileNameFromUser();
            if (inputFile == null) {
                System.out.println("No file selected.");
                return;
            }

            // Read words from the selected file and check against the dictionary
            Scanner inputFileScanner = new Scanner(inputFile);
            inputFileScanner.useDelimiter("[^a-zA-Z]+");

            Set<String> misspelledWords = new HashSet<>();

            while (inputFileScanner.hasNext()) {
                String word = inputFileScanner.next().toLowerCase();
                if (!dictionary.contains(word)) {
                    misspelledWords.add(word);
                }
            }
            inputFileScanner.close();

            // Generate corrections for misspelled words and print suggestions
            for (String misspelledWord : misspelledWords) {
                Set<String> suggestions = corrections(misspelledWord, dictionary);
                if (suggestions.isEmpty()) {
                    System.out.println(misspelledWord + ": (no suggestions)");
                } else {
                    System.out.println(misspelledWord + ": " + suggestions);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    /**
     * Lets the user select an input file using a standard file selection dialog box.
     * If the user cancels the dialog without selecting a file, the return value is null.
     */
    static File getInputFileNameFromUser() {
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setDialogTitle("Select File for Input");
        int option = fileDialog.showOpenDialog(null);
        if (option != JFileChooser.APPROVE_OPTION)
            return null;
        else
            return fileDialog.getSelectedFile();
    }

    /**
     * Generates a TreeSet<String> containing variations on the badWord that are contained in the dictionary.
     */
    static TreeSet<String> corrections(String badWord, Set<String> dictionary) {
        TreeSet<String> suggestions = new TreeSet<>();

        // Delete any one of the letters from the misspelled word
        for (int i = 0; i < badWord.length(); i++) {
            String correctedWord = badWord.substring(0, i) + badWord.substring(i + 1);
            if (dictionary.contains(correctedWord)) {
                suggestions.add(correctedWord);
            }
        }
        for (int i = 0; i < badWord.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                String correctedWord = badWord.substring(0, i) + ch + badWord.substring(i + 1);
                if (dictionary.contains(correctedWord)) {
                    suggestions.add(correctedWord);
                }
            }
        }

        // Insert any letter at any point in the misspelled word
        for (int i = 0; i <= badWord.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                String correctedWord = badWord.substring(0, i) + ch + badWord.substring(i);
                if (dictionary.contains(correctedWord)) {
                    suggestions.add(correctedWord);
                }
            }
        }

        // Swap any two neighboring characters in the misspelled word
        for (int i = 0; i < badWord.length() - 1; i++) {
            String correctedWord = badWord.substring(0, i) + badWord.charAt(i + 1) + badWord.charAt(i) + badWord.substring(i + 2);
            if (dictionary.contains(correctedWord)) {
                suggestions.add(correctedWord);
            }
        }

        // Insert a space at any point in the misspelled word (and check that both of the resulting words are in the dictionary)
        for (int i = 1; i < badWord.length(); i++) {
            String word1 = badWord.substring(0, i);
            String word2 = badWord.substring(i);
            if (dictionary.contains(word1) && dictionary.contains(word2)) {
                suggestions.add(word1 + " " + word2);
            }
        }

        return suggestions;
    }
}


