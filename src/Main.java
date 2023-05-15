import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

import javax.swing.JFileChooser;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner filein = new Scanner(new File("src/words.txt"));

        HashSet<String> set = new HashSet<String>();

        while (filein.hasNext()) {
            String tk = filein.next();
            set.add(tk.toLowerCase());
        }

        filein.close();

        filein = new Scanner(getInputFileNameFromUser());

        filein.useDelimiter("[^a-zA-Z]+");

        while (filein.hasNext()) {
            String tk = filein.next();
            if (!set.contains(tk.toLowerCase())) {
                TreeSet<String> correct = corrections(tk, set);

                System.out.print(tk + ": ");

                if (correct.isEmpty())
                    System.out.println("(no suggestions)");
                else {
                    Iterator<String> itr = correct.iterator();

                    System.out.print(itr.next());

                    while (itr.hasNext())
                        System.out.print(", " + itr.next());

                    System.out.println();
                }
            }
        }

        filein.close();
    }

    /**
     * Lets the user select an input file using a standard file
     * selection dialog box. If the user cancels the dialog
     * without selecting a file, the return value is null.
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

    static TreeSet<String> corrections(String badWord, HashSet<String> dictionary) {
        TreeSet<String> result = new TreeSet<String>();

        for (int i = 0; i < badWord.length() - 1; i++) {
            String left = badWord.substring(0, i);
            String right = badWord.substring(i + 1);

            String newWord = left + right;

            if (dictionary.contains(newWord))
                result.add(newWord);
        }

        for (int i = 0; i < badWord.length(); i++) {
            String left = badWord.substring(0, i);
            String right = badWord.substring(i + 1);

            for (char ch = 'a'; ch <= 'z'; ch++) {
                String newWord = left + ch + right;

                if (dictionary.contains(newWord))
                    result.add(newWord);
            }
        }

        for (int i = 0; i < badWord.length(); i++) {
            String left = badWord.substring(0, i);
            String right = badWord.substring(i);

            for (char ch = 'a'; ch <= 'z'; ch++) {
                String newWord = left + ch + right;

                if (dictionary.contains(newWord))
                    result.add(newWord);
            }
        }

        for (int i = 0; i < badWord.length() - 1; i++) {
            char c1 = badWord.charAt(i);
            char c2 = badWord.charAt(i + 1);
            String left = badWord.substring(0, i);
            String right = badWord.substring(i + 2);

            String newWord = left + c2 + c1 + right;

            if (dictionary.contains(newWord))
                result.add(newWord);
        }

        for (int i = 0; i < badWord.length(); i++) {
            String left = badWord.substring(0, i);
            String right = badWord.substring(i);

            if (dictionary.contains(left) && dictionary.contains(right))
                result.add(left + " " + right);
        }

        return result;
    }
}