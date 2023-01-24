=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 1200 Game Project README
PennKey: mschwier
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Array
        My game board is represented by a 6x5 2D array of Tile Objects. Each row will be a 5-letter word guess.
        The user has 6 guesses, hench the 6 rows. Each element in the array is a Tile that stores the letter, color,
        and position for each letter in the guess. It will be colored yellow, gray, or red according to whether or
        not the answer contains the character. I used a Tile type as opposed to characters or integers because I could
        encompass all attributes that a single letter would have.

  2. Collections
        The following will be stored as a list collection: all valid 5-letter words, certain 5-letter words that I
        want to be a candidate for the answer, and the current guesses. A collection rather than an array is needed
        because I will be removing the randomly generated answer from the answer candidates so that the user doesn't
        get repeat words. I chose to use collections as opposed to maps because the data value doesn't require a key to
        be accessed and it's not linked to functionality of a different variable. I chose to use an ArrayList for the
        guesses because I wanted to maintain the order of the guesses while also easily adding guesses. I used a
        LinkedList for the valid letters and answers because I wanted to easily remove used words from the answers (as
        mentioned above) and it made iteration easier when reading the data from the File I/O.

  3. JUnit Testable Component
        I tested elements of the Wordle class to make sure that the game board was updating correctly. I will check
        both the checkGuessAnswer method and the checkLetter method. The checkGuessAnswer method checks whether the
        first 5 chars of a word is a valid word and whether it is the correct answer, updating the gameOver and gameWon
        accordingly. The checkLetter method updates the Tile state depending on the letter and its placement in the
        word.
        To accomplish testing, I've added extra constructors and method getters so that I can control aspects of the
        gameboard, like creating a Wordle constructor that takes in a String as a parameter and sets the answer to
        that string. I also added a getColor method in the Tile class so that I can check that the Tile is updating its
        color correctly.

  4. File I/O
        I will be using a file for 2 main functionalities:
        1. Uploading words - I imported a list of all 5-letter words and a smaller list of 5-letter words that I want
        to be part of the possible answers in 2 separate files. I used a buffered reader and file line iterator to
        add all the words in the file into the corresponding lists.
        2. Saving game board - I will save the current state of the game board so that when the user opens it again,
        the same game state will be displayed. The file saves the current answer, number of guesses that has been used,
        and each non-empty Tile and its state.

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

  Dictionary class - reads the files containing the word lists and also has a method to return a random word out of
  the answer list

  Wordle class - codes the basic functionality and outline of the Wordle board by initiating the array representing
  the board and getting a random word from the answer list to be the answer.

  FileLineIterator class - copy of the FileLineIterator class from Twitter bot used to read the information in the list
  of words to place them in the list.

  RunWordle class - takes care of the formatting of the Wordle frame window, button functionality, etc.

  GameBoard class - Implements Wordle functionality with user input and graphics. Contains functions that code button
  functionality, draw functionality, and what to do when the user presses the relevant keys.

  Tile class - Stores the state of each tile in the game (including letter, color, and position). Is able to check a
  letter and update its state/color accordingly. Also has code to draw each individual tile.


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  While I was coding, I ran into trouble trying to maintain all the states of each letter and trying to check and
  update states in multiple places. To solve this, I changed my design to implement a Tile object that stored the state
  of each letter in one place, which made my future coding a lot easier, especially with drawing the Tile objects. I
  also had trouble with implementing a way to only make the letters change colors after the enter key is pressed. To
  solve this, I had to introduce another variable (show) and another method showTile to dictate whether the Tile state
  should be shown yet when drawing it.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think that I encapsulated my game pretty well. For example, for the returnBoard function of the Wordle class,
  rather than directly returning the Wordle board, I return a copy of the array to ensure that the actual board can't
  be altered outside the class. This is similar to the Tile class, where you can only modify the state of tiles (ie
  letter, color/state) by using the provided setter methods. Additionally, in the Dictionary class, I return copies
  of the collections as opposed to directly returning lists. I also tried to take advantage of using as many private
  methods as possible within classes to encapsulated the private state and reduce outside exposure.
  I would refactor the structure of my Wordle class and the methods I used. When I started, I planned on storing just
  the characters of the letters in the 2D array. However, I ran into trouble trying to maintain states of each letter
  as I coded. So, I think that my Wordle class isn't as efficient as I could be since I had originally coded it for a
  2D char array and changed only a minimal amount of lines for it to fit with the Tile object. I also think that there
  is some redundancy in my code that could be reduced with all the checking. If I were to do it again, I would check
  letters and words all in one place.


========================
=: External Resources :=
========================

- Cite any external resources (images, tutorials, etc.) that you may have used 
  while implementing your game.

  JavaDocs for key typing: https://docs.oracle.com/javase/7/docs/api/java/awt/event/KeyEvent.html#KEY_TYPED
  Word List: https://www.wordunscrambler.net/words/5-letter


