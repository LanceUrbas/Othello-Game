package org.cis1200.Othello;

import javax.swing.*;

public class OthelloGame {

    public static void main(String[] args) {
        Runnable game = new org.cis1200.Othello.RunOthello();

        SwingUtilities.invokeLater(game);
    }
}
