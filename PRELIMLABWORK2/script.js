/**
 * Login Form Validation Script
 * This script handles form submission, validates credentials,
 * and provides feedback to the user.
 */

// Predefined credentials (hardcoded for this assignment)
const VALID_USERNAME = "Clive Manaysay";
const VALID_PASSWORD = "clive1114";

/**
 * Attendance Records Data Structure
 * Array to store attendance records with username and login timestamp
 */
let attendanceRecords = [];

/**
 * Attendance Record Object Structure:
 * {
 *     id: number,           // Unique identifier for each record
 *     username: string,     // Username of the person who logged in
 *     timestamp: string,    // Formatted date and time of login
 *     rawDate: Date         // Raw Date object for sorting/filtering
 * }
 */

/**
 * Initialize the script when the DOM is fully loaded
 */
document.addEventListener('DOMContentLoaded', function() {
    // Get the form element
    const loginForm = document.querySelector('form');
    
    // Add event listener for form submission
    loginForm.addEventListener('submit', handleFormSubmit);
});

/**
 * Handle form submission
 * @param {Event} event - The form submit event
 */
function handleFormSubmit(event) {
    // Prevent the default form submission behavior
    event.preventDefault();
    
    // Remove any existing messages first
    const existingMessages = document.querySelectorAll('.message');
    existingMessages.forEach(msg => msg.remove());
    
    // Get the input values
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;
    
    // Validate the credentials
    if (validateCredentials(username, password)) {
        // Login successful
        displayWelcomeMessage(username);
    } else {
        // Login failed
        displayErrorMessage();
        playBeepSound();
    }
}

/**
 * Validate username and password against predefined values
 * @param {string} username - The entered username
 * @param {string} password - The entered password
 * @returns {boolean} - True if credentials are valid, false otherwise
 */
function validateCredentials(username, password) {
    // Check if username and password match predefined values
    return username === VALID_USERNAME && password === VALID_PASSWORD;
}

/**
 * Display a welcome message on successful login
 * @param {string} username - The username of the logged-in user
 */
function displayWelcomeMessage(username) {
    // Get current date and time
    const now = new Date();
    const loginTimestamp = getCurrentTimestamp();
    
    // Add attendance record to the data structure
    addAttendanceRecord(username, loginTimestamp, now);
    
    // Remove any existing messages
    const existingMessages = document.querySelectorAll('.message');
    existingMessages.forEach(msg => msg.remove());
    
    // Create welcome message element
    const welcomeDiv = document.createElement('div');
    welcomeDiv.className = 'message success-message';
    welcomeDiv.innerHTML = `
        <h3>✓ Login Successful!</h3>
        <p>Welcome back, <strong>${username}</strong>!</p>
    `;
    
    // Insert the message before the form
    const formContainer = document.querySelector('.form-container');
    const titleElement = formContainer.querySelector('h2');
    titleElement.insertAdjacentElement('afterend', welcomeDiv);
    
    // Display the timestamp in the timestamp section
    displayTimestamp(loginTimestamp);
    
    // Display all attendance records
    displayAttendanceRecords();
    
    // Hide the form
    document.querySelector('form').style.display = 'none';
    
    // Hide the info text
    const infoText = document.querySelector('.info-text');
    if (infoText) {
        infoText.style.display = 'none';
    }
    
    // Optional: Log to console
    console.log(`Login successful for user: ${username} at ${loginTimestamp}`);
    console.log('Current attendance records:', attendanceRecords);
}

/**
 * Get current system date and time
 * @returns {string} - Formatted date and time string
 */
function getCurrentTimestamp() {
    // Create a new Date object with current date and time
    const now = new Date();
    
    // Get individual date components
    const month = String(now.getMonth() + 1).padStart(2, '0'); // Months are 0-indexed
    const day = String(now.getDate()).padStart(2, '0');
    const year = now.getFullYear();
    
    // Get individual time components
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    
    // Format as "MM/DD/YYYY HH:MM:SS"
    const formattedTimestamp = `${month}/${day}/${year} ${hours}:${minutes}:${seconds}`;
    
    return formattedTimestamp;
}

/**
 * Display the timestamp in the HTML
 * @param {string} timestamp - The formatted timestamp string
 */
function displayTimestamp(timestamp) {
    // Get the timestamp display section
    const timestampSection = document.getElementById('timestamp-display');
    
    // Make the section visible
    timestampSection.style.display = 'block';
    
    // Update the timestamp text
    document.getElementById('login-time').textContent = timestamp;
}

/**
 * Add a new attendance record to the data structure
 * @param {string} username - The username of the logged-in user
 * @param {string} timestamp - The formatted timestamp string
 * @param {Date} rawDate - The raw Date object
 */
function addAttendanceRecord(username, timestamp, rawDate) {
    // Create a new attendance record object
    const record = {
        id: attendanceRecords.length + 1,  // Auto-increment ID
        username: username,
        timestamp: timestamp,
        rawDate: rawDate
    };
    
    // Add the record to the array
    attendanceRecords.push(record);
    
    // Optional: Log the new record
    console.log('New attendance record added:', record);
}

/**
 * Display all attendance records in the HTML
 */
function displayAttendanceRecords() {
    // Get or create the attendance records section
    let recordsSection = document.getElementById('attendance-records');
    
    if (!recordsSection) {
        // Create the section if it doesn't exist
        recordsSection = document.createElement('div');
        recordsSection.id = 'attendance-records';
        recordsSection.className = 'attendance-records-section';
        
        // Add to the form container
        const formContainer = document.querySelector('.form-container');
        formContainer.appendChild(recordsSection);
    }
    
    // Clear existing content
    recordsSection.innerHTML = '<h3>Attendance Records</h3>';
    
    // Check if there are any records
    if (attendanceRecords.length === 0) {
        recordsSection.innerHTML += '<p class="no-records">No attendance records yet.</p>';
        return;
    }
    
    // Create a table to display records
    let tableHTML = `
        <table class="attendance-table">
            <thead>
                <tr>
                    <th>#</th>
                    <th>Username</th>
                    <th>Login Time</th>
                </tr>
            </thead>
            <tbody>
    `;
    
    // Add each record as a table row
    attendanceRecords.forEach(record => {
        tableHTML += `
            <tr>
                <td>${record.id}</td>
                <td>${record.username}</td>
                <td>${record.timestamp}</td>
            </tr>
        `;
    });
    
    tableHTML += `
            </tbody>
        </table>
    `;
    
    // Add download button
    tableHTML += `
        <div class="download-button-container">
            <button onclick="downloadAttendanceSummary()" class="download-btn">
                📥 Download Attendance Summary
            </button>
        </div>
    `;
    
    // Add the table to the section
    recordsSection.innerHTML += tableHTML;
    
    // Make the section visible
    recordsSection.style.display = 'block';
}

/**
 * Get all attendance records
 * @returns {Array} - Array of all attendance records
 */
function getAllAttendanceRecords() {
    return attendanceRecords;
}

/**
 * Get attendance records for a specific username
 * @param {string} username - The username to filter by
 * @returns {Array} - Array of filtered attendance records
 */
function getRecordsByUsername(username) {
    return attendanceRecords.filter(record => record.username === username);
}

/**
 * Clear all attendance records
 */
function clearAttendanceRecords() {
    attendanceRecords = [];
    console.log('All attendance records cleared');
}

/**
 * Generate and download attendance summary as a text file
 * Uses the Blob API to create a downloadable file
 */
function downloadAttendanceSummary() {
    // Check if there are any records
    if (attendanceRecords.length === 0) {
        alert('No attendance records to download!');
        return;
    }
    
    // Create the header for the text file
    let attendanceData = '========================================\n';
    attendanceData += '      ATTENDANCE SUMMARY REPORT\n';
    attendanceData += '========================================\n\n';
    
    // Add generation timestamp
    attendanceData += 'Report Generated: ' + getCurrentTimestamp() + '\n';
    attendanceData += 'Total Records: ' + attendanceRecords.length + '\n';
    attendanceData += '========================================\n\n';
    
    // Add each attendance record
    attendanceRecords.forEach((record, index) => {
        attendanceData += 'Record #' + record.id + '\n';
        attendanceData += '-----------------------------------\n';
        attendanceData += 'Username: ' + record.username + '\n';
        attendanceData += 'Login Time: ' + record.timestamp + '\n';
        
        // Add a blank line between records (except for the last one)
        if (index < attendanceRecords.length - 1) {
            attendanceData += '\n';
        }
    });
    
    // Add footer
    attendanceData += '\n========================================\n';
    attendanceData += 'End of Report\n';
    attendanceData += '========================================\n';
    
    // Create a Blob object with the text data
    const blob = new Blob([attendanceData], { type: 'text/plain' });
    
    // Create a temporary anchor element for downloading
    const link = document.createElement('a');
    
    // Create a URL for the blob
    link.href = window.URL.createObjectURL(blob);
    
    // Set the download filename with current date
    const downloadDate = new Date().toISOString().split('T')[0]; // Format: YYYY-MM-DD
    link.download = 'attendance_summary_' + downloadDate + '.txt';
    
    // Trigger the download
    link.click();
    
    // Clean up - revoke the object URL to free up memory
    window.URL.revokeObjectURL(link.href);
    
    // Log to console
    console.log('Attendance summary downloaded successfully');
}

/**
 * Display an error message on failed login
 */
function displayErrorMessage() {
    // Remove any existing error messages
    const existingMessages = document.querySelectorAll('.message');
    existingMessages.forEach(msg => msg.remove());
    
    // Create error message element
    const errorDiv = document.createElement('div');
    errorDiv.className = 'message error-message';
    errorDiv.innerHTML = `
        <h3>✗ Login Failed!</h3>
        <p>Invalid username or password. Please try again.</p>
    `;
    
    // Insert the error message after the title
    const formContainer = document.querySelector('.form-container');
    const titleElement = formContainer.querySelector('h2');
    titleElement.insertAdjacentElement('afterend', errorDiv);
    
    // Shake animation for the form
    const form = document.querySelector('form');
    form.classList.add('shake');
    setTimeout(() => {
        form.classList.remove('shake');
    }, 500);
    
    // Clear the password field for security
    document.getElementById('password').value = '';
    
    // Optional: Log to console
    console.log('Login failed: Invalid credentials');
}

/**
 * Play a beep sound when login fails
 * Uses the HTML Audio API to play a sound file
 */
function playBeepSound() {
    // Create a new Audio object with the beep sound file
    // You can use a data URL for a simple beep or link to an audio file
    const beepSound = new Audio('oulul.mp3');
    
    // Set volume (0.0 to 1.0)
    beepSound.volume = 0.5;
    
    // Play the sound
    beepSound.play()
        .then(() => {
            console.log('Beep sound played successfully');
        })
        .catch(error => {
            console.log('Error playing beep sound:', error);
        });
}

/**
 * Optional: Add keyboard shortcut for testing
 * Press Ctrl+Shift+L to auto-fill correct credentials
 */
document.addEventListener('keydown', function(event) {
    if (event.ctrlKey && event.shiftKey && event.key === 'L') {
        document.getElementById('username').value = VALID_USERNAME;
        document.getElementById('password').value = VALID_PASSWORD;
        console.log('Test credentials filled. Username: admin, Password: password123');
    }
});