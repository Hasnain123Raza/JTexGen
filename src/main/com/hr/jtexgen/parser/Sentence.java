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
     * Constructs a new empty Sentence object.
     */
    public Sentence() {
        words = new ArrayList<>();
    }

    /**
     * Adds a word to this sentence.
     * 
     * @param word word to be added.
     */
    public void addWord(Word word) {
        words.add(word);
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

    /**
     * Returns whether or not this sentence is empty.
     * 
     * @return whether or not this sentence is empty.
     */
    public boolean isEmpty() {
        return words.size() == 0;
    }

    /**
     * Compares this sentence with another for equality.
     * 
     * @param other the other sentence to compare with.
     * @return true if the two sentences are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (other instanceof Sentence) {
            Sentence otherSentence = (Sentence) other;
            if (words.size() != otherSentence.words.size()) {
                return false;
            }

            for (int index = 0; index < words.size(); index++) {
                if (!words.get(index).equals(otherSentence.words.get(index))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Converts to string.
     * 
     * @return the string representation of this sentence.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        
        for (int counter = 0; counter < words.size(); counter++) {
            builder.append(words.get(counter).toString());
            if (counter < words.size() - 1) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

}
