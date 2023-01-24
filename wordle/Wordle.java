package org.cis1200.wordle;

import java.util.ArrayList;
import java.util.List;

/**
 * This class will instantiate the Wordle Board and its functionality
 */
public class Wordle {
    private Tile[][] board;
    private boolean gameOver;
    private List<String> guesses;
    private String answer;
    private int guessNumber;
    private List<String> valid;
    private boolean won;

    public Wordle() {
        reset();
    }

    // initiate wordle from saved game state
    public Wordle(Tile[][] board, String ans, int guessNum) {
        this.board = board;
        gameOver = false;
        won = false;
        guessNumber = guessNum;
        Dictionary d = new Dictionary();
        valid = d.getValid();
        answer = ans;
        guesses = new ArrayList<>();
        System.out.println(answer);
    }

    // for testing purposes
    public Wordle(String ans) {
        board = new Tile[5][6];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // initialize blank tiles
                board[i][j] = new Tile((i * 65) + 20, (j * 65) + 50);
            }
        }
        gameOver = false;
        won = false;
        guessNumber = 0;
        Dictionary d = new Dictionary();
        valid = d.getValid();
        answer = ans.toUpperCase();
        guesses = new ArrayList<>();
        System.out.println(answer);
    }

    // check whether a guess is the correct answer
    // return false for invalid guess, true for valid guess
    public boolean checkGuessAnswer(char[] guessChars) {
        String guess = "";

        // add first 5 valid chars to form a string
        for (int i = 0; guess.length() < 5 && i < guessChars.length; i++) {
            char c = guessChars[i];
            if (Character.isLetter(c)) {
                guess += c;
            }
        }

        // string is too short
        if (guess.length() < 5) {
            throw new IllegalArgumentException();
        }

        // check if the guess is a valid string
        if (!checkGuessValid(guess)) {
            return false;
        }

        for (int i = 0; i < board.length; i++) {
            Tile t = board[i][guessNumber];
            t.showTile();
        }

        // valid guess
        guesses.add(guess);
        guessNumber++;
        if (guessNumber >= 6) {
            gameOver = true;
        }
        if (answer.equals(guess)) {
            won = true;
            gameOver = true;
        }
        return true;
    }

    // check if the guess is a valid string
    private boolean checkGuessValid(String guess) {
        return valid.contains(guess);
    }

    // set board element based on position
    // returns int based on whether it's correct
    public Tile setElement(char c, int col) {
        Tile t = board[col][guessNumber];
        t.changeTileColor(checkLetter(c, col));
        board[col][guessNumber] = t.setChar(c);
        return t;
    }

    // returns 0 if letter is not in string
    // 1 if letter is in string but wrong place
    // 2 if letter is in string and right place
    private int checkLetter(char c, int col) {
        char[] ans = new char[5];
        boolean contains = false;

        // parse answer string into an array of chars
        for (int i = 0; i < ans.length; i++) {
            ans[i] = answer.charAt(i);
            if (ans[i] == c) {
                contains = true;
            }
        }

        if (ans[col] == c) {
            // c is in the correct place in the answer
            return 2;
        } else if (contains) {
            // c is in the answer but wrong place
            return 1;
        }
        // c is not in the answer
        return 0;
    }

    // return whether the game is over
    public boolean getGameState() {
        return gameOver;
    }

    public int getGuessNumber() {
        return guessNumber;
    }

    public boolean getWon() {
        return won;
    }

    public String getAnswer() {
        return answer;
    }

    // for testing
    public void setAnswer(String ans) {
        answer = ans;
    }

    public void reset() {
        board = new Tile[5][6];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // initialize blank tiles
                board[i][j] = new Tile((i * 65) + 20, (j * 65) + 50);
            }
        }
        gameOver = false;
        won = false;
        guessNumber = 0;
        Dictionary d = new Dictionary();
        valid = d.getValid();
        answer = d.getRandomAns();
        d.removeAns(answer);
        guesses = new ArrayList<>();
        System.out.println(answer);
    }

    // return encapsulated board
    public Tile[][] returnBoard() {
        Tile[][] ret = new Tile[5][6];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                ret[i][j] = board[i][j];
            }
        }
        return ret;
    }

}
