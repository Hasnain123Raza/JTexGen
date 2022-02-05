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
        assertEquals(wordRecords.length, 5);
        assertEquals(wordRecords[0].getWeight(), 1.0, 0.0001);
        assertEquals(wordRecords[1].getWeight(), 2.0, 0.0001);
        assertEquals(wordRecords[2].getWeight(), 3.0, 0.0001);
        assertEquals(wordRecords[3].getWeight(), 1.0, 0.0001);
        assertEquals(wordRecords[4].getWeight(), 2.0, 0.0001);
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
        assertEquals(wordRecords[0].isEnd(), false);
        assertEquals(wordRecords[1].isEnd(), true);
        assertEquals(wordRecords[2].isEnd(), false);
        assertEquals(wordRecords[3].isEnd(), false);
        assertEquals(wordRecords[4].isEnd(), false);
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

}
