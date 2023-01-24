package org.cis1200.wordle;

import java.util.LinkedList;
import java.util.List;

/**
 * Stores all valid 5-letter words and a smaller set of potential answers for
 * the Wordle game. All strings are stored in uppercase to avoid casing mismatch
 * in the game.
 */
public class Dictionary {
    // list of all valid 5-letter words (UPPERCASE)
    private List<String> valid = new LinkedList<>();
    // list of all possible answers for Wordle (UPPERCASE)
    private List<String> answers = new LinkedList<>();

    public Dictionary() {
        FileLineIterator flValid = new FileLineIterator("files/AllStrings");
        FileLineIterator flAnswers = new FileLineIterator("files/Answers");
        while (flValid.hasNext()) {
            valid.add(flValid.next().toUpperCase());
        }
        while (flAnswers.hasNext()) {
            answers.add(flAnswers.next().toUpperCase());
        }
    }

    // returns encapsulated valid strings
    public List<String> getValid() {
        List<String> val = new LinkedList<>();
        for (String s : valid) {
            val.add(s);
        }
        return val;
    }

    // gets a random word from possible answer list
    public String getRandomAns() {
        int max = answers.size() - 1;
        int randomInt = (int) Math.floor(Math.random() * (max + 1));
        return answers.get(randomInt);
    }

    // remove already used string from valid answers
    public void removeAns(String ans) {
        answers.remove(ans.toUpperCase());
    }

    // testing returning a random answer
    public static void main(String[] args) {
        Dictionary d = new Dictionary();
        String a = d.getRandomAns();
        System.out.println(a);
    }
}
