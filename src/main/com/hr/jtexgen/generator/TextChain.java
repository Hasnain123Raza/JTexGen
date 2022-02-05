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
     * Creates a new text chain.
     * 
     * @param text the text to be used.
     */
    public TextChain(Text text) {
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
     */
    public String generate(int minimumLength, int maximumLength) {
        StringBuilder stringBuilder = new StringBuilder();

        Word currentWord = startWords.select().getWord();
        int currentLength = 1;
        stringBuilder.append(currentWord.toString());

        while (currentLength < minimumLength) {
            WordRecordList currentWordRecordList = wordMap.get(currentWord);

            if (currentWordRecordList == null) {
                if (stringBuilder.charAt(stringBuilder.length() - 1) != '.') {
                    stringBuilder.append(".");
                }
                currentWord = startWords.select().getWord();
                if (currentLength >= maximumLength) {
                    break;
                }
                continue;
            } else {
                WordRecord wordRecord = currentWordRecordList.select();
                currentWord = wordRecord.getWord();

                if (wordRecord.isEnd()) {
                    if (currentWord.equals(endWords.select().getWord())) {
                        stringBuilder.append(currentWord + ".");
                        currentWord = startWords.select().getWord();
                        currentLength++;
                        if (currentLength >= maximumLength) {
                            break;
                        }
                        continue;
                    }
                }

                stringBuilder.append(" " + currentWord);
                currentLength++;

                if (currentLength >= maximumLength) {
                    break;
                }
            }
        }

        return stringBuilder.toString();
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
