import java.util.Scanner;

/**
 * QuadraticEquationSolver is a program that computes the largest root of
 * a quadratic equation using the provided root method.
 */
public class QuadraticEquationSolver {

    /**
     * The main method prompts the user for coefficients A, B, and C of the
     * quadratic equation, computes the largest root if possible, and prints
     * the result. If there's an error, it displays an appropriate error message.
     * The program continues to prompt for new equations until the user decides
     * to stop.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean repeat = true;

        while (repeat) {
            System.out.print("Enter the coefficients A, B, and C: ");
            double A = scanner.nextDouble();
            double B = scanner.nextDouble();
            double C = scanner.nextDouble();

            try {
                double largestRoot = root(A, B, C);
                System.out.println("The largest root is: " + largestRoot);
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.print("Do you want to enter another equation? (yes/no): ");
            String response = scanner.next().trim().toLowerCase();
            repeat = response.equals("yes");
        }

        scanner.close();
        System.out.println("Goodbye!");
    }

    /**
     * Returns the larger of the two roots of the quadratic equation
     * A*x*x + B*x + C = 0, provided it has any roots.  If A == 0 or
     * if the discriminant, B*B - 4*A*C, is negative, then an exception
     * of type IllegalArgumentException is thrown.
     *
     * @param A the coefficient of x^2
     * @param B the coefficient of x
     * @param C the constant term
     * @return the largest root of the quadratic equation
     * @throws IllegalArgumentException if A is 0 or the discriminant is negative
     */
    static public double root(double A, double B, double C) throws IllegalArgumentException {
        if (A == 0) {
            throw new IllegalArgumentException("A can't be zero.");
        } else {
            double disc = B * B - 4 * A * C;
            if (disc < 0)
                throw new IllegalArgumentException("Discriminant < zero.");
            return (-B + Math.sqrt(disc)) / (2 * A);
        }
    }
}
