/**
 * Student Name   : <Your Full Name>
 * Student ID     : <Your Student ID>
 * Course Code    : PROG2-9307-AY225
 * Assignment     : Midterm Lab 2 - Determinant Solver
 * Date           : 2026-03-16
 *
 * Description:
 *   Reads a 3x3 matrix from the user and computes its determinant by
 *   performing a cofactor expansion along the first row. The program
 *   prints each step, including the original matrix, the 2x2 minors,
 *   the cofactor terms, and the final determinant value.
 */

import java.util.Scanner;

public class DeterminantSolver {

    /**
     * Entry point for the program.
     * Prompts the user for a 3x3 matrix, then solves and prints step-by-step.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter a 3x3 matrix.
        System.out.println("Enter the 3x3 matrix values (row by row):");
        int[][] matrix = new int[3][3];

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                System.out.printf("  M[%d][%d] = ", row, col);
                matrix[row][col] = readInt(scanner);
            }
        }

        // Print the original matrix before computing the determinant.
        System.out.println();
        System.out.println("Original matrix:");
        printMatrix(matrix);

        // Compute and print the determinant with detailed cofactor expansion steps.
        int determinant = solveDeterminant(matrix);

        // Indicate if the matrix is singular (determinant is zero).
        if (determinant == 0) {
            System.out.println("\nThe matrix is SINGULAR — it has no inverse.");
        }

        scanner.close();
    }

    /**
     * Reads an integer from the scanner, retrying until a valid integer is entered.
     */
    private static int readInt(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("    Invalid number. Please enter an integer: ");
            }
        }
    }

    /**
     * Prints the matrix in a nice formatted grid.
     */
    private static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            System.out.print("| ");
            for (int value : row) {
                System.out.printf("%4d ", value);
            }
            System.out.println("|");
        }
    }

    /**
     * Computes the determinant of a 3x3 matrix using cofactor expansion along the first row.
     * Prints every step: the expansion formula, each minor, each cofactor term, and the final result.
     *
     * @param m a 3x3 matrix
     * @return the determinant value
     */
    public static int solveDeterminant(int[][] m) {
        // Ensure matrix is 3x3 before proceeding.
        if (m == null || m.length != 3 || m[0].length != 3) {
            throw new IllegalArgumentException("Matrix must be 3x3.");
        }

        System.out.println("\n--- Cofactor expansion along the first row ---");
        System.out.println("det(M) = M[0][0]*(M[1][1]*M[2][2] - M[1][2]*M[2][1])");
        System.out.println("       - M[0][1]*(M[1][0]*M[2][2] - M[1][2]*M[2][0])");
        System.out.println("       + M[0][2]*(M[1][0]*M[2][1] - M[1][1]*M[2][0])\n");

        // Extract first-row elements and prepare for terms printing.
        int a = m[0][0];
        int b = m[0][1];
        int c = m[0][2];

        // Compute each 2x2 minor for the first row.
        int minorA = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
        int minorB = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
        int minorC = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);

        // Print each minor with labels and the 2x2 submatrix.
        System.out.println("Minor for M[0][0]:");
        printMinor(new int[][]{{m[1][1], m[1][2]}, {m[2][1], m[2][2]}});
        System.out.printf("  Determinant = %d*%d - %d*%d = %d\n\n", m[1][1], m[2][2], m[1][2], m[2][1], minorA);

        System.out.println("Minor for M[0][1]:");
        printMinor(new int[][]{{m[1][0], m[1][2]}, {m[2][0], m[2][2]}});
        System.out.printf("  Determinant = %d*%d - %d*%d = %d\n\n", m[1][0], m[2][2], m[1][2], m[2][0], minorB);

        System.out.println("Minor for M[0][2]:");
        printMinor(new int[][]{{m[1][0], m[1][1]}, {m[2][0], m[2][1]}});
        System.out.printf("  Determinant = %d*%d - %d*%d = %d\n\n", m[1][0], m[2][1], m[1][1], m[2][0], minorC);

        // Compute cofactor terms (with alternating signs) and print them.
        int termA = a * minorA; // sign +
        int termB = -b * minorB; // sign -
        int termC = c * minorC; // sign +

        System.out.printf("Cofactor term for M[0][0]: +(%d * %d) = %d\n", a, minorA, termA);
        System.out.printf("Cofactor term for M[0][1]: -(%d * %d) = %d\n", b, minorB, termB);
        System.out.printf("Cofactor term for M[0][2]: +(%d * %d) = %d\n", c, minorC, termC);

        int determinant = termA + termB + termC;
        System.out.printf("\nFinal determinant: %d + %d + %d = %d\n", termA, termB, termC, determinant);

        return determinant;
    }

    /**
     * Computes the determinant of a 2x2 matrix given its four elements.
     *
     * @param m00 top-left element
     * @param m01 top-right element
     * @param m10 bottom-left element
     * @param m11 bottom-right element
     * @return the determinant (m00*m11 - m01*m10)
     */
    public static int computeMinor(int m00, int m01, int m10, int m11) {
        return m00 * m11 - m01 * m10;
    }

    /**
     * Prints a 2x2 minor matrix in a formatted way.
     */
    private static void printMinor(int[][] minor) {
        System.out.print("  | ");
        System.out.printf("%4d %4d", minor[0][0], minor[0][1]);
        System.out.println(" |");
        System.out.print("  | ");
        System.out.printf("%4d %4d", minor[1][0], minor[1][1]);
        System.out.println(" |");
    }
}
