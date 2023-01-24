package org.cis1200.wordle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.io.*;

import static java.lang.Character.toUpperCase;

/**
 * This class instantiates the Wordle Game Board.
 * As the user either types or clicks on the keyboard to enter the guess, the
 * model is updated. Whenever the model
 * * is updated, the game board repaints itself and updates its status JLabel to
 * * reflect the current state of the model.
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {
    private Wordle w;
    private JLabel status;
    public static final int BOARD_WIDTH = 350;
    public static final int BOARD_HEIGHT = 450;
    private boolean validWord;

    public GameBoard(JLabel statusInit) {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.status = statusInit;

        // gets keyboard focus
        setFocusable(true);

        char[] guess = new char[5];
        final int[] ctr = { 0 };
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                // check if game is over
                if (!w.getWon() && !w.getGameState()) {
                    if (e.getKeyChar() == 10 && ctr[0] == 5) {
                        // check if enter is pressed
                        // check the final answer
                        System.out.println("\nenter key pressed");
                        if (w.checkGuessAnswer(guess)) {
                            statusValidWord(true);
                            repaint();
                            ctr[0] = 0;
                        } else {
                            System.out.println("\ninvalid word");
                            statusValidWord(false);
                        }

                    } else if (e.getKeyChar() == 8) {
                        // check if backspace is pressed
                        System.out.println("\nbackspace pressed");
                        if (ctr[0] > 0) {
                            ctr[0]--;
                            guess[ctr[0]] = (char) 0;
                            w.setElement(guess[ctr[0]], ctr[0]);
                            repaint();
                        } else {
                            ctr[0] = 0;
                        }
                    } else if (ctr[0] < 5) {
                        char c = toUpperCase(e.getKeyChar());
                        // check if c is a valid letter
                        if (Character.isLetter(c)) {
                            guess[ctr[0]] = c;
                            System.out.print(c);
                            w.setElement(c, ctr[0]);
                            repaint();
                            ctr[0]++;
                        }
                    } else if (ctr[0] >= 5) {
                        System.out.println("more than 5 letters typed");
                    }
                }
                updateStatus();
            }
        });
    }

    public void updateStatus() {
        if (w.getWon()) {
            status.setText("You Won! Press NEW WORD to play again.");
        } else if (w.getGameState()) {
            status.setText("Game Over... out of moves. The word was " + w.getAnswer());
        } else if (!validWord) {
            status.setText("Enter valid word");
        } else {
            status.setText("Number of guesses left: " + (6 - w.getGuessNumber()));
        }
    }

    // set status to enter valid wordle
    private void statusValidWord(boolean stat) {
        validWord = stat;
    }

    public void reset() {
        // reset board
        w.reset();
        status.setText("Press HELP for instructions");
        // save an empty game
        resetSavedGame();
        requestFocusInWindow();
        repaint();
    }

    // display instructions
    public void displayHelp() {
        JFrame instructionFrame = new JFrame();
        instructionFrame.setSize(700, 210);
        JPanel instructionTitle = new JPanel();
        JPanel instructionPanel = new JPanel();
        JLabel title = new JLabel("WELCOME TO WORDLE");
        JLabel lbl7 = new JLabel("This is a classic game of Wordle.");
        JLabel lbl1 = new JLabel("Guess the Wordle in 6 tries.");
        JLabel lbl2 = new JLabel("Each guess must be a valid 5 letter word.");
        JLabel lbl3 = new JLabel("Begin by typing a valid 5 letter word.");
        JLabel lbl8 = new JLabel("Press enter to check your word.");
        JLabel lbl11 = new JLabel(
                "If the letter is green, then the letter is in the correct spot."
        );
        JLabel lbl4 = new JLabel(
                "If the letter is in the word, but is in the wrong spot, it will be yellow."
        );
        JLabel lbl5 = new JLabel("If the letter is red, then the letter is not in the word");
        JLabel lbl6 = new JLabel(
                "If the letters don't change colors, " +
                        "then the string given isn't a valid English word."
        );
        JLabel lbl9 = new JLabel("Press SAVE to save the current game and return later.");
        JLabel lbl10 = new JLabel("If you're stuck, press NEW WORD to generate a new word");

        instructionTitle.add(title);
        instructionPanel.add(lbl7);
        instructionPanel.add(lbl1);
        instructionPanel.add(lbl2);
        instructionPanel.add(lbl3);
        instructionPanel.add(lbl8);
        instructionPanel.add(lbl11);
        instructionPanel.add(lbl4);
        instructionPanel.add(lbl5);
        instructionPanel.add(lbl6);
        instructionPanel.add(lbl9);
        instructionPanel.add(lbl10);
        instructionFrame.add(instructionTitle, BorderLayout.PAGE_START);
        instructionFrame.add(instructionPanel, BorderLayout.CENTER);

        instructionFrame.setVisible(true);
    }

    // save an empty game
    public void resetSavedGame() {
        File newFile = null;
        try {
            newFile = new File("files/savegame.txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        BufferedWriter bw = null;
        // make an empty file
        try {
            bw = new BufferedWriter(new FileWriter(newFile, false));
            bw.write("");
        } catch (IOException e) {
            System.out.println("IOException writeStringsToFile");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    // save game
    // use a buffered writer to write game state into a file
    public void saveGame() {
        // create file
        boolean exists = false;
        File newFile = null;
        try {
            newFile = new File("files/savegame.txt");
            if (newFile.createNewFile()) {
                System.out.println("File created: " + newFile.getName());
            } else {
                System.out.println("File already exists.");
                exists = true;
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // add string form of tiles into an array of strings
        String[] stringsToWrite = { "", "", "", "", "" };
        Tile[][] board = w.returnBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                stringsToWrite[i] += board[i][j];
            }
        }

        // write strings to file
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(newFile, !exists));
            bw.write(w.getAnswer());
            bw.newLine();
            bw.write(String.valueOf(w.getGuessNumber()));
            bw.newLine();
            for (String s : stringsToWrite) {
                if (s != null && !s.isEmpty()) {
                    bw.write(s);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("IOException writeStringsToFile");
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        status.setText("Game saved.");
    }

    // get game state from file
    public void uploadSavedGame() {
        // initialize new parameters
        Tile[][] board = new Tile[5][6];
        String ans = "";
        int numGuesses = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Tile((i * 65) + 20, (j * 65) + 50);
            }
        }

        // use file reader to read in file contents
        FileLineIterator fl;
        try {
            fl = new FileLineIterator("files/savegame.txt");
        } catch (IllegalArgumentException e) {
            w = new Wordle();
            return;
        }
        // read in answer
        if (fl.hasNext()) {
            ans = fl.next();
        }
        // read in number of guesses
        if (fl.hasNext()) {
            numGuesses = Integer.parseInt(fl.next());
        }
        // read in tile states
        int ctr = 0;
        while (fl.hasNext()) {
            String list = fl.next();
            String[] rows = list.split(",");
            for (int i = 0; i < rows.length; i++) {
                if (rows != null && !rows[0].equals("")) {
                    rows[i] = rows[i].trim();
                    String[] tile = rows[i].split(" ");
                    if (tile[0] != null && !tile[0].equals("") && tile[0].length() != 0) {
                        board[ctr][i] = new Tile(
                                tile[0].charAt(0), Integer.parseInt(tile[1]),
                                Integer.parseInt(tile[2]),
                                Integer.parseInt(tile[3]), Integer.parseInt(tile[4]),
                                Integer.parseInt(tile[5])
                        );
                    }
                }
            }
            ctr++;
        }

        if (numGuesses == 0) {
            // empty board saved
            w = new Wordle();
        } else {
            // played board saved
            w = new Wordle(board, ans, numGuesses);
        }

        repaint();
        status.setText("Saved game uploaded.");
    }

    // repaint
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw all the tiles
        Tile[][] board = w.returnBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    board[i][j].drawTile(g);
                }
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }

}
