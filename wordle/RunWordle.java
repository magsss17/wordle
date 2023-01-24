package org.cis1200.wordle;

import javax.swing.*;
import java.awt.*;

public class RunWordle implements Runnable {
    public void run() {

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Wordle");
        frame.setLocation(300, 300);
        frame.setAlwaysOnTop(true);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Press HELP for instructions");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Reset will allow the user to get a new word
        final JButton reset = new JButton("New Word");
        reset.setFocusable(false);
        reset.addActionListener(e -> board.reset());
        control_panel.add(reset);

        // Help will display the instructions
        final JButton help = new JButton("Help");
        help.setFocusable(false);
        help.addActionListener(e -> board.displayHelp());
        control_panel.add(help);

        // Save will save the game state
        final JButton save = new JButton("Save Game");
        save.setFocusable(false);
        save.addActionListener(e -> board.saveGame());
        control_panel.add(save);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.uploadSavedGame();
    }
}