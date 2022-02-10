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

}
