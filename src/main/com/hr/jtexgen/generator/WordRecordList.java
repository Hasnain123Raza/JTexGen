package com.hr.jtexgen.generator;

import com.hr.jtexgen.parser.Word;

import java.util.ArrayList;

/**
 * A class that represents a list of word records.
 * 
 * @author Hasnain Raza
 */
class WordRecordList implements Cloneable {
    
    private ArrayList<WordRecord> list;
    private boolean isNormalized;

    private double totalWeightedWords;
    private double totalWeight;
    private double weightIncrement;

    /**
     * Returns an array of word records.
     * 
     * @return an array of word records.
     */
    public WordRecord[] getList() {
        WordRecord[] wordRecords = new WordRecord[list.size()];

        for (int index = 0; index < list.size(); index++) {
            wordRecords[index] = list.get(index).clone();
        }

        return wordRecords;
    }

    /**
     * Gets the total weighted words.
     * 
     * @return the total weighted words.
     */
    public double getTotalWeightedWords() {
        return totalWeightedWords;
    }

    /**
     * Gets the weight increment.
     * 
     * @return the weight increment.
     */
    public double getWeightIncrement() {
        return weightIncrement;
    }

    /**
     * Creates a new word record list.
     * Default isNormalized is false.
     */
    public WordRecordList() {
        list = new ArrayList<WordRecord>();
        isNormalized = false;

        totalWeightedWords = 0;
        totalWeight = 0;
        weightIncrement = 1;
    }

    /**
     * Adds a word to the list.
     * To add a word, the list searches whether a word record with the same word already exists.
     * If it does, the weight of the word record is incremented by one.
     * If it doesn't, a new word record is created and added to the list with weight equal to one.
     * Adding a word also sets the isNormalized flag to false.
     * 
     * @param word the word to be added.
     */
    public void add(Word word) {
        add(word, 1.0);
    }

    /**
     * Adds a word to the list with an alpha value.
     * To add a word, the list searches whether a word record with the same word already exists.
     * If it does, the weight of the word record is incremented by the alpha value.
     * If it doesn't, a new word record is created and added to the list with weight equal to the alpha value.
     * Adding a word also sets the isNormalized flag to false.
     * 
     * @param word the word to be added.
     * @param alpha the alpha value.
     * @throws IllegalArgumentException if alpha is less than or equal to zero or alpha is greater than one.
     */
    public void add(Word word, double alpha) throws IllegalArgumentException {
        if (alpha <= 0 || alpha > 1) {
            throw new IllegalArgumentException("Alpha must be greater than zero and less than or equal to one.");
        }

        isNormalized = false;
        totalWeightedWords += alpha;
        totalWeight += weightIncrement * alpha;

        for (WordRecord wordRecord : list) {
            if (wordRecord.getWord().equals(word)) {
                wordRecord.incrementWeight(weightIncrement * alpha);
                return;
            }
        }

        WordRecord newWordRecord = new WordRecord(word);
        newWordRecord.incrementWeight(weightIncrement * alpha);
        list.add(newWordRecord);
    }

    /**
     * Sets the isEnd flag of the word record associated with the word to isEnd.
     * If a word record with the word doesn't exist, nothing happens.
     * 
     * @param word the word whose isEnd flag is to be set.
     * @param isEnd the value of the isEnd flag. 
     */
    public void setEnd(Word word, boolean isEnd) {
        for (WordRecord wordRecord : list) {
            if (wordRecord.getWord().equals(word)) {
                wordRecord.setEnd(isEnd);
                return;
            }
        }
    }

    /**
     * Normalizes the weights of the word records in the list.
     * The weights of the word records are normalized by dividing them by the sum of the weights.
     */
    public void normalize() {
        if (isNormalized || totalWeightedWords == 0) {
            return;
        }

        for (WordRecord wordRecord : list) {
            wordRecord.setWeight(wordRecord.getWeight() / totalWeight);
        }

        isNormalized = true;
        totalWeight = 1;
        weightIncrement = (double) 1 / totalWeightedWords;
    }

    /**
     * Returns a weighted random word record from the list.
     * Weighted random selection is used.
     * 
     * @return a weighted random word record from the list.
     */
    public WordRecord select() {
        normalize();

        double random = Math.random();
        for (WordRecord wordRecord : list) {
            random -= wordRecord.getWeight();

            if (random <= 0.0) {
                return wordRecord;
            }
        }

        return null;
    }

    /**
     * Clones the word record list.
     * 
     * @return the cloned word record list.
     */
    public WordRecordList clone() {
        WordRecordList wordRecordList = new WordRecordList();
        for (WordRecord wordRecord : list) {
            wordRecordList.list.add(wordRecord.clone());
        }

        return wordRecordList;
    }

}
