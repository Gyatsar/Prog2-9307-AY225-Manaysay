import java.util.Scanner;

/**
 * Prelim Grade Calculator - Java Version
 * This program calculates the required Prelim Exam score needed to pass or achieve
 * an excellent grade based on the student's current Class Standing.
 * 
 * Grading Breakdown:
 * - Prelim Grade = (Prelim Exam × 0.70) + (Class Standing × 0.30)
 * - Class Standing = (Attendance × 0.40) + (Lab Work Average × 0.60)
 * - Lab Work Average = (Lab1 + Lab2 + Lab3) / 3
 * 
 * Target Grades:
 * - Passing Grade: 75
 * - Excellent Grade: 100
 */
public class PrelimGradeCalculator {
    
    // Constants for grading weights
    private static final double PRELIM_EXAM_WEIGHT = 0.70;
    private static final double CLASS_STANDING_WEIGHT = 0.30;
    private static final double ATTENDANCE_WEIGHT = 0.40;
    private static final double LAB_WORK_WEIGHT = 0.60;
    
    // Target grades
    private static final double PASSING_GRADE = 75.0;
    private static final double EXCELLENT_GRADE = 100.0;
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Display program header
        System.out.println("========================================");
        System.out.println("   PRELIM GRADE CALCULATOR (JAVA)");
        System.out.println("========================================\n");
        
        // Get user inputs
        System.out.print("Enter number of attendances: ");
        double attendance = scanner.nextDouble();
        
        System.out.print("Enter Lab Work 1 grade: ");
        double lab1 = scanner.nextDouble();
        
        System.out.print("Enter Lab Work 2 grade: ");
        double lab2 = scanner.nextDouble();
        
        System.out.print("Enter Lab Work 3 grade: ");
        double lab3 = scanner.nextDouble();
        
        // Calculate Lab Work Average
        double labWorkAverage = calculateLabWorkAverage(lab1, lab2, lab3);
        
        // Calculate Class Standing
        double classStanding = calculateClassStanding(attendance, labWorkAverage);
        
        // Calculate required Prelim Exam scores
        double requiredForPassing = calculateRequiredPrelimExam(classStanding, PASSING_GRADE);
        double requiredForExcellent = calculateRequiredPrelimExam(classStanding, EXCELLENT_GRADE);
        
        // Display results
        displayResults(attendance, lab1, lab2, lab3, labWorkAverage, 
                      classStanding, requiredForPassing, requiredForExcellent);
        
        scanner.close();
    }
    
    /**
     * Calculate the average of three lab work grades
     * Formula: (Lab1 + Lab2 + Lab3) / 3
     */
    private static double calculateLabWorkAverage(double lab1, double lab2, double lab3) {
        return (lab1 + lab2 + lab3) / 3.0;
    }
    
    /**
     * Calculate Class Standing
     * Formula: (Attendance × 0.40) + (Lab Work Average × 0.60)
     */
    private static double calculateClassStanding(double attendance, double labWorkAverage) {
        return (attendance * ATTENDANCE_WEIGHT) + (labWorkAverage * LAB_WORK_WEIGHT);
    }
    
    /**
     * Calculate required Prelim Exam score to achieve target grade
     * Formula: (Target Grade - (Class Standing × 0.30)) / 0.70
     */
    private static double calculateRequiredPrelimExam(double classStanding, double targetGrade) {
        return (targetGrade - (classStanding * CLASS_STANDING_WEIGHT)) / PRELIM_EXAM_WEIGHT;
    }
    
    /**
     * Display all computed values and remarks
     */
    private static void displayResults(double attendance, double lab1, double lab2, double lab3,
                                      double labWorkAverage, double classStanding,
                                      double requiredForPassing, double requiredForExcellent) {
        
        System.out.println("\n========================================");
        System.out.println("           CALCULATION RESULTS");
        System.out.println("========================================\n");
        
        // Display input values
        System.out.printf("Attendance Score: %.2f\n", attendance);
        System.out.printf("Lab Work 1 Grade: %.2f\n", lab1);
        System.out.printf("Lab Work 2 Grade: %.2f\n", lab2);
        System.out.printf("Lab Work 3 Grade: %.2f\n", lab3);
        
        System.out.println("\n--- Computed Values ---");
        System.out.printf("Lab Work Average: %.2f\n", labWorkAverage);
        System.out.printf("Class Standing: %.2f\n", classStanding);
        
        System.out.println("\n--- Required Prelim Exam Scores ---");
        System.out.printf("To Pass (75): %.2f\n", requiredForPassing);
        System.out.printf("For Excellent (100): %.2f\n", requiredForExcellent);
        
        // Display remarks based on results
        System.out.println("\n--- Student Standing ---");
        displayRemarks(classStanding, requiredForPassing, requiredForExcellent);
        
        System.out.println("\n========================================");
    }
    
    /**
     * Display clear remarks explaining the student's standing
     */
    private static void displayRemarks(double classStanding, 
                                      double requiredForPassing, 
                                      double requiredForExcellent) {
        
        System.out.printf("Current Class Standing: %.2f\n", classStanding);
        
        // Check if passing is possible
        if (requiredForPassing > 100) {
            System.out.println("\nREMARK: Unfortunately, it is IMPOSSIBLE to pass the Prelim period.");
            System.out.printf("You would need a Prelim Exam score of %.2f (above 100) to pass.\n", 
                            requiredForPassing);
            System.out.println("Please focus on improving in the Midterm and Finals periods.");
        } 
        else if (requiredForPassing < 0) {
            System.out.println("\nREMARK: Congratulations! You have already SECURED a passing grade!");
            System.out.println("Even with a score of 0 on the Prelim Exam, you will pass.");
            
            // Check if excellent is still achievable
            if (requiredForExcellent <= 100) {
                System.out.printf("To achieve an excellent grade (100), you need %.2f on the Prelim Exam.\n", 
                                requiredForExcellent);
            }
        } 
        else {
            System.out.printf("\nREMARK: You need a Prelim Exam score of %.2f to PASS (75).\n", 
                            requiredForPassing);
            
            // Check if excellent is achievable
            if (requiredForExcellent > 100) {
                System.out.println("Achieving an excellent grade (100) is not possible.");
                System.out.printf("You would need a score of %.2f (above 100).\n", requiredForExcellent);
            } else if (requiredForExcellent <= 100 && requiredForExcellent >= 0) {
                System.out.printf("To achieve an EXCELLENT grade (100), you need %.2f on the Prelim Exam.\n", 
                                requiredForExcellent);
            }
        }
        
        // Additional encouragement
        if (classStanding >= 90) {
            System.out.println("\nEXCELLENT work on your Class Standing! Keep it up!");
        } else if (classStanding >= 75) {
            System.out.println("\nGood job! You're on track. Stay focused!");
        } else {
            System.out.println("\nDon't give up! There's still time to improve!");
        }
    }
}