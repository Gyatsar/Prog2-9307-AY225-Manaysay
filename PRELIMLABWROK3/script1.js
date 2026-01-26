/**
 * Prelim Grade Calculator - JavaScript Version
 * This script calculates the required Prelim Exam score needed to pass or achieve
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

// Constants for grading weights
const PRELIM_EXAM_WEIGHT = 0.70;
const CLASS_STANDING_WEIGHT = 0.30;
const ATTENDANCE_WEIGHT = 0.40;
const LAB_WORK_WEIGHT = 0.60;

// Target grades
const PASSING_GRADE = 75.0;
const EXCELLENT_GRADE = 100.0;

/**
 * Calculate the average of three lab work grades
 * Formula: (Lab1 + Lab2 + Lab3) / 3
 * @param {number} lab1 - Lab Work 1 grade
 * @param {number} lab2 - Lab Work 2 grade
 * @param {number} lab3 - Lab Work 3 grade
 * @returns {number} - Average of the three lab works
 */
function calculateLabWorkAverage(lab1, lab2, lab3) {
    return (lab1 + lab2 + lab3) / 3.0;
}

/**
 * Calculate Class Standing
 * Formula: (Attendance × 0.40) + (Lab Work Average × 0.60)
 * @param {number} attendance - Attendance score
 * @param {number} labWorkAverage - Average of lab works
 * @returns {number} - Class standing score
 */
function calculateClassStanding(attendance, labWorkAverage) {
    return (attendance * ATTENDANCE_WEIGHT) + (labWorkAverage * LAB_WORK_WEIGHT);
}

/**
 * Calculate required Prelim Exam score to achieve target grade
 * Formula: (Target Grade - (Class Standing × 0.30)) / 0.70
 * @param {number} classStanding - Current class standing
 * @param {number} targetGrade - Target grade to achieve
 * @returns {number} - Required prelim exam score
 */
function calculateRequiredPrelimExam(classStanding, targetGrade) {
    return (targetGrade - (classStanding * CLASS_STANDING_WEIGHT)) / PRELIM_EXAM_WEIGHT;
}

/**
 * Generate remarks based on the student's standing
 * @param {number} classStanding - Current class standing
 * @param {number} requiredForPassing - Required score to pass
 * @param {number} requiredForExcellent - Required score for excellent grade
 * @returns {string} - HTML string with remarks
 */
function generateRemarks(classStanding, requiredForPassing, requiredForExcellent) {
    let remarksHTML = '';
    let remarksClass = 'remarks';
    
    // Check if passing is impossible (requires score > 100)
    if (requiredForPassing > 100) {
        remarksClass = 'remarks impossible';
        remarksHTML = `
            <h4>⚠️ Student Standing</h4>
            <p><strong>Current Class Standing:</strong> ${classStanding.toFixed(2)}</p>
            <p><strong>Status:</strong> Unfortunately, it is IMPOSSIBLE to pass the Prelim period.</p>
            <p>You would need a Prelim Exam score of <strong>${requiredForPassing.toFixed(2)}</strong> (above 100) to pass.</p>
            <p>💪 Don't give up! Focus on improving in the Midterm and Finals periods.</p>
        `;
    } 
    // Check if already passing (requires score < 0)
    else if (requiredForPassing < 0) {
        remarksClass = 'remarks excellent';
        remarksHTML = `
            <h4>🎉 Student Standing</h4>
            <p><strong>Current Class Standing:</strong> ${classStanding.toFixed(2)}</p>
            <p><strong>Status:</strong> Congratulations! You have already SECURED a passing grade!</p>
            <p>Even with a score of 0 on the Prelim Exam, you will pass.</p>
        `;
        // Check if excellent is still achievable
        if (requiredForExcellent <= 100) {
            remarksHTML += `<p>To achieve an excellent grade (100), you need <strong>${requiredForExcellent.toFixed(2)}</strong> on the Prelim Exam.</p>`;
        }
    } 
    // Normal case - passing is possible
    else {
        remarksHTML = `
            <h4>📊 Student Standing</h4>
            <p><strong>Current Class Standing:</strong> ${classStanding.toFixed(2)}</p>
            <p>You need a Prelim Exam score of <strong>${requiredForPassing.toFixed(2)}</strong> to PASS (75).</p>
        `;
        
        // Check if excellent is achievable
        if (requiredForExcellent > 100) {
            remarksHTML += `<p>Achieving an excellent grade (100) is not possible. You would need a score of ${requiredForExcellent.toFixed(2)} (above 100).</p>`;
        } else if (requiredForExcellent <= 100 && requiredForExcellent >= 0) {
            remarksHTML += `<p>To achieve an EXCELLENT grade (100), you need <strong>${requiredForExcellent.toFixed(2)}</strong> on the Prelim Exam.</p>`;
        }
    }
    
    // Additional encouragement based on class standing
    if (classStanding >= 90) {
        remarksHTML += `<p>✨ EXCELLENT work on your Class Standing! Keep it up!</p>`;
    } else if (classStanding >= 75) {
        remarksHTML += `<p>👍 Good job! You're on track. Stay focused!</p>`;
    } else {
        remarksHTML += `<p>💪 Don't give up! There's still time to improve!</p>`;
    }
    
    return `<div class="${remarksClass}">${remarksHTML}</div>`;
}

/**
 * Main calculation function - called when the Calculate button is clicked
 */
function calculateGrades() {
    // Get input values from the form
    const attendance = parseFloat(document.getElementById('attendance').value);
    const lab1 = parseFloat(document.getElementById('lab1').value);
    const lab2 = parseFloat(document.getElementById('lab2').value);
    const lab3 = parseFloat(document.getElementById('lab3').value);

    // Validate that all inputs are numbers
    if (isNaN(attendance) || isNaN(lab1) || isNaN(lab2) || isNaN(lab3)) {
        alert('Please enter all grades!');
        return;
    }

    // Validate that all inputs are within valid range (0-100)
    if (attendance < 0 || attendance > 100 || lab1 < 0 || lab1 > 100 || 
        lab2 < 0 || lab2 > 100 || lab3 < 0 || lab3 > 100) {
        alert('All grades must be between 0 and 100!');
        return;
    }

    // Calculate Lab Work Average
    const labWorkAverage = calculateLabWorkAverage(lab1, lab2, lab3);
    
    // Calculate Class Standing
    const classStanding = calculateClassStanding(attendance, labWorkAverage);
    
    // Calculate required Prelim Exam scores for passing and excellent grades
    const requiredForPassing = calculateRequiredPrelimExam(classStanding, PASSING_GRADE);
    const requiredForExcellent = calculateRequiredPrelimExam(classStanding, EXCELLENT_GRADE);

    // Display results in the results div
    const resultsDiv = document.getElementById('results');
    resultsDiv.style.display = 'block';
    resultsDiv.innerHTML = `
        <h3>📊 Calculation Results</h3>
        
        <div class="section-title">Input Values</div>
        <div class="result-item">
            <span>Attendance Score:</span>
            <strong>${attendance.toFixed(2)}</strong>
        </div>
        <div class="result-item">
            <span>Lab Work 1 Grade:</span>
            <strong>${lab1.toFixed(2)}</strong>
        </div>
        <div class="result-item">
            <span>Lab Work 2 Grade:</span>
            <strong>${lab2.toFixed(2)}</strong>
        </div>
        <div class="result-item">
            <span>Lab Work 3 Grade:</span>
            <strong>${lab3.toFixed(2)}</strong>
        </div>
        
        <div class="section-title">Computed Values</div>
        <div class="result-item">
            <span>Lab Work Average:</span>
            <strong>${labWorkAverage.toFixed(2)}</strong>
        </div>
        <div class="result-item">
            <span>Class Standing:</span>
            <strong>${classStanding.toFixed(2)}</strong>
        </div>
        
        <div class="section-title">Required Prelim Exam Scores</div>
        <div class="grade-highlight">
            To Pass (75): ${requiredForPassing.toFixed(2)}
        </div>
        <div class="grade-highlight">
            For Excellent (100): ${requiredForExcellent.toFixed(2)}
        </div>
        
        ${generateRemarks(classStanding, requiredForPassing, requiredForExcellent)}
    `;
    
    // Optional: Log results to console for debugging
    console.log('=== Prelim Grade Calculation ===');
    console.log('Attendance:', attendance);
    console.log('Lab Works:', lab1, lab2, lab3);
    console.log('Lab Work Average:', labWorkAverage.toFixed(2));
    console.log('Class Standing:', classStanding.toFixed(2));
    console.log('Required for Passing:', requiredForPassing.toFixed(2));
    console.log('Required for Excellent:', requiredForExcellent.toFixed(2));
}