import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Attendance Tracker Application
 * This application creates a GUI for tracking student attendance
 * with automatic time stamping and e-signature generation.
 * 
 * @author Your Name
 * @version 1.0
 */
public class AttendanceTracker extends JFrame {
    
    // Declare GUI components
    private JTextField nameField;
    private JTextField courseField;
    private JTextField timeInField;
    private JTextField eSignatureField;
    private JButton generateButton;
    
    /**
     * Constructor - Initializes the Attendance Tracker window
     */
    public AttendanceTracker() {
        // Set up the main frame
        setTitle("Attendance Tracker");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window on screen
        
        // Initialize and setup components
        initializeComponents();
        
        // Make the frame visible
        setVisible(true);
    }
    
    /**
     * Initialize and arrange all GUI components
     */
    private void initializeComponents() {
        // Create main panel with padding
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Create title label
        JLabel titleLabel = new JLabel("Student Attendance System", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        // Create form panel with GridBagLayout for better control
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8); // Padding between components
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Row 0: Attendance Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel nameLabel = new JLabel("Attendance Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(nameField, gbc);
        
        // Row 1: Course/Year
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel courseLabel = new JLabel("Course/Year:");
        courseLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(courseLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        courseField = new JTextField(20);
        courseField.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(courseField, gbc);
        
        // Row 2: Time In
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        JLabel timeLabel = new JLabel("Time In:");
        timeLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(timeLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        timeInField = new JTextField(20);
        timeInField.setFont(new Font("Arial", Font.PLAIN, 14));
        timeInField.setEditable(false); // Make it read-only
        timeInField.setBackground(Color.WHITE);
        formPanel.add(timeInField, gbc);
        
        // Row 3: E-Signature
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        JLabel signatureLabel = new JLabel("E-Signature:");
        signatureLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        formPanel.add(signatureLabel, gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        eSignatureField = new JTextField(20);
        eSignatureField.setFont(new Font("Arial", Font.PLAIN, 12));
        eSignatureField.setEditable(false); // Make it read-only
        eSignatureField.setBackground(Color.WHITE);
        formPanel.add(eSignatureField, gbc);
        
        // Create button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        // Generate button to populate Time In and E-Signature
        generateButton = new JButton("Generate Attendance");
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.setPreferredSize(new Dimension(180, 35));
        
        // Add action listener to the button
        generateButton.addActionListener(e -> generateAttendance());
        
        buttonPanel.add(generateButton);
        
        // Add all panels to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add main panel to frame
        add(mainPanel);
        
        // Automatically generate time and signature on startup
        generateAttendance();
    }
    
    /**
     * Generate current date/time and e-signature
     * This method is called when the button is clicked or on startup
     */
    private void generateAttendance() {
        // Get current date and time
        LocalDateTime now = LocalDateTime.now();
        
        // Format the date and time for better readability
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = now.format(formatter);
        
        // Set the formatted time in the Time In field
        timeInField.setText(formattedDateTime);
        
        // Generate a unique e-signature using UUID
        String eSignature = UUID.randomUUID().toString();
        
        // Set the e-signature in the E-Signature field
        eSignatureField.setText(eSignature);
        
        // Optional: Show confirmation message
        System.out.println("Attendance generated at: " + formattedDateTime);
        System.out.println("E-Signature: " + eSignature);
    }
    
    /**
     * Main method - Entry point of the application
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        // Use SwingUtilities to ensure thread safety
        SwingUtilities.invokeLater(() -> {
            // Create and display the Attendance Tracker
            new AttendanceTracker();
        });
    }
}