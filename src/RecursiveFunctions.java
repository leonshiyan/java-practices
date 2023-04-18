/**
 * A class that contains two recursive functions to compute factorial and Fibonacci.
 */
public class RecursiveFunctions {
	/**
	 * Returns the factorial of the given non-negative integer using recursion.
	 * 
	 * @param n The non-negative integer to compute the factorial of.
	 * @return The factorial of n.
	 */
	public static int factorial(int n) {
		if (n == 0) {
	      return 1;
	    } else {
	      return n * factorial(n - 1);
	    }
	}
	/**
	 * Returns the nth Fibonacci number using recursion.
	 * 
	 * @param n The non-negative integer representing the nth Fibonacci number to compute.
	 * @return The nth Fibonacci number.
	 */
	public static int fibonacci(int n) {
	    if (n == 0 || n == 1) {
	      return 1;
	    } else {
	      return fibonacci(n - 1) + fibonacci(n - 2);
	    }
	 }
	
	/**
	 * Tests the factorial and fibonacci functions.
	 * 
	 * @param args Unused.
	 */
	public static void main(String[] args) {
		int n = 5;
	    System.out.println("Factorial of " + n + " is: " + factorial(n));
	    System.out.println("Fibonacci number at position " + n + " is: " + fibonacci(n));

	}

}
