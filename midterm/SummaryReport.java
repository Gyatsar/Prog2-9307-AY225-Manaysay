import java.io.*;
import java.util.Scanner;

/**
 * Simple utility that reads a CSV dataset, computes a few sample analytics,
 * and writes a summary report to <code>summary_report.csv</code>.
 *
 * Requirements from MANAYSAY task:
 *  - prompt for file path using Scanner
 *  - validate using File class, loop until valid
 *  - perform analytics and export results using FileWriter
 */
public class SummaryReport {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        File dataFile;

        // prompt until a valid file is provided
        while (true) {
            System.out.print("Enter dataset file path: ");
            String path = input.nextLine();
            dataFile = new File(path);
            if (dataFile.exists() && dataFile.isFile()) {
                break;
            } else {
                System.out.println("Invalid file path. Please try again.");
            }
        }

        int lineCount = 0;
        double sum = 0;
        int numValues = 0;

        // read the CSV and do some simple analytics
        try (BufferedReader br = new BufferedReader(new FileReader(dataFile))) {
            // assume first line is header and skip it
            String header = br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                lineCount++;
                String[] parts = line.split(",");
                for (String part : parts) {
                    try {
                        double v = Double.parseDouble(part.trim());
                        sum += v;
                        numValues++;
                    } catch (NumberFormatException nfe) {
                        // ignore non‑numeric values
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading data file: " + e.getMessage());
            System.exit(1);
        }

        // write the summary to CSV with headers
        try (FileWriter writer = new FileWriter("summary_report.csv")) {
            writer.append("Metric,Value\n");
            writer.append("Lines," + lineCount + "\n");
            if (numValues > 0) {
                writer.append("NumericValues," + numValues + "\n");
                writer.append("Sum," + sum + "\n");
                writer.append("Average," + (sum / numValues) + "\n");
            }
            System.out.println("Summary written to summary_report.csv");
        } catch (IOException e) {
            System.err.println("Error writing summary: " + e.getMessage());
        }
    }
}
