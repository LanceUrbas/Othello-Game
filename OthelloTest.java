package org.cis1200.othello;

import org.cis1200.Othello.Othello;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class OthelloTest {
    private Othello othelloModel;
    private int[][] customBoard = new int[8][8];

    @Test
    public void testWin() {
        othelloModel = new Othello();
        othelloModel.reset();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                customBoard[i][j] = 1;
            }
        }

        customBoard[1][1] = 2;
        customBoard[2][2] = 2;
        customBoard[3][3] = 2;
        othelloModel.setBoard(customBoard);
        int actual = othelloModel.checkWinner();

        assertEquals(1, actual);
    }

    @Test
    public void testDraw() {
        othelloModel = new Othello();
        othelloModel.reset();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i < 4) {
                    customBoard[i][j] = 1;
                } else {
                    customBoard[i][j] = 2;
                }
            }
        }

        othelloModel.setBoard(customBoard);
        int actual = othelloModel.checkWinner();

        assertEquals(3, actual);
    }

    @Test
    public void testStalemateWin() {
        othelloModel = new Othello();
        othelloModel.reset();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 0 || j == 0) {
                    customBoard[i][j] = 1;
                } else {
                    customBoard[i][j] = 2;
                }
            }
        }
        customBoard[0][0] = 0;

        othelloModel.setBoard(customBoard);
        boolean p1HasMove = othelloModel.hasValidMove(othelloModel.getCurrentPlayer());
        boolean p2HasMove = othelloModel.hasValidMove(!othelloModel.getCurrentPlayer());
        int actualWinner = othelloModel.checkWinner();

        assertFalse(p1HasMove || p2HasMove);
        assertEquals(2, actualWinner);
    }

    @Test
    public void testInvalidMoves() {
        othelloModel = new Othello();
        othelloModel.reset();

        // Tests many invalid moves and edge cases.
        int[][] expected = othelloModel.getBoard();
        othelloModel.playTurn(0, 0);
        othelloModel.playTurn(7,7);
        othelloModel.playTurn(2, 2);
        othelloModel.playTurn(3,4);
        int[][] actual = othelloModel.getBoard();

        assertEquals(expected, actual);
    }

    @Test
    public void testPlayTurn() {
        Othello othelloModel1 = new Othello();
        Othello othelloModel2 = new Othello();
        othelloModel1.reset();
        othelloModel2.reset();


        customBoard = othelloModel1.getBoard();
        customBoard[5][4] = 1;
        customBoard[5][3] = 2;
        int expected1 = othelloModel1.getCell(4, 5);
        int expected2 = othelloModel1.getCell(3, 5);
        othelloModel2.playTurn(4, 5);
        othelloModel2.playTurn(3, 5);
        int actual1 = othelloModel2.getCell(4, 5);
        int actual2 = othelloModel2.getCell(3, 5);
        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
    }
}
