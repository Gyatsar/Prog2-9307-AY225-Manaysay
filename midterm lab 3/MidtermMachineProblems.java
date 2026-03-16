import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Midterm Machine Problems (MP02, MP03, MP04) for the Prog2 assignment.
 *
 * MP02 - Display the first 10 rows of the dataset.
 * MP03 - Search for a keyword in the dataset.
 * MP04 - Count valid rows excluding empty rows.
 *
 * Program follows the required Java program flow:
 * 1. Start program
 * 2. Prompt user for dataset file path
 * 3. Read CSV using BufferedReader / FileReader
 * 4. Store records in a list of strings
 * 5. Process dataset depending on machine problem
 * 6. Display formatted output
 * 7. Handle errors (missing file, invalid data)
 * 8. End program
 */
public class MidtermMachineProblems {

    // === Variables ===
    // scanner: used for reading user input from the console.
    // rows: holds the raw dataset rows read from the CSV file.
    // choice: holds the selected machine problem option.
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // --- Program start ---
        // Processing logic:
        // 1) Prompt user for the dataset path.
        // 2) Read the dataset into memory (each line as a row).
        // 3) Show a menu to choose MP02/MP03/MP04.
        // 4) Execute the selected processing logic.
        System.out.println("=== Midterm Machine Problems (MP02 / MP03 / MP04) ===");
        String filePath = promptForFilePath();

        List<String> rows;
        try {
            rows = readAllRows(filePath);
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            return;
        }

        if (rows.isEmpty()) {
            System.out.println("The dataset appears to be empty.");
            return;
        }

        // Main menu cycle
        while (true) {
            System.out.println();
            System.out.println("Select a machine problem to run:");
            System.out.println("  2 - MP02: Display first 10 rows");
            System.out.println("  3 - MP03: Search for a keyword");
            System.out.println("  4 - MP04: Count valid rows (exclude empty rows)");
            System.out.println("  X - Exit");
            System.out.print("Enter choice: ");

            String choice = scanner.nextLine().trim();
            if (choice.equalsIgnoreCase("X")) {
                System.out.println("Exiting.");
                break;
            }

            switch (choice) {
                case "2":
                    runMP02(rows);
                    break;
                case "3":
                    runMP03(rows);
                    break;
                case "4":
                    runMP04(rows);
                    break;
                default:
                    System.out.println("Invalid choice. Try 2, 3, 4, or X.");
            }
        }
    }

    private static String promptForFilePath() {
        System.out.print("Enter the dataset CSV file path: ");
        return scanner.nextLine().trim();
    }

    private static List<String> readAllRows(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("File not found: " + filePath);
        }

        List<String> rows = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Keep lines exactly as read; processing functions will deal with emptiness.
                rows.add(line);
            }
        }
        return rows;
    }

    private static void runMP02(List<String> rows) {
        // MP02 processing logic: locate the first dataset row, then print the next 10 rows.
        // Dataset handling: raw CSV lines are stored in `rows`; we skip metadata/empty lines.
        System.out.println("\n--- MP02: Display first 10 rows of the dataset ---");

        // Find first row that looks like a real record (e.g., a Student/Faculty line)
        int start = findFirstDataRow(rows);
        if (start < 0) {
            System.out.println("No data rows found in the dataset.");
            return;
        }

        int end = Math.min(rows.size(), start + 10);

        System.out.printf("Showing rows %d through %d (inclusive) out of %d total rows:\n", start + 1, end, rows.size());
        for (int i = start; i < end; i++) {
            System.out.printf("%4d | %s\n", i + 1, rows.get(i));
        }

        if (end < rows.size()) {
            System.out.println("... dataset has more rows. Run MP02 again or use another machine problem.");
        }
    }

    /**
     * Finds the first row that looks like a data record (student/faculty row).
     * This helps skip the leading header/metadata rows in the provided CSV.
     */
    private static int findFirstDataRow(List<String> rows) {
        for (int i = 0; i < rows.size(); i++) {
            String row = rows.get(i);
            if (isEmptyRow(row)) {
                continue;
            }
            String lower = row.toLowerCase();
            // Typical data rows contain a name in quotes and the role (Student/Faculty) as a column
            if ((lower.contains("student") || lower.contains("faculty")) && row.contains(",")) {
                // Ensure it looks like a CSV entry (several commas)
                long commaCount = row.chars().filter(ch -> ch == ',').count();
                if (commaCount >= 4) {
                    return i;
                }
            }
        }
        return -1;
    }

    private static void runMP03(List<String> rows) {
        // MP03 processing logic: prompt for a keyword and scan each row for matches.
        // Variables: keyword (user input), matches (counter of matched rows), lowerKeyword (case-insensitive search key).
        System.out.println("\n--- MP03: Search for a keyword in the dataset ---");
        System.out.print("Enter keyword to search for: ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("No keyword entered. Returning to menu.");
            return;
        }

        String lowerKeyword = keyword.toLowerCase();
        int matches = 0;

        for (int i = 0; i < rows.size(); i++) {
            String row = rows.get(i);
            if (row.toLowerCase().contains(lowerKeyword)) {
                if (matches == 0) {
                    System.out.println("Matching rows (row number | row contents):");
                }
                System.out.printf("%4d | %s\n", i + 1, row);
                matches++;
            }
        }

        if (matches == 0) {
            System.out.println("No rows contain the keyword: " + keyword);
        } else {
            System.out.printf("\nFound %d matching row(s).\n", matches);
        }
    }

    private static void runMP04(List<String> rows) {
        // MP04 processing logic: count rows that are not empty based on our dataset handling rules.
        // Variables: validCount holds the number of non-empty rows.
        System.out.println("\n--- MP04: Count valid rows (exclude empty rows) ---");

        int validCount = 0;
        for (String row : rows) {
            if (!isEmptyRow(row)) {
                validCount++;
            }
        }

        System.out.printf("Total rows in file: %d\n", rows.size());
        System.out.printf("Valid (non-empty) rows: %d\n", validCount);
        System.out.println("Empty or blank rows are excluded from the valid row count.");
    }

    private static int findFirstNonEmptyRow(List<String> rows) {
        for (int i = 0; i < rows.size(); i++) {
            if (!isEmptyRow(rows.get(i))) {
                return i;
            }
        }
        return -1;
    }

    private static boolean isEmptyRow(String row) {
        if (row == null) {
            return true;
        }
        String trimmed = row.trim();
        if (trimmed.isEmpty()) {
            return true;
        }
        // If row only contains commas and whitespace (and optional quotes), treat it as empty.
        String stripped = trimmed.replaceAll("[\",\\s]", "");
        stripped = stripped.replaceAll(",", "");
        return stripped.isEmpty();
    }
}
