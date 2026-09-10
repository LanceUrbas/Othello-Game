package org.cis1200.Othello;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class OthelloGameBoard extends JPanel {

    private Othello othelloModel; // model for the game
    private JLabel status; // current status text

    // Game constants
    public static final int BOARD_WIDTH = 800;
    public static final int BOARD_HEIGHT = 800;
    public OthelloGameBoard(JLabel status) {

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setFocusable(true);

        othelloModel = new Othello(); // initializes model for the game
        this.status = status;

        boolean playerTurn = othelloModel.getCurrentPlayer();
        if (playerTurn) {
            this.status.setText("Player 1's Turn");
        } else {
            this.status.setText("Player 2's Turn");
        }

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();

                // updates the model given the coordinates of the mouseclick
                othelloModel.playTurn(p.x / 100, p.y / 100);

                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }

    public void reset() {
        othelloModel.reset();
        status.setText("Player 1's Turn");
        repaint();

        requestFocusInWindow();
    }

    private void updateStatus() {
        if (othelloModel.getCurrentPlayer()) {
            status.setText("Player 1's Turn");
        } else {
            status.setText("Player 2's Turn");
        }

        if (othelloModel.getGameOver()) {
            if (othelloModel.getCheckWinner() == 3) {
                status.setText("It's a Tie!");
            } else if (othelloModel.getCheckWinner() == 1) {
                status.setText("Black Wins!");
            } else if (othelloModel.getCheckWinner() == 2) {
                status.setText("White Wins!");
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draws board and grid
        g.setColor(Color.getHSBColor(.294F, 1F, 0.4F));
        g.fillRect(0, 0, 800, 800);

        g.setColor(Color.BLACK);
        for (int row = 1; row < othelloModel.getBoard().length; row++) {
            for (int col = 1; col < othelloModel.getBoard()[row].length; col++) {
                g.drawLine(row * 100, 0, row * 100, 800);
                g.drawLine(0, col * 100, 800, col * 100);
            }
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int state = othelloModel.getCell(j, i);
                if (state == 1) {
                    g.setColor(Color.BLACK);
                    g.fillOval(10 + 100 * j, 10 + 100 * i, 80, 80);
                } else if (state == 2) {
                    g.setColor(Color.WHITE);
                    g.fillOval(10 + 100 * j, 10 + 100 * i, 80, 80);
                } else if (state == 3) {
                    g.setColor(Color.GRAY);
                    g.drawOval(10 + 100 * j, 10 + 100 * i, 80, 80);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
