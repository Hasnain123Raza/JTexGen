package com.hr.jtexgen.generator;

import com.hr.jtexgen.parser.Word;

/**
 * A class that represents a word record.
 * A word record is used to hold the word, it's frequency or weight, and whether it's an end word.
 * 
 * @author Hasnain Raza
 */
class WordRecord implements Cloneable {
    
    private Word word;
    private double weight;
    private boolean isEnd;

    /**
     * Returns the word.
     * 
     * @return the word.
     */
    public Word getWord() {
        return word;
    }

    /**
     * Returns the weight.
     * 
     * @return the weight.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Increments the weight by amount.
     * 
     * @param amount the amount to increment the weight by.
     */
    public void incrementWeight(double amount) {
        weight += amount;
    }

    /**
     * Sets the weight.
     * 
     * @param weight the weight.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Returns whether the word is an end word.
     * 
     * @return whether the word is an end word.
     */
    public boolean isEnd() {
        return isEnd;
    }

    /**
     * Sets whether the word is an end word.
     * 
     * @param isEnd whether the word is an end word.
     */
    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    /**
     * Constructs a new word record.
     * The default weight is 0.
     * The default isEnd is false.
     * 
     * @param word the word.
     */
    public WordRecord(Word word) {
        this.word = word;
        this.weight = 0;
        this.isEnd = false;
    }

    /**
     * Clones the word record.
     * 
     * @return the cloned word record.
     */
    public WordRecord clone() {
        WordRecord wordRecord = new WordRecord(word);
        wordRecord.setWeight(weight);
        wordRecord.setEnd(isEnd);
        return wordRecord;
    }

}
