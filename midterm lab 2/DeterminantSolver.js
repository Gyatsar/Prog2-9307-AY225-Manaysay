/**
 * Student Name   : <Your Full Name>
 * Student ID     : <Your Student ID>
 * Course Code    : PROG2-9307-AY225
 * Assignment     : Midterm Lab 2 - Determinant Solver
 * Date           : 2026-03-16
 *
 * Description:
 *   Reads a 3x3 matrix from the user via the console and computes its
 *   determinant by performing a cofactor expansion along the first row.
 *   The program prints the original matrix, each 2x2 minor, each cofactor
 *   term, and the final determinant result.
 */

const readline = require('readline');

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

/**
 * Prints a 3x3 matrix to the console.
 * @param {number[][]} matrix
 */
const printMatrix = (matrix) => {
  matrix.forEach((row) => {
    const rowStr = row.map((v) => String(v).padStart(4)).join(' ');
    console.log(`| ${rowStr} |`);
  });
};

/**
 * Computes the determinant of a 2x2 matrix given its elements.
 * @param {number} a
 * @param {number} b
 * @param {number} c
 * @param {number} d
 * @returns {number}
 */
const computeMinor = (a, b, c, d) => a * d - b * c;

/**
 * Computes the determinant of a 3x3 matrix using cofactor expansion along the first row.
 * Prints each minor, cofactor term, and the final determinant.
 * @param {number[][]} m
 * @returns {number}
 */
const solveDeterminant = (m) => {
  console.log('\n--- Cofactor expansion along the first row ---');
  console.log('det(M) = M[0][0]*(M[1][1]*M[2][2] - M[1][2]*M[2][1])');
  console.log('       - M[0][1]*(M[1][0]*M[2][2] - M[1][2]*M[2][0])');
  console.log('       + M[0][2]*(M[1][0]*M[2][1] - M[1][1]*M[2][0])\n');

  const [a, b, c] = m[0];

  const minorA = computeMinor(m[1][1], m[1][2], m[2][1], m[2][2]);
  const minorB = computeMinor(m[1][0], m[1][2], m[2][0], m[2][2]);
  const minorC = computeMinor(m[1][0], m[1][1], m[2][0], m[2][1]);

  console.log('Minor for M[0][0]:');
  printMatrix([ [m[1][1], m[1][2]], [m[2][1], m[2][2]] ]);
  console.log(`  Determinant = ${m[1][1]}*${m[2][2]} - ${m[1][2]}*${m[2][1]} = ${minorA}\n`);

  console.log('Minor for M[0][1]:');
  printMatrix([ [m[1][0], m[1][2]], [m[2][0], m[2][2]] ]);
  console.log(`  Determinant = ${m[1][0]}*${m[2][2]} - ${m[1][2]}*${m[2][0]} = ${minorB}\n`);

  console.log('Minor for M[0][2]:');
  printMatrix([ [m[1][0], m[1][1]], [m[2][0], m[2][1]] ]);
  console.log(`  Determinant = ${m[1][0]}*${m[2][1]} - ${m[1][1]}*${m[2][0]} = ${minorC}\n`);

  const termA = a * minorA; // +
  const termB = -b * minorB; // -
  const termC = c * minorC; // +

  console.log(`Cofactor term for M[0][0]: +(${a} * ${minorA}) = ${termA}`);
  console.log(`Cofactor term for M[0][1]: -(${b} * ${minorB}) = ${termB}`);
  console.log(`Cofactor term for M[0][2]: +(${c} * ${minorC}) = ${termC}\n`);

  const determinant = termA + termB + termC;
  console.log(`Final determinant: ${termA} + ${termB} + ${termC} = ${determinant}`);

  if (determinant === 0) {
    console.log('\nThe matrix is SINGULAR — it has no inverse.');
  }

  return determinant;
};

/**
 * Prompts the user for a 3x3 matrix and runs the determinant solver.
 */
const main = async () => {
  const matrix = Array.from({ length: 3 }, () => Array(3).fill(0));

  console.log('Enter the 3x3 matrix values (row by row):');

  const ask = (prompt) =>
    new Promise((resolve) => rl.question(prompt, (answer) => resolve(answer.trim())));

  for (let r = 0; r < 3; r += 1) {
    for (let c = 0; c < 3; c += 1) {
      const input = await ask(`  M[${r}][${c}] = `);
      const value = Number(input);
      if (Number.isNaN(value)) {
        console.log('    Invalid number. Please enter an integer.');
        c -= 1;
        continue;
      }
      matrix[r][c] = value;
    }
  }

  console.log('\nOriginal matrix:');
  printMatrix(matrix);

  solveDeterminant(matrix);

  rl.close();
};

main().catch((err) => {
  console.error('Unexpected error:', err);
  rl.close();
});
