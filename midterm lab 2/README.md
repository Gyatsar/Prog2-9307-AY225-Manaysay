# Programming Assignment 1 - 3x3 Matrix Determinant Solver

## Student Information
- Student Name: CELADEZ, JED CEDRIC G.
- Course: Math 101 - Linear Algebra
- School: University of Perpetual Help System DALTA, Molino Campus
- Date Completed: March 16, 2026
- Github Repo : https://github.com/jed1boy/uphsd-cs-celadez-jedcedric

## Assigned Matrix
\[
M = \begin{bmatrix}
5 & 3 & 1 \\
2 & 4 & 6 \\
1 & 5 & 3
\end{bmatrix}
\]

## Files Included
- DeterminantSolver.java
- determinant_solver.js

## How to Run
### Java
```
javac DeterminantSolver.java
java DeterminantSolver
```

### JavaScript (Node.js)
```
node DeterminantSolver.js
```

## Final Determinant Value
- det(M) = -3

## Sample Output (Java and JavaScript)
```text
=======================================================
  3x3 MATRIX DETERMINANT SOLVER
  Student: MANAYSAY, CLIVE E.
  Assigned Matrix:
=======================================================
  |  4  5  3 |
  |  2  6  1 |
  |  5  3  4 |
=======================================================

Expanding along Row 1 (cofactor expansion):

  Step 1 - Minor M11: det([6,1],[3,4]) = (6*4) - (1*3) = 24 - 3 = 21
  Step 2 - Minor M12: det([2,1],[5,4]) = (2*4) - (1*5) = 8 - 5 = 3
  Step 3 - Minor M13: det([2,6],[5,3]) = (2*3) - (6*5) = 6 - 30 = -24

  Cofactor C11 = (+1) * 4 * 21 = 84
  Cofactor C12 = (-1) * 5 * 3 = -15
  Cofactor C13 = (+1) * 3 * -24 = -72

  det(M) = 84 + (-15) + (-72)
=======================================================
  DETERMINANT = -3
=======================================================
```
