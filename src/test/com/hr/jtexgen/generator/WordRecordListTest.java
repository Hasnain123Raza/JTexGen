package com.hr.jtexgen.generator;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.hr.jtexgen.parser.Word;

import com.hr.jtexgen.generator.WordRecord;
import com.hr.jtexgen.generator.WordRecordList;

public class WordRecordListTest {
    
    @Test
    public void testAdd() {
        Word words[] = {
            new Word("hello"),
            new Word("world"),
            new Word("world"),
            new Word("how"),
            new Word("how"),
            new Word("how"),
            new Word("are"),
            new Word("you"),
            new Word("you"),
        };

        WordRecordList wordRecordList = new WordRecordList();
        for (Word word : words) {
            wordRecordList.add(word);
        }

        WordRecord wordRecords[] = wordRecordList.getList();
        assertEquals(5, wordRecords.length);
        assertEquals(1.0, wordRecords[0].getWeight(), 0.0001);
        assertEquals(2.0, wordRecords[1].getWeight(), 0.0001);
        assertEquals(3.0, wordRecords[2].getWeight(), 0.0001);
        assertEquals(1.0, wordRecords[3].getWeight(), 0.0001);
        assertEquals(2.0, wordRecords[4].getWeight(), 0.0001);
        assertEquals(9.0, wordRecordList.getTotalWeightedWords(), 0.0001);
    }

    @Test
    public void testAddAlpha() {
        Word words[] = {
            new Word("hello"),
            new Word("world"),
            new Word("world"),
            new Word("how"),
            new Word("how"),
            new Word("how"),
            new Word("are"),
            new Word("you"),
            new Word("you"),
        };

        WordRecordList wordRecordList = new WordRecordList();
        for (Word word : words) {
            wordRecordList.add(word, 0.5);
        }

        WordRecord wordRecords[] = wordRecordList.getList();
        assertEquals(5, wordRecords.length);
        assertEquals(0.5, wordRecords[0].getWeight(), 0.0001);
        assertEquals(1.0, wordRecords[1].getWeight(), 0.0001);
        assertEquals(1.5, wordRecords[2].getWeight(), 0.0001);
        assertEquals(0.5, wordRecords[3].getWeight(), 0.0001);
        assertEquals(1.0, wordRecords[4].getWeight(), 0.0001);
        assertEquals(4.5, wordRecordList.getTotalWeightedWords(), 0.0001);
    }

    @Test
    public void testSetEnd() {
        Word words[] = {
            new Word("hello"),
            new Word("hello"),
            new Word("hello"),
            new Word("world"),
            new Word("how"),
            new Word("how"),
            new Word("how"),
            new Word("are"),
            new Word("you"),
            new Word("you"),
        };

        WordRecordList wordRecordList = new WordRecordList();
        for (Word word : words) {
            wordRecordList.add(word);
        }

        wordRecordList.setEnd(words[3], true);

        WordRecord wordRecords[] = wordRecordList.getList();
        assertEquals(false, wordRecords[0].isEnd());
        assertEquals(true, wordRecords[1].isEnd());
        assertEquals(false, wordRecords[2].isEnd());
        assertEquals(false, wordRecords[3].isEnd());
        assertEquals(false, wordRecords[4].isEnd());
    }

    @Test
    public void testNormalize() {
        Word words[] = {
            new Word("hello"),
            new Word("world"),
            new Word("how"),
            new Word("how"),
            new Word("are"),
            new Word("you"),
            new Word("you"),
            new Word("you"),
        };

        WordRecordList wordRecordList = new WordRecordList();
        for (Word word : words) {
            wordRecordList.add(word);
        }

        wordRecordList.normalize();

        WordRecord wordRecords[] = wordRecordList.getList();
        assertEquals(0.125, wordRecords[0].getWeight(), 0.001);
        assertEquals(0.125, wordRecords[1].getWeight(), 0.001);
        assertEquals(0.25, wordRecords[2].getWeight(), 0.001);
        assertEquals(0.125, wordRecords[3].getWeight(), 0.001);
        assertEquals(0.375, wordRecords[4].getWeight(), 0.001);

        double total = 0.0;
        for (WordRecord wordRecord : wordRecords) {
            total += wordRecord.getWeight();
        }
        assertEquals(1.0, total, 0.001);
    }

    @Test
    public void testMultipleNormalize() {
        Word words[] = {
            new Word("hello"),
            new Word("hello"),
            new Word("world"),
            new Word("world"),
            new Word("world"),
            new Word("how"),
            new Word("how"),
            new Word("how"),
            new Word("how"),
            new Word("are"),
            new Word("are"),
            new Word("are"),
            new Word("are"),
            new Word("are"),
        };

        WordRecordList wordRecordList = new WordRecordList();
        for (int counter = 0; counter < 5; counter++) {
            wordRecordList.add(words[counter]);
        }

        assertEquals(1.0, wordRecordList.getWeightIncrement(), 0.001);
        assertEquals(2.0, wordRecordList.getList()[0].getWeight(), 0.001);
        assertEquals(3.0, wordRecordList.getList()[1].getWeight(), 0.001);

        wordRecordList.normalize();
        assertEquals(1.0 / 5, wordRecordList.getWeightIncrement(), 0.001);
        assertEquals(2.0 / 5, wordRecordList.getList()[0].getWeight(), 0.001);
        assertEquals(3.0 / 5, wordRecordList.getList()[1].getWeight(), 0.001);

        for (int counter = 5; counter < 9; counter++) {
            wordRecordList.add(words[counter]);
        }

        assertEquals(4.0 / 5, wordRecordList.getList()[2].getWeight(), 0.001);

        wordRecordList.normalize();
        assertEquals(1.0 / 9, wordRecordList.getWeightIncrement(), 0.001);
        assertEquals(2.0 / 9, wordRecordList.getList()[0].getWeight(), 0.001);
        assertEquals(3.0 / 9, wordRecordList.getList()[1].getWeight(), 0.001);
        assertEquals(4.0 / 9, wordRecordList.getList()[2].getWeight(), 0.001);

        for (int counter = 9; counter < words.length; counter++) {
            wordRecordList.add(words[counter]);
        }

        assertEquals(5.0 / 9, wordRecordList.getList()[3].getWeight(), 0.001);

        wordRecordList.normalize();
        assertEquals(1.0 / 14, wordRecordList.getWeightIncrement(), 0.001);
        assertEquals(2.0 / 14, wordRecordList.getList()[0].getWeight(), 0.001);
        assertEquals(3.0 / 14, wordRecordList.getList()[1].getWeight(), 0.001);
        assertEquals(4.0 / 14, wordRecordList.getList()[2].getWeight(), 0.001);
        assertEquals(5.0 / 14, wordRecordList.getList()[3].getWeight(), 0.001);
    }

}
