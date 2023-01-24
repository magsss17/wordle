package org.cis1200.wordle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    // TESTING UPDATING LETTERS
    // test correct letter in right place
    @Test
    public void testCheckCorrectLetter() {
        Wordle w = new Wordle("hello");
        char c = 'L';
        Tile t = w.setElement(c, 2);
        assertEquals(t.getColor(), "green");
    }

    // test correct letter in wrong place
    @Test
    public void testCheckWrongPlaceLetter() {
        Wordle w = new Wordle("hello");
        char c = 'L';
        Tile t = w.setElement(c, 0);
        assertEquals(t.getColor(), "yellow");
    }

    // test wrong letter
    @Test
    public void testCheckWrongLetter() {
        Wordle w = new Wordle("hello");
        char c = 'P';
        Tile t = w.setElement(c, 3);
        assertEquals(t.getColor(), "red");
    }

    // TESTING CHECKGUESS METHOD
    // test valid
    @Test
    public void testCheckGuessAnswerIsValid() {
        Wordle w = new Wordle("hello");
        char[] chars = { 'H', 'E', 'L', 'L', 'O' };
        assertTrue(w.checkGuessAnswer(chars));
        assertEquals(1, w.getGuessNumber());
    }

    // test invalid word
    @Test
    public void testCheckGuessAnswerInvalidWord() {
        Wordle w = new Wordle("hello");
        char[] chars = { 't', 'r', 'a', 'b', 'c' };
        assertFalse(w.checkGuessAnswer(chars));
        assertEquals(0, w.getGuessNumber());
    }

    // test too long word
    // only first 5 chars are checked
    @Test
    public void testCheckGuessTooLongWrong() {
        Wordle w = new Wordle("hello");
        char[] chars = { 'P', 'U', 'R', 'P', 'L', 'E' };
        assertFalse(w.checkGuessAnswer(chars));
        assertEquals(0, w.getGuessNumber());
    }

    @Test
    public void testCheckGuessTooLongRight() {
        Wordle w = new Wordle("hello");
        char[] chars = { 'H', 'E', 'L', 'L', 'O', 'W', 'O', 'R', 'L', 'D' };
        assertTrue(w.checkGuessAnswer(chars));
        assertEquals(1, w.getGuessNumber());
        assertTrue(w.getWon());
    }

    // test too short word
    @Test
    public void testCheckGuessTooShort() {
        Wordle w = new Wordle("hello");
        char[] chars = { 'C', 'A', 'T' };
        assertThrows(IllegalArgumentException.class, () -> w.checkGuessAnswer(chars));
        assertEquals(0, w.getGuessNumber());
    }

    // test removing invalid chars is too short
    @Test
    public void testCheckGuessRemoveTooShort() {
        Wordle w = new Wordle("hello");
        char[] chars = { '$', 'D', '#', 'O', 'G' };
        assertThrows(IllegalArgumentException.class, () -> w.checkGuessAnswer(chars));
        assertEquals(0, w.getGuessNumber());
    }

    // test invalid chars right word
    @Test
    public void testCheckGuessRemoveRight() {
        Wordle w = new Wordle("hello");
        char[] chars = { '$', 'H', '#', 'E', 'L', '8', 'L', '*', 'O' };
        assertTrue(w.checkGuessAnswer(chars));
        assertEquals(1, w.getGuessNumber());
    }

    // test user won
    @Test
    public void testUserWon() {
        Wordle w = new Wordle("hello");
        char[] guess1 = { 'P', 'A', 'P', 'E', 'R' };
        assertTrue(w.checkGuessAnswer(guess1));
        assertEquals(1, w.getGuessNumber());
        assertFalse(w.getWon());
        assertFalse(w.getGameState());
        char[] guess2 = { 'H', 'E', 'L', 'L', 'O' };
        assertTrue(w.checkGuessAnswer(guess2));
        assertEquals(2, w.getGuessNumber());
        assertTrue(w.getWon());
        assertTrue(w.getGameState());
    }

    // test user won on 6th try
    @Test
    public void testUserWonLastTry() {
        Wordle w = new Wordle("hello");
        char[] guess1 = { 'P', 'A', 'P', 'E', 'R' };
        assertTrue(w.checkGuessAnswer(guess1));
        assertEquals(1, w.getGuessNumber());
        assertFalse(w.getWon());
        assertFalse(w.getGameState());
        char[] guess2 = { 'P', 'A', 'P', 'E', 'R' };
        assertTrue(w.checkGuessAnswer(guess2));
        assertEquals(2, w.getGuessNumber());
        assertFalse(w.getWon());
        char[] guess3 = { 'P', 'A', 'P', 'E', 'R' };
        assertTrue(w.checkGuessAnswer(guess3));
        assertEquals(3, w.getGuessNumber());
        assertFalse(w.getWon());
        char[] guess4 = { 'P', 'A', 'P', 'E', 'R' };
        assertTrue(w.checkGuessAnswer(guess4));
        assertEquals(4, w.getGuessNumber());
        assertFalse(w.getWon());
        char[] guess5 = { 'P', 'A', 'P', 'E', 'R' };
        assertTrue(w.checkGuessAnswer(guess5));
        assertEquals(5, w.getGuessNumber());
        assertFalse(w.getWon());
        char[] guess6 = { 'H', 'E', 'L', 'L', 'O' };
        assertTrue(w.checkGuessAnswer(guess6));
        assertEquals(6, w.getGuessNumber());
        assertTrue(w.getWon());
        assertTrue(w.getGameState());
    }

    // test gameOver after 6 tries
    @Test
    public void testGameOverOutOfTries() {
        Wordle w = new Wordle("hello");
        char[] guess1 = { 'P', 'A', 'P', 'E', 'R' };
        assertTrue(w.checkGuessAnswer(guess1));
        assertEquals(1, w.getGuessNumber());
        assertFalse(w.getWon());
        assertFalse(w.getGameState());
        char[] guess2 = { 'P', 'A', 'P', 'E', 'R' };
        assertTrue(w.checkGuessAnswer(guess2));
        assertEquals(2, w.getGuessNumber());
        assertFalse(w.getWon());
        char[] guess3 = { 'P', 'A', 'P', 'E', 'R' };
        assertTrue(w.checkGuessAnswer(guess3));
        assertEquals(3, w.getGuessNumber());
        assertFalse(w.getWon());
        char[] guess4 = { 'P', 'A', 'P', 'E', 'R' };
        assertTrue(w.checkGuessAnswer(guess4));
        assertEquals(4, w.getGuessNumber());
        assertFalse(w.getWon());
        char[] guess5 = { 'P', 'A', 'P', 'E', 'R' };
        assertTrue(w.checkGuessAnswer(guess5));
        assertEquals(5, w.getGuessNumber());
        assertFalse(w.getWon());
        char[] guess6 = { 'P', 'E', 'A', 'C', 'E' };
        assertTrue(w.checkGuessAnswer(guess6));
        assertEquals(6, w.getGuessNumber());
        assertFalse(w.getWon());
        assertTrue(w.getGameState());
    }
}