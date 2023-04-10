/**
 *The SortingComparison class compares the time it takes for the Selection Sort algorithm and
 *the built-in Arrays.sort() method to sort an array of random integers.
 *This class generates two identical arrays of random integers, sorts one using the Selection Sort
 *algorithm, and sorts the other using the Arrays.sort() method. It then calculates and prints
 *the time it takes to sort each array.
 */

import java.util.Arrays;

public class SortingComparison {
	// Constants
	private static final int ARRAY_SIZE = 100000; // Change this value to 10000 or 100000 for different tests

	/**
	 * The main method creates an instance of the SortingComparison class and runs a test with a 
	 * specified array size.
	 * 
	 * @param args An array of command-line arguments.
	 */
	public static void main(String[] args) {
	    SortingComparison sc = new SortingComparison();
	    sc.runTest(ARRAY_SIZE);
	}

	/**
	 * The runTest method generates two identical arrays of random integers, sorts one using the 
	 * Selection Sort algorithm, and sorts the other using the Arrays.sort() method. It then 
	 * calculates and prints the time it takes to sort each array.
	 * 
	 * @param arraySize The size of the array to be sorted.
	 */
	public void runTest(int arraySize) {
	    
	    // Create two identical arrays
	    int[] array1 = new int[arraySize];
	    int[] array2 = new int[arraySize];
	    for (int i = 0; i < arraySize; i++) {
	        int randomValue = (int) (Integer.MAX_VALUE * Math.random());
	        array1[i] = randomValue;
	        array2[i] = randomValue;
	    }

	    // Time selection sort
	    long startTime1 = System.currentTimeMillis();
	    selectionSort(array1);
	    long endTime1 = System.currentTimeMillis();
	    long elapsedTime1 = endTime1 - startTime1;

	    // Time Arrays.sort()
	    long startTime2 = System.currentTimeMillis();
	    Arrays.sort(array2);
	    long endTime2 = System.currentTimeMillis();
	    long elapsedTime2 = endTime2 - startTime2;

	    // Print the results
	    System.out.println("Selection Sort Time: " + elapsedTime1 + " ms");
	    System.out.println("Arrays.sort() Time: " + elapsedTime2 + " ms");
	}

	/**
	 * The selectionSort method implements the Selection Sort algorithm to sort an array of integers 
	 * in ascending order.
	 * 
	 * @param A The array to be sorted.
	 */
	static void selectionSort(int[] A) {
	    // Sort A into increasing order, using selection sort
	    
	    for (int lastPlace = A.length-1; lastPlace > 0; lastPlace--) {
	        // Find the largest item among A[0], A[1], ...,
	        // A[lastPlace], and move it into position lastPlace 
	        // by swapping it with the number that is currently 
	        // in position lastPlace.

	        int maxLoc = 0;  // Location of largest item seen so far.

	        for (int j = 1; j <= lastPlace; j++) {
	            if (A[j] > A[maxLoc]) {
	                // Since A[j] is bigger than the maximum we've seen
	                // so far, j is the new location of the maximum value
	                // we've seen so far.
	                maxLoc = j;  
	            }
	        }

	        int temp = A[maxLoc];  // Swap largest item with A[lastPlace].
	        A[maxLoc] = A[lastPlace];
	        A[lastPlace] = temp;

	    }  // end of for
	}
}