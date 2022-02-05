package com.hr.jtexgen.generator;

import com.hr.jtexgen.parser.Word;

import java.util.ArrayList;

/**
 * A class that represents a list of word records.
 * 
 * @author Hasnain Raza
 */
class WordRecordList {
    
    private ArrayList<WordRecord> list;
    private boolean isNormalized;

    /**
     * Returns an array of word records.
     * 
     * @return an array of word records.
     */
    public WordRecord[] getList() {
        return list.toArray(new WordRecord[list.size()]);
    }

    /**
     * Creates a new word record list.
     * Default isNormalized is false.
     */
    public WordRecordList() {
        list = new ArrayList<WordRecord>();
        isNormalized = false;
    }

    /**
     * Adds a word to the list.
     * To add a word, the list searches whether a word record with the same word already exists.
     * If it does, the weight of the word record is incremented by 1.
     * If it doesn't, a new word record is created and added to the list.
     * Adding a word also sets the isNormalized flag to false.
     * 
     * @param word the word to be added.
     */
    public void add(Word word) {
        isNormalized = false;

        for (WordRecord wordRecord : list) {
            if (wordRecord.getWord().equals(word)) {
                wordRecord.incrementWeight();
                return;
            }
        }

        list.add(new WordRecord(word));
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
        if (isNormalized) {
            return;
        }

        double sum = 0.0;
        for (WordRecord wordRecord : list) {
            sum += wordRecord.getWeight();
        }

        for (WordRecord wordRecord : list) {
            wordRecord.setWeight(wordRecord.getWeight() / sum);
        }

        isNormalized = true;
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

}
