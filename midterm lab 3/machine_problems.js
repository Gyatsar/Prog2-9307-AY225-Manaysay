

// Midterm Machine Problems (MP02, MP03, MP04)
// Implements the same functionality as the Java version in JavaScript (Node.js).
//
// Usage:
//   node machine_problems.js
//
// MP02 - Display the first 10 rows of the dataset.
// MP03 - Search for a keyword in the dataset.
// MP04 - Count valid rows excluding empty rows.
//
// Remarks:
// - Variables: used for storing user input, file rows, and counters.
// - Functions: each machine problem is implemented as a separate function.
// - Processing logic: read file, parse rows, then handle each MP separately.
// - Dataset handling: the CSV is read as raw lines; empty/blank lines are excluded when needed.

const fs = require('fs');
const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

// Variables used in this file:
// - rl: readline interface for reading user input.
// - rows: array of CSV lines read from disk.
// - keyword: search string entered by the user.
function prompt(question) {
  return new Promise((resolve) => rl.question(question, (answer) => resolve(answer.trim())));
}

function isEmptyRow(line) {
  if (!line) return true;
  const trimmed = line.trim();
  if (trimmed.length === 0) return true;
  // If row only contains commas, quotes, or whitespace, treat as empty.
  const stripped = trimmed.replace(/[",\s]/g, '').replace(/,/g, '');
  return stripped.length === 0;
}

async function run() {
  console.log('=== Midterm Machine Problems (MP02 / MP03 / MP04) ===');

  const filePath = await prompt('Enter the dataset CSV file path: ');

  let rows;
  try {
    const raw = fs.readFileSync(filePath, { encoding: 'utf8' });
    rows = raw.split(/\r?\n/);
  } catch (err) {
    console.error('Error reading file:', err.message);
    rl.close();
    return;
  }

  if (rows.length === 0) {
    console.log('The dataset appears to be empty.');
    rl.close();
    return;
  }

  while (true) {
    console.log('\nSelect a machine problem to run:');
    console.log('  2 - MP02: Display first 10 rows');
    console.log('  3 - MP03: Search for a keyword');
    console.log('  4 - MP04: Count valid rows (exclude empty rows)');
    console.log('  X - Exit');

    const choice = (await prompt('Enter choice: ')).toUpperCase();
    if (choice === 'X') {
      console.log('Exiting.');
      break;
    }

    switch (choice) {
      case '2':
        runMP02(rows);
        break;
      case '3':
        await runMP03(rows);
        break;
      case '4':
        runMP04(rows);
        break;
      default:
        console.log('Invalid choice. Try 2, 3, 4, or X.');
    }
  }

  rl.close();
}

function findFirstNonEmptyRow(rows) {
  for (let i = 0; i < rows.length; i++) {
    if (!isEmptyRow(rows[i])) {
      return i;
    }
  }
  return -1;
}

function runMP02(rows) {
  console.log('\n--- MP02: Display first 10 rows of the dataset ---');
  const firstNonEmpty = findFirstNonEmptyRow(rows);
  if (firstNonEmpty < 0) {
    console.log('No non-empty rows found in the dataset.');
    return;
  }

  const start = firstNonEmpty;
  const end = Math.min(rows.length, start + 10);

  console.log(`Showing rows ${start + 1} through ${end} (inclusive) out of ${rows.length} total rows:`);
  for (let i = start; i < end; i++) {
    console.log(`${String(i + 1).padStart(4)} | ${rows[i]}`);
  }
  if (end < rows.length) {
    console.log('... dataset has more rows. Run MP02 again or use another machine problem.');
  }
}

async function runMP03(rows) {
  console.log('\n--- MP03: Search for a keyword in the dataset ---');
  const keyword = await prompt('Enter keyword to search for: ');
  if (!keyword) {
    console.log('No keyword entered. Returning to menu.');
    return;
  }

  const lowerKeyword = keyword.toLowerCase();
  let matches = 0;
  for (let i = 0; i < rows.length; i++) {
    const row = rows[i];
    if (row.toLowerCase().includes(lowerKeyword)) {
      if (matches === 0) {
        console.log('Matching rows (row number | row contents):');
      }
      console.log(`${String(i + 1).padStart(4)} | ${row}`);
      matches++;
    }
  }

  if (matches === 0) {
    console.log('No rows contain the keyword:', keyword);
  } else {
    console.log(`\nFound ${matches} matching row(s).`);
  }
}

function runMP04(rows) {
  console.log('\n--- MP04: Count valid rows (exclude empty rows) ---');

  const validCount = rows.reduce((count, row) => (isEmptyRow(row) ? count : count + 1), 0);

  console.log(`Total rows in file: ${rows.length}`);
  console.log(`Valid (non-empty) rows: ${validCount}`);
  console.log('Empty or blank rows are excluded from the valid row count.');
}

run();
