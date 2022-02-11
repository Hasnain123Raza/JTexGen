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
     * Gets the word record.
     * 
     * @param word the word.
     * @return the word record.
     */
    public WordRecord getWordRecord(Word word) {
        for (WordRecord wordRecord : list) {
            if (wordRecord.getWord().equals(word)) {
                return wordRecord;
            }
        }

        return null;
    }

    /**
     * Creates a new word record list.
     * Default isNormalized is false.
     */
    public WordRecordList() {
        list = new ArrayList<WordRecord>();
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
        WordRecord wordRecord = getWordRecord(word);
        if (wordRecord != null) {
            wordRecord.incrementWeight(1.0);
            return;
        }

        WordRecord newWordRecord = new WordRecord(word);
        newWordRecord.incrementWeight(1.0);
        list.add(newWordRecord);
    }

    /**
     * Merges the word record list with the given word record list.
     * The word record list is merged with the current word record list by adding the words of the given word record list.
     * The alpha value determines how much the final result resemble the weights of the given word record list.
     * The alpha value must be between 0 and 1.
     * The closer alpha is to 0, the more the final result resemble the weights of the original word record list.
     * The closer alpha is to 1, the more the final result resemble the weights of the given word record list.
     * 
     * @param wordRecordList the word record list to be merged.
     * @param alpha the alpha value.
     * @throws IllegalArgumentException if alpha is less than or equal to 0 or greater than or equal to 1.
     */
    public void merge(WordRecordList wordRecordList, double alpha) throws IllegalArgumentException {
        if (wordRecordList == null) {
            throw new IllegalArgumentException("WordRecordList cannot be null.");
        }
        if (alpha <= 0.0 || alpha >= 1.0) {
            throw new IllegalArgumentException("Alpha must be greater than zero and less than one.");
        }

        for (WordRecord wordRecord : wordRecordList.list) {
            WordRecord foundWordRecord = getWordRecord(wordRecord.getWord());

            if (foundWordRecord != null) {
                foundWordRecord.setWeight(foundWordRecord.getWeight() * (1 - alpha) + wordRecord.getWeight() * alpha);
            } else {
                WordRecord newWordRecord = new WordRecord(wordRecord.getWord());
                newWordRecord.setWeight(wordRecord.getWeight() * alpha);
                list.add(newWordRecord);
            }
        }

        for (WordRecord wordRecord : list) {
            WordRecord foundWordRecord = wordRecordList.getWordRecord(wordRecord.getWord());
            
            if (foundWordRecord == null) {
                wordRecord.setWeight(wordRecord.getWeight() * (1 - alpha));
            }
        }
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
     * Returns a weighted random word record from the list.
     * Weighted random selection is used.
     * 
     * @return a weighted random word record from the list.
     */
    public WordRecord select() {
        double random = Math.random() * getTotalWeight();
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

    /**
     * Returns the total weight of the word records in the list.
     * 
     * @return the total weight of the word records in the list.
     */
    private double getTotalWeight() {
        double totalWeight = 0.0;
        for (WordRecord wordRecord : list) {
            totalWeight += wordRecord.getWeight();
        }

        return totalWeight;
    }

}
