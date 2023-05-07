package SpellChecker;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JFileChooser;

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

            while (inputFileScanner.hasNext()) {
                String word = inputFileScanner.next().toLowerCase();
                if (!dictionary.contains(word)) {
                    System.out.println("Misspelled word: " + word);
                }
            }
            inputFileScanner.close();
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
}

