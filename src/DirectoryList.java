import java.io.File;
import java.util.Scanner;

/**
 * This program lists the files in a directory specified by
 * the user.  The user is asked to type in a directory name.
 * If the name entered by the user is not a directory, a
 * message is printed and the program ends.
 */
public class DirectoryList {


   public static void main(String[] args) {

      String directoryName;  // Directory name entered by the user.
      File directory;        // File object referring to the directory.
      String[] files;        // Array of file names in the directory.
      Scanner scanner;       // For reading a line of input from the user.

      scanner = new Scanner(System.in);  // scanner reads from standard input.

      System.out.print("Enter a directory name: ");
      directoryName = scanner.nextLine().trim();
      directory = new File(directoryName);

      if (directory.isDirectory() == false) {
          if (directory.exists() == false)
              System.out.println("There is no such directory!");
          else
              System.out.println("That file is not a directory.");
      } else {
          listFiles(directory);
      }

   } // end main()
   
   private static void listFiles(File directory) {
       File[] files = directory.listFiles();
       if (files != null) {
           for (File file : files) {
               if (file.isDirectory()) {
                   System.out.println("Directory: " + file.getAbsolutePath());
                   listFiles(file); // Recursive call to handle subdirectories
               } else {
                   System.out.println("File: " + file.getAbsolutePath());
               }
           }
       }
   }
} // end class DirectoryList
