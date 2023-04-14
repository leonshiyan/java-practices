/**
 * This program generates random sentences based on a set of grammar rules
 * expressed in BNF (Backus-Naur Form). The program includes methods to generate
 * sentences, noun phrases, and verb phrases, as well as arrays of strings
 * representing various parts of speech (proper nouns, common nouns, verbs,
 * etc.). The probabilities of generating certain elements (such as conjunctions,
 * transitive verbs, etc.) have been adjusted to avoid infinite recursion and
 * excessively long sentences.
 *
 * To run the program, simply compile the source file and run the resulting class
 * file from the command line. The program will generate and output one random
 * sentence every three seconds until it is halted (for example, by typing Control-C
 * in the terminal window where it is running).
 *
 * Note: This program does not use the optional ([xxx]) or repeated optional ([xxx]...)
 * elements in the BNF rules. These could be added to the program with additional
 * if statements or loops, if desired.
 *
 * @author Yan Shi
 * @version 1.0
 */
import java.util.Random;

public class RandomSentences {

    // Declare arrays of strings representing various parts of speech
    static final String[] properNouns = {"Fred", "Jane", "Richard Nixon", "Miss America"};
    static final String[] commonNouns = {"man", "woman", "fish", "elephant", "unicorn"};
    static final String[] determiners = {"a", "the", "every", "some"};
    static final String[] adjectives = {"big", "tiny", "pretty", "bald"};
    static final String[] intransitiveVerbs = {"runs", "jumps", "talks", "sleeps"};
    static final String[] transitiveVerbs = {"loves", "hates", "sees", "knows", "looks for", "finds"};
    static final String[] conjunctions = {"and", "or", "but", "because"};

    // Declare a Random object for generating random numbers
    static final Random random = new Random();

    /**
     * Main method. Runs the program by generating and outputting one random
     * sentence every three seconds until halted.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        while (true) {
            System.out.print("Generated sentence: ");
            sentence();
            System.out.println("\n");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                // Do nothing
            }
        }
    }

    /**
     * Generates a random sentence by calling the simpleSentence method and
     * possibly appending a conjunction and another sentence.
     */
    static void sentence() {
        simpleSentence();
        if (random.nextDouble() < 0.3) {
            System.out.print(" ");
            randomItem(conjunctions);
            System.out.print(" ");
            sentence();
        }
    }

    /**
     * Generates a random simple sentence by calling the nounPhrase and verbPhrase
     * methods.
     */
    static void simpleSentence() {
        nounPhrase();
        verbPhrase();
    }

    /**
     * Generates a random noun phrase by randomly selecting a proper noun or
     * constructing a phrase with a determiner, common noun, and possibly a modifier
     * and relative clause.
     */
    static void nounPhrase() {
        if (random.nextDouble() < 0.4) {
            randomItem(properNouns);
        } else {
            randomItem(determiners);
            if (random.nextDouble() < 0.6) {
                System.out.print(" ");
                randomItem(adjectives);
            }
            System.out.print(" ");
            randomItem(commonNouns);
            if (random.nextDouble() < 0.4) {
                System.out.print(" who ");
                verbPhrase();
            }
        }
    }

    /**
     * Generates a random verb phrase by randomly selecting an intransitive verb,
     * a transitive verb with a noun phrase, a form of "to be" with an adjective,
     * or a form of "to believe" with a simple sentence.
     */
    static void verbPhrase() {
        if (random.nextDouble() < 0.3) {
            System.out.print(" ");
            randomItem(intransitiveVerbs);
        } else if (random.nextDouble() < 0.5) {
            System.out.print(" ");
            randomItem(transitiveVerbs);
            System.out.print(" ");
            nounPhrase();
        } else if (random.nextDouble() < 0.7) {
            System.out.print(" is ");
            randomItem(adjectives);
        } else {
            System.out.print(" believes that ");
            simpleSentence();
        }
    }

    /**
     * Chooses a random item from an array of strings and prints it to the console.
     *
     * @param listOfStrings The array of strings to choose from.
     */
    static void randomItem(String[] listOfStrings) {
        System.out.print(listOfStrings[random.nextInt(listOfStrings.length)]);
    }
}
           
