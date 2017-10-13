# FifteenPuzzle
A basic A* solver for the popular sliding puzzle game.

Compile:
`javac *.java`

Usage:
`java SlidingPuzzle --numbers 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,0`

Puzzles can be any `N x N` matrix. No steps are made to check if a puzzle is solvable, so you probably should make sure it's solvable before inputting it. 

Add `--verbose` to print every visited state (even when not part of the solution).

This was made for an Artificial Intelligence independent study for two seniors in highschool. Don't judge too much.

Example:

`java SlidingPuzzle --numbers 5,0,2,8,6,4,7,3,1`
