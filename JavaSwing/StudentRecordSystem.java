// Records - [Your Name] [Your Student ID]

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class StudentRecordSystem extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField idField, firstNameField, lastNameField;
    private JTextField lab1Field, lab2Field, lab3Field, prelimField, attendanceField;
    private JButton addButton, deleteButton;
    private static final String CSV_FILE = "MOCK_DATA.csv";
    private static String CSV_PATH;
    
    static {
        // Try to find MOCK_DATA.csv in the JavaSwing directory
        File javaSwingDir = new File(System.getProperty("user.dir"), "Prog2-9307-AY225-Manaysay/JavaSwing");
        File csvFile = new File(javaSwingDir, CSV_FILE);
        
        if (csvFile.exists()) {
            CSV_PATH = csvFile.getAbsolutePath();
        } else {
            // Fallback: try current directory
            csvFile = new File(System.getProperty("user.dir"), CSV_FILE);
            CSV_PATH = csvFile.getAbsolutePath();
        }
    }

    public StudentRecordSystem() {
        setTitle("Records - [Your Name] [Your Student ID]");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Create table model with columns
        String[] columns = {"StudentID", "first_name", "last_name", "LAB WORK 1", "LAB WORK 2", "LAB WORK 3", "PRELIM EXAM", "ATTENDANCE GRADE"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        
        JScrollPane scrollPane = new JScrollPane(table);

        // Create input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());

        idField = new JTextField(12);
        firstNameField = new JTextField(12);
        lastNameField = new JTextField(12);
        lab1Field = new JTextField(5);
        lab2Field = new JTextField(5);
        lab3Field = new JTextField(5);
        prelimField = new JTextField(5);
        attendanceField = new JTextField(5);

        inputPanel.add(new JLabel("Student ID:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("First Name:"));
        inputPanel.add(firstNameField);
        inputPanel.add(new JLabel("Last Name:"));
        inputPanel.add(lastNameField);
        inputPanel.add(new JLabel("LAB1:"));
        inputPanel.add(lab1Field);
        inputPanel.add(new JLabel("LAB2:"));
        inputPanel.add(lab2Field);
        inputPanel.add(new JLabel("LAB3:"));
        inputPanel.add(lab3Field);
        inputPanel.add(new JLabel("Prelim:"));
        inputPanel.add(prelimField);
        inputPanel.add(new JLabel("Attendance:"));
        inputPanel.add(attendanceField);

        // Create button panel
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        // Add action listeners
        addButton.addActionListener(e -> addRecord());
        deleteButton.addActionListener(e -> deleteRecord());

        // Layout
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Load CSV data on startup
        loadCSVData();
    }

    private void loadCSVData() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            boolean isHeader = true;
            while ((line = br.readLine()) != null) {
                // Skip header row
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",");
                // Fill missing columns with empty strings instead of zeros
                String s0 = data.length > 0 ? data[0].trim() : "";
                String s1 = data.length > 1 ? data[1].trim() : "";
                String s2 = data.length > 2 ? data[2].trim() : "";
                String s3 = data.length > 3 ? data[3].trim() : "";
                String s4 = data.length > 4 ? data[4].trim() : "";
                String s5 = data.length > 5 ? data[5].trim() : "";
                String s6 = data.length > 6 ? data[6].trim() : "";
                String s7 = data.length > 7 ? data[7].trim() : "";

                Object[] row = {s0, s1, s2, s3, s4, s5, s6, s7};
                tableModel.addRow(row);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error reading CSV file: " + CSV_PATH + "\n\n" + e.getMessage() +
                "\n\nMake sure MOCK_DATA.csv is in the same folder as the .java file!",
                "File Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addRecord() {
        String id = idField.getText().trim();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String lab1 = lab1Field.getText().trim();
        String lab2 = lab2Field.getText().trim();
        String lab3 = lab3Field.getText().trim();
        String prelim = prelimField.getText().trim();
        String attendance = attendanceField.getText().trim();

        if (!id.isEmpty() && !firstName.isEmpty() && !lastName.isEmpty()) {
            // Keep fields empty if user didn't enter them
            tableModel.addRow(new Object[]{id, firstName, lastName, lab1, lab2, lab3, prelim, attendance});
            saveToCSV();
            
            // Clear fields
            idField.setText("");
            firstNameField.setText("");
            lastNameField.setText("");
            lab1Field.setText("");
            lab2Field.setText("");
            lab3Field.setText("");
            prelimField.setText("");
            attendanceField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Please fill in all fields", 
                "Input Error", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteRecord() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            saveToCSV();
        } else {
            JOptionPane.showMessageDialog(this, 
                "Please select a row to delete", 
                "Selection Error", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateRecord() {
        // Update functionality removed per request (only add and delete allowed)
        JOptionPane.showMessageDialog(this,
            "Update is disabled. Use Add and Delete only.",
            "Info",
            JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveToCSV() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CSV_PATH))) {
            // Write header
            pw.println("StudentID,first_name,last_name,LAB WORK 1,LAB WORK 2,LAB WORK 3,PRELIM EXAM,ATTENDANCE GRADE");
            
            // Write data rows with placeholder values for grade columns
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String id = tableModel.getValueAt(i, 0).toString();
                String firstName = tableModel.getValueAt(i, 1).toString();
                String lastName = tableModel.getValueAt(i, 2).toString();
                String lab1 = tableModel.getValueAt(i, 3) != null ? tableModel.getValueAt(i, 3).toString() : "";
                String lab2 = tableModel.getValueAt(i, 4) != null ? tableModel.getValueAt(i, 4).toString() : "";
                String lab3 = tableModel.getValueAt(i, 5) != null ? tableModel.getValueAt(i, 5).toString() : "";
                String prelim = tableModel.getValueAt(i, 6) != null ? tableModel.getValueAt(i, 6).toString() : "";
                String attendance = tableModel.getValueAt(i, 7) != null ? tableModel.getValueAt(i, 7).toString() : "";
                pw.println(id + "," + firstName + "," + lastName + "," + lab1 + "," + lab2 + "," + lab3 + "," + prelim + "," + attendance);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, 
                "Error writing to CSV file: " + e.getMessage(), 
                "File Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentRecordSystem frame = new StudentRecordSystem();
            frame.setVisible(true);
        });
    }
}