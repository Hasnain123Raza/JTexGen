package com.hr.jtexgen.generator;

import com.hr.jtexgen.parser.Text;
import com.hr.jtexgen.parser.Sentence;
import com.hr.jtexgen.parser.Word;

import java.util.HashMap;

/**
 * A class that represents a Markov chain for a weighted graph of words as the state.
 * 
 * @author Hasnain Raza
 */
public class TextChain {
    
    private WordRecordList startWords;
    private WordRecordList endWords;
    private HashMap<Word, WordRecordList> wordMap;

    /**
     * Returns a clone of the start words.
     * 
     * @return a clone of the start words.
     */
    public WordRecordList getStartWords() {
        return startWords.clone();
    }

    /**
     * Returns a clone of the end words.
     * 
     * @return a clone of the end words.
     */
    public WordRecordList getEndWords() {
        return endWords.clone();
    }

    /**
     * Returns a clone of the word map.
     * 
     * @return a clone of the word map.
     */
    public HashMap<Word, WordRecordList> getWordMap() {
        HashMap<Word, WordRecordList> clonedWordMap = new HashMap<>();

        for (Word word : wordMap.keySet()) {
            clonedWordMap.put(word, wordMap.get(word).clone());
        }

        return clonedWordMap;
    }

    /**
     * Creates a new text chain.
     * 
     * @param text the text to be used.
     * @throws IllegalArgumentException if the given text is null or empty.
     */
    public TextChain(Text text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            throw new IllegalArgumentException("Text cannot be empty");
        }

        startWords = new WordRecordList();
        endWords = new WordRecordList();
        wordMap = new HashMap<Word, WordRecordList>();

        digest(text);
    }

    /**
     * Generates text using the internal Markov chain.
     * 
     * @param minimumLength the minimum length of the generated text.
     * @param maximumLength the maximum length of the generated text.
     * @return the generated text.
     * @throws IllegalArgumentException if the given minimum length is less than 1 or greater than the maximum length.
     */
    public String generate(int minimumLength, int maximumLength) throws IllegalArgumentException {
        if (minimumLength < 1) {
            throw new IllegalArgumentException("Minimum length must be greater than 0");
        }

        if (maximumLength < minimumLength) {
            throw new IllegalArgumentException("Maximum length must be greater than or equal to minimum length");
        }

        StringBuilder stringBuilder = new StringBuilder();

        Word currentWord = startWords.select().getWord();
        int currentLength = 1;
        stringBuilder.append(currentWord.toString());

        while (currentLength < minimumLength) {
            WordRecordList currentWordRecordList = wordMap.get(currentWord);

            if (currentWordRecordList == null) {
                stringBuilder.append(".");
                if (currentLength >= maximumLength) {
                    break;
                }
                currentWord = startWords.select().getWord();
            } else {
                WordRecord wordRecord = currentWordRecordList.select();
                currentWord = wordRecord.getWord();

                if (wordRecord.isEnd()) {
                    if (currentWord.equals(endWords.select().getWord())) {
                        stringBuilder.append(currentWord + ".");
                        currentLength++;
                        if (currentLength >= maximumLength) {
                            break;
                        }
                        currentWord = startWords.select().getWord();
                    }
                }
            }

            stringBuilder.append(" " + currentWord);
            currentLength++;

            if (currentLength >= maximumLength) {
                break;
            }
        }

        return stringBuilder.toString();
    }

    /**
     * Merges the text chain with the given text chain.
     * The alpha value is used to determine how much the final result resemble the weights of the given text chain. 
     * 
     * @param textChain the TextChain to be merged.
     * @param alpha the alpha value to be used.
     * @throws IllegalArgumentException if text chain is null or alpha is less than or equal to 0 or greater than or equal to 1.
     */
    public void merge(TextChain textChain, double alpha) throws IllegalArgumentException {
        if (textChain == null) {
            throw new IllegalArgumentException("Text chain cannot be null");
        }
        if (alpha <= 0.0 || alpha >= 1.0) {
            throw new IllegalArgumentException("Alpha must be greater than zero and less than one.");
        }

        startWords.merge(textChain.startWords, alpha);
        endWords.merge(textChain.endWords, alpha);

        for (Word word : textChain.wordMap.keySet()) {
            WordRecordList wordRecordList = wordMap.get(word);

            if (wordRecordList == null) {
                wordRecordList = new WordRecordList();
                wordMap.put(word, wordRecordList);
            }

            wordRecordList.merge(textChain.wordMap.get(word), alpha);
        }

        WordRecordList emptyWordRecordList = new WordRecordList();
        for (Word word : wordMap.keySet()) {
            WordRecordList wordRecordList = textChain.wordMap.get(word);

            if (wordRecordList == null) {
                wordMap.get(word).merge(emptyWordRecordList, alpha);
            }
        }
    }

    /**
     * Digests the text and creates the internal Markov chain.
     * 
     * @param text the text to be digested.
     */
    private void digest(Text text) {
        for (Sentence sentence : text.getSentences()) {
            startWords.add(sentence.getFirstWord());
            endWords.add(sentence.getLastWord());

            Word[] words = sentence.getWords();
            for (int index = 1; index < words.length; index++) {
                Word currentWord = words[index - 1];
                Word nextWord = words[index];

                WordRecordList currentWordRecordList = wordMap.get(currentWord);

                if (currentWordRecordList == null) {
                    currentWordRecordList = new WordRecordList();
                    wordMap.put(currentWord, currentWordRecordList);
                }

                currentWordRecordList.add(nextWord);
                if (index == words.length - 1) {
                    currentWordRecordList.setEnd(words[index], true);
                }
            }
        }
    }

}
