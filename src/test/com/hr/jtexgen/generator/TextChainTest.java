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

}
