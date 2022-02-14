package com.hr.jtexgen.generator;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.util.HashMap;

import com.hr.jtexgen.parser.Word;
import com.hr.jtexgen.parser.Sentence;
import com.hr.jtexgen.parser.Text;

import com.hr.jtexgen.generator.WordRecord;
import com.hr.jtexgen.generator.WordRecordList;
import com.hr.jtexgen.generator.TextChain;

public class TextChainTest {
    
    @Test
    public void testTextChain() {
        Text text = new Text("Look again at that dot. That's here. That's home. That's us. On it everyone you love, everyone you know, everyone you ever heard of, every human being who ever was, lived out their lives.");
        TextChain textChain = new TextChain(text);

        WordRecordList startWords = textChain.getStartWords();
        WordRecordList endWords = textChain.getEndWords();
        HashMap<Word, WordRecordList> wordMap = textChain.getWordMap();

        assertEquals(3, startWords.getList().length);
        assertEquals(5, endWords.getList().length);
        assertEquals(22, wordMap.size());
    }

    @Test
    public void testMerge() {
        Text text1 = new Text("Hello world! How are you today? What are you up to?");
        Text text2 = new Text("Goodbye world! It was nice meeting you today. See you later!");

        TextChain textChain1 = new TextChain(text1);
        TextChain textChain2 = new TextChain(text2);

        textChain1.merge(textChain2, 0.25);

        WordRecordList startWords = textChain1.getStartWords();
        WordRecordList endWords = textChain1.getEndWords();
        HashMap<Word, WordRecordList> wordMap = textChain1.getWordMap();

        WordRecord startWordRecordsList[] = startWords.getList();
        assertEquals(6, startWordRecordsList.length);
        assertEquals("hello", startWordRecordsList[0].getWord().toString());
        assertEquals("how", startWordRecordsList[1].getWord().toString());
        assertEquals("what", startWordRecordsList[2].getWord().toString());
        assertEquals("goodbye", startWordRecordsList[3].getWord().toString());
        assertEquals("it", startWordRecordsList[4].getWord().toString());
        assertEquals("see", startWordRecordsList[5].getWord().toString());

        WordRecord endWordRecordsList[] = endWords.getList();
        assertEquals(4, endWordRecordsList.length);
        assertEquals("world", endWordRecordsList[0].getWord().toString());
        assertEquals("today", endWordRecordsList[1].getWord().toString());
        assertEquals("to", endWordRecordsList[2].getWord().toString());
        assertEquals("later", endWordRecordsList[3].getWord().toString());

        assertEquals(12, wordMap.size());
        WordRecordList youRecord = wordMap.get(new Word("you"));
        WordRecord youWordRecordsList[] = youRecord.getList();
        assertEquals(3, youWordRecordsList.length);
        assertEquals(1, youWordRecordsList[0].getWeight(), 0.001);
        assertEquals(0.75, youWordRecordsList[1].getWeight(), 0.001);
        assertEquals(0.25, youWordRecordsList[2].getWeight(), 0.001);
    }

}
