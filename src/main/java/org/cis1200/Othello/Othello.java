package org.cis1200.Othello;

import java.io.*;

public class Othello {

    private int[][] board;
    private int numTurns;
    private boolean player1;
    private boolean gameOver;
    private boolean flipPermission;
    private int[] cDirections = {-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] rDirections = {1, 0, 1, -1, 1, -1, 0, 1};

    public Othello() {
        load();
    }

    public int checkWinner() {
        int p1Total = 0;
        int p2Total = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == 1) {
                    p1Total++;
                } else if (board[i][j] == 2) {
                    p2Total++;
                }
            }
        }


        if (p1Total == p2Total) {
            return 3;
        } else if (p1Total > p2Total) {
            return 1;
        } else {
            return 2;
        }
    }

    public int recursiveFlip(int numLeft, int newR, int newC, int direction, int origVal) {
        if (numLeft <= 0) {
            return numLeft;
        } else {
            board[newR][newC] = origVal;

            newC += cDirections[direction];
            newR += rDirections[direction];
            return recursiveFlip(numLeft - 1, newR, newC, direction, origVal);
        }
    }

    public boolean validMove(int c, int r, boolean player) {
        int oppositeVal;
        int origVal;
        if (player) {
            oppositeVal = 2;
            origVal = 1;
        } else {
            oppositeVal = 1;
            origVal = 2;
        }

        for (int i = 0; i < 8; i++) {
            int numInBetween = 0;
            int newC = c + cDirections[i];
            int newR = r + rDirections[i];

            try {
                while (board[newR][newC] == oppositeVal) {
                    numInBetween++;

                    newC += cDirections[i];
                    newR += rDirections[i];

                    if (board[newR][newC] == 0 || board[newR][newC] == 3) {
                        numInBetween = 0;
                    }

                }

                if (flipPermission) {
                    newC = c + cDirections[i];
                    newR = r + rDirections[i];

                    numInBetween = recursiveFlip(numInBetween, newR, newC, i, origVal);
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                numInBetween = 0;
            }

            if (numInBetween > 0) {
                return true;
            }
        }

        return false;
    }

    public boolean hasValidMove(boolean player) {
        boolean hasValidMove = false;
        for (int row = 0; row < 8; row ++) {
            for (int col = 0; col < 8; col++) {
                if (board[row][col] == 3) {
                    board[row][col] = 0;
                }

                if (validMove(col, row, !player) && board[row][col] == 0) {
                    board[row][col] = 3;
                    hasValidMove = true;
                }
            }
        }

        return hasValidMove;
    }

    public void updateMemory() {
        try {
            BufferedWriter in = new BufferedWriter(new FileWriter("files/GameMemory.txt"));
            for (int i = 0; i < 8; i ++) {
                for (int j = 0; j < 8; j++) {
                    int value = board[i][j];
                    in.write(value + 48);
                }
                in.newLine();
            }

            if (numTurns < 10) {
                in.write("" + numTurns, 0, 1);
            } else {
                in.write("" + numTurns, 0, 2);
            }
            in.newLine();
            in.write("" + player1);
            in.newLine();
            in.write("" + gameOver);
            in.close();

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }
    }

    public void playTurn(int c, int r) {
        if (board[r][c] == 1 || board[r][c] == 2 ||
                gameOver || !validMove(c, r, player1)) {
            return;
        }

        if (player1) {
            board[r][c] = 1;
        } else {
            board[r][c] = 2;
        }

        flipPermission = true;
        validMove(c, r, player1);
        flipPermission = false;
        numTurns++;

        if (numTurns >= 60) {
            this.gameOver = true;
            checkWinner();
        }

        if (hasValidMove(player1)) {
            player1 = !player1;
        } else if (!hasValidMove(!player1)) {
            gameOver = true;
        }

        updateMemory();
    }


    public void reset() {
        board = new int[8][8];
        board[3][3] = 2;
        board[3][4] = 1;
        board[4][3] = 1;
        board[4][4] = 2;
        board[3][2] = 3;
        board[2][3] = 3;
        board[5][4] = 3;
        board[4][5] = 3;
        numTurns = 0;
        player1 = true;
        gameOver = false;
        updateMemory();
    }

    public void load() {
        board = new int[8][8];

        try {
            BufferedReader in = new BufferedReader(new FileReader("files/GameMemory.txt"));
            for (int i = 0; i < 8; i ++) {
                for (int j = 0; j < 8; j++) {
                    int value = in.read();
                    if (value == 10) {
                        value = in.read();
                    }
                    board[i][j] = value - 48;
                }
            }

            in.readLine();
            this.numTurns = Integer.parseInt(in.readLine());
            this.player1 = Boolean.parseBoolean(in.readLine());
            this.gameOver = Boolean.parseBoolean(in.readLine());
            in.close();

        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException();
        } catch (IOException e) {
            throw new IllegalArgumentException();
        }

    }

    public boolean getCurrentPlayer() {
        return player1;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getCell(int c, int r) {
        return board[r][c];
    }

    public boolean getGameOver() {
        return gameOver;
    }

    public int getCheckWinner() {
        return checkWinner();
    }

    public void setBoard(int[][] customBoard) {
        this.board = customBoard;
    }
}
