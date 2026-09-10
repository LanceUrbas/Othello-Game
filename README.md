# Othello Game

A fully playable Othello (also known as Reversi) game built in Java, with automatic game state saving and JUnit test coverage for the core game logic.

## What it does

The game runs on a standard 8x8 board where players take turns placing chips, flipping any opposing chips that get sandwiched in the process. The current game state is written to a save file after every move, so the game picks up exactly where it left off if it's closed and reopened. A reset button starts a fresh game at any time.

## Core concepts used

**Two dimensional arrays.** The game board is stored as a 2D array, which maps naturally onto the 8x8 grid and makes it straightforward to track and update the state of individual squares.

**Recursion.** Flipping chips uses a recursive method, since the number of chips between a newly placed chip and an existing chip of the same color can vary. The method keeps flipping chips down the line until it hits a matching color, which serves as the base case.

**File I/O.** The game state is read from and written to a save file, so progress persists across sessions rather than resetting every time the game is closed.

**JUnit testing.** The core game logic is covered by JUnit tests, including edge cases around Othello's less obvious rules, since a fair number of them are easy to get subtly wrong.

## File breakdown

**RunOthello.java** sets up the game window and its buttons and display text, then hands the game off to the board.

**OthelloGameBoard.java** renders the board and chips, listens for mouse clicks, and passes the click location to the game model to determine what happens next.

**Othello.java** contains the core game rules. It validates whether a click is a legal move, updates the board if so, checks for game over conditions, and writes the updated state to the save file after every move.

**OthelloTest.java** contains JUnit tests covering the core game logic.

## How to run it

This is a Maven project. From the project's root folder, run:

mvn compile
mvn exec:java

The first command builds the project, the second launches the game.

## Notes

Built as a class project focused on applying two dimensional arrays, recursion, file I/O, and JUnit testing to a real, interactive game.
