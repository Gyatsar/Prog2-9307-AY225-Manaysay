const fs = require('fs');
const readline = require('readline');

// JavaScript (Node.js) implementation of the same task:
// - prompt for dataset path using readline
// - validate with fs.existsSync
// - compute simple analytics and write summary_report.csv using fs.writeFile

const rl = readline.createInterface({
    input: process.stdin,
    output: process.stdout
});

function askFilePath() {
    rl.question('Enter dataset file path: ', function(path) {
        if (fs.existsSync(path)) {
            console.log('File found. Processing...');
            generateSummary(path);
        } else {
            console.log('Invalid file path. Try again.');
            askFilePath();
        }
    });
}

function generateSummary(path) {
    const data = fs.readFileSync(path, 'utf8');
    const lines = data.trim().split(/\r?\n/);

    let lineCount = Math.max(0, lines.length - 1); // skip header if present
    let sum = 0;
    let numValues = 0;

    for (let i = 1; i < lines.length; i++) {
        const parts = lines[i].split(',');
        for (const p of parts) {
            const v = parseFloat(p);
            if (!isNaN(v)) {
                sum += v;
                numValues++;
            }
        }
    }

    const outputLines = [
        'Metric,Value',
        `Lines,${lineCount}`,
        `NumericValues,${numValues}`,
        `Sum,${sum}`,
        `Average,${numValues > 0 ? sum / numValues : 0}`
    ];

    fs.writeFile('summary_report.csv', outputLines.join('\n'), (err) => {
        if (err) {
            console.error('Error writing summary:', err);
        } else {
            console.log('Summary written to summary_report.csv');
        }
        rl.close();
    });
}

askFilePath();
