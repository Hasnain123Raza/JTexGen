package com.hr.jtexgen.parser;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Text class represents a text.
 * Text is defined as a sequence of sentences.
 * Sentences are delimited by ".!?" characters or newline.
 * 
 * @author Hasnain Raza
 */
public class Text {
    
    private ArrayList<Sentence> sentences;

    /**
     * Constructs a new Text object from a filename.
     * 
     * @param filename name of the file from which the Text object is constructed.
     * @return a new Text object.
     * @throws IOException if the file cannot be read.
     * @throws IllegalArgumentException if the given filename is null or empty, or if text is not properly formatted (see constructor).
     */
    public static Text fromFile(String filename) throws IOException, IllegalArgumentException {
        if (filename == null) {
            throw new IllegalArgumentException("File name cannot be null");
        }

        StringBuilder stringBuilder = new StringBuilder();
        try (FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {
                
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
        }

        return new Text(stringBuilder.toString());
    }

    /**
     * Constructs a new Text object.
     * Extracts sentences from the given text string.
     * Throws IllegalArgumentException if the given file is or becomes (after transformations) null or empty.
     * 
     * @param text string from which the Text object is constructed.
     * @throws IllegalArgumentException if the given file is or becomes (after transformations) null or empty.
     */
    public Text(String text) throws IllegalArgumentException {
        if (text == null) {
            throw new IllegalArgumentException("Text cannot be null");
        }

        sentences = new ArrayList<>();

        Scanner scanner = new Scanner(text);
        scanner.useDelimiter("[.!?\n]");
        while (scanner.hasNext()) {
            try {
                sentences.add(new Sentence(scanner.next()));
            } catch (IllegalArgumentException e) {
                // Ignore invalid sentences
            }
        }
        scanner.close();

        if (sentences.size() == 0) {
            throw new IllegalArgumentException("Text was improperly formatted");
        }
    }

    /**
     * Constructs a new empty Text object.
     */
    public Text() {
        sentences = new ArrayList<>();
    }

    /**
     * Adds a sentence to this text.
     * 
     * @param sentence sentence to be added.
     */
    public void addSentence(Sentence sentence) {
        sentences.add(sentence);
    }

    /**
     * Returns whether or not this text is empty.
     * 
     * @return whether or not this text is empty.
     */
    public boolean isEmpty() {
        return sentences.size() == 0;
    }

    /**
     * Returns the array of sentences in this text.
     * 
     * @return the array of sentences in this text.
     */
    public Sentence[] getSentences() {
        return sentences.toArray(new Sentence[sentences.size()]);
    }

    /**
     * Converts this text to a string.
     * 
     * @return a string representation of this text.
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Sentence sentence : sentences) {
            stringBuilder.append(sentence.toString() + ". ");
        }
        return stringBuilder.toString();
    }

}
