package com.hr.jtexgen.parser;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Sentence class represents a sentence.
 * Sentence is defined as a sequence of words.
 * Words are delimited by whitespace.
 * 
 * @author Hasnain Raza
 */
public class Sentence {
    
    private final ArrayList<Word> words;

    /**
     * Constructs a new Sentence object.
     * Extracts tokens from the given string delimited by whitespace.
     * Throws IllegalArgumentException if the given string is or becomes (after transformations) null or empty.
     * 
     * @param sentence string from which the Sentence object is constructed.
     * @throws IllegalArgumentException if the given string is or becomes (after transformations) null or empty.
     */
    public Sentence(String sentence) throws IllegalArgumentException {
        if (sentence == null) {
            throw new IllegalArgumentException("Sentence cannot be null");
        }

        words = new ArrayList<>();

        sentence = sentence.trim();

        if (sentence.replaceAll("[^%.!%?\n]*", "").length() > 0) {
            throw new IllegalArgumentException("Sentence was improperly formatted");
        }

        Scanner scanner = new Scanner(sentence);
        while (scanner.hasNext()) {
            try {
                words.add(new Word(scanner.next()));
            } catch (IllegalArgumentException e) {
                // Ignore invalid words
            }
        }
        scanner.close();

        if (words.size() == 0) {
            throw new IllegalArgumentException("Sentence was improperly formatted");
        }
    }

    /**
     * Returns the array of words in this sentence.
     * 
     * @return the array of words in this sentence.
     */
    public Word[] getWords() {
        return words.toArray(new Word[words.size()]);
    }

    /**
     * Returns the first word in this sentence.
     * 
     * @return the first word in this sentence.
     */
    public Word getFirstWord() {
        return words.get(0);
    }

    /**
     * Returns the last word in this sentence.
     * 
     * @return the last word in this sentence.
     */
    public Word getLastWord() {
        return words.get(words.size() - 1);
    }

}
