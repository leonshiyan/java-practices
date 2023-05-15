import java.io.*;
import java.util.*;
import javax.swing.JFileChooser;

public class Dictionary {
    public static void main(String args[]) throws FileNotFoundException {
        try {
            // Reading the words.txt file
            Scanner filein = new Scanner(new File("words.txt"));

            // New dictionary data structure
            HashSet<String> dictionary = new HashSet<>();
            while (filein.hasNext()) {
                String tk = filein.next();
                dictionary.add(tk.toLowerCase());
            }

            Scanner userFile = new Scanner(getInputFileNameFromUser());
            userFile.useDelimiter("[^a-zA-Z]+");

            while (userFile.hasNext()) {
                String word = userFile.next();
                String lowerCaseWord = word.toLowerCase();

                if (!dictionary.contains(lowerCaseWord)) {
                    TreeSet<String> suggestions = corrections(lowerCaseWord, dictionary);
                    System.out.println(word + ": " + suggestions);
                }
            }
        } catch (IOException e) {
            System.out.println("File not found - words.txt");
        }
    }

    static TreeSet<String> corrections(String badWord, HashSet<String> dictionary) {
        TreeSet<String> tree = new TreeSet<>();

        // Delete any one of the letters from the misspelled word
        for (int i = 0; i < badWord.length(); i++) {
            String s = badWord.substring(0, i) + badWord.substring(i + 1);
            if (dictionary.contains(s)) {
                tree.add(s);
            }
        }

        // Change any letter in the misspelled word to any other letter
        for (int i = 0; i < badWord.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                String s = badWord.substring(0, i) + ch + badWord.substring(i + 1);
                if (dictionary.contains(s)) {
                    tree.add(s);
                }
            }
        }

        // Insert any letter at any point in the misspelled word
        for (int i = 0; i <= badWord.length(); i++) {
            for (char ch = 'a'; ch <= 'z'; ch++) {
                String s = badWord.substring(0, i) + ch + badWord.substring(i);
                if (dictionary.contains(s)) {
                    tree.add(s);
                }
            }
        }

        // Swap any two neighboring characters in the misspelled word
        for (int i = 0; i < badWord.length() - 1; i++) {
            String s = badWord.substring(0, i) + badWord.substring(i + 1, i + 2) +
                    badWord.substring(i, i + 1) + badWord.substring(i + 2);
            if (dictionary.contains(s)) {
                tree.add(s);
            }
        }

        // Insert a space at any point in the misspelled word
        for (int i = 1; i < badWord.length(); i++) {
            String stringInput = badWord.substring(0, i) + " " + badWord.substring(i);
            String tempString = "";
            StringTokenizer tempWords = new StringTokenizer(stringInput);

            while (tempWords.hasMoreTokens()) {
                String stringWord1 = tempWords.nextToken();
                String stringWord2 = tempWords.nextToken();

                if (dictionary.contains(stringWord1) && dictionary.contains(stringWord2)) {
                    tempString = stringWord1 + " " + stringWord2;
                } else {
                    break;
                }
                tree.add(tempString);
            }
        }

        if (tree.isEmpty()) {
            tree.add("no suggestions");
        }

        return tree;

    }
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
