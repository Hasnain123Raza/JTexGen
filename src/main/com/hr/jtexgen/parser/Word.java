package com.hr.jtexgen.parser;

/**
 * Word class represents a word.
 * It is an immutable class with cached hashCode.
 * 
 * Word is defined as a lower-case, alphabetical string.
 * 
 * @author Hasnain Raza
 */
public final class Word implements Cloneable, Comparable<Word> {
    
    private final String word;
    private final int hashCode;

    /**
     * Constructs a new Word object.
     * Transforms the given string to lower-case and removes all non-alphabetical characters.
     * Throws IllegalArgumentException if the given string is or becomes (after transformations) null or empty.
     * 
     * @param word string from which the Word object is constructed.
     * @throws IllegalArgumentException
     */
    public Word(String word) throws IllegalArgumentException {
        if (word == null) {
            throw new IllegalArgumentException("Word cannot be null");
        }

        this.word = word.trim().toLowerCase().replaceAll("[^a-z]", "");
        if (this.word.length() == 0 || this.word.replaceAll("\\S*", "").length() > 0) {
            throw new IllegalArgumentException("Word was improperly formatted");
        }

        hashCode = this.word.hashCode();
    }

    /**
     * Clones this Word object.
     * 
     * @throws CloneNotSupportedException
     * @return a clone of this Word object.
     */
    @Override
    public Word clone() throws CloneNotSupportedException {
        return (Word) super.clone();
    }

    /**
     * Compares the word to another word.
     * 
     * @param other the word to compare to.
     * @return 0 if the words are equal, -1 if this word is less than the other word, 1 if this word is greater than the other word.
     */
    @Override
    public int compareTo(Word other) {
        return word.compareTo(other.word);
    }

    /**
     * Compares this word to another object.
     * 
     * @param other the object to compare to.
     * @return true if the other object is a Word and the words are equal, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (other == this) {
            return true;
        }

        if (other instanceof Word) {
            return word.equals(((Word) other).word);
        }
        return false;
    }

    /**
     * Returns the hash code of this word.
     * 
     * @return the hash code of this word.
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    /**
     * Returns the string representation of this word.
     * 
     * @return the string representation of this word.
     */
    @Override
    public String toString() {
        return word;
    }

}
