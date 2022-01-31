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
     * Constructs a new Text object.
     * Extracts sentences from the given file.
     * Throws IllegalArgumentException if the given file is or becomes (after transformations) null or empty.
     * 
     * @param filename name of the file from which the Text object is constructed.
     * @throws IllegalArgumentException if the given file is or becomes (after transformations) null or empty.
     */
    public Text(String filename) throws IOException, IllegalArgumentException {
        if (filename == null) {
            throw new IllegalArgumentException("File name cannot be null");
        }

        sentences = new ArrayList<>();

        try (FileReader fileReader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter("[.!?]");
                while (scanner.hasNext()) {
                    try {
                        sentences.add(new Sentence(scanner.next()));
                    } catch (IllegalArgumentException e) {
                        // Ignore invalid sentences
                    }
                }
                scanner.close();
            }   
        }

        if (sentences.size() == 0) {
            throw new IllegalArgumentException("File was improperly formatted");
        }
    }

    /**
     * Returns the array of sentences in this text.
     * 
     * @return the array of sentences in this text.
     */
    public Sentence[] getSentences() {
        return sentences.toArray(new Sentence[sentences.size()]);
    }

}
