package com.hr.jtexgen.parser;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.hr.jtexgen.parser.Sentence;

public class SentenceTest {
    
    @Test
    public void testNormalSentence() {
        Sentence sentence = new Sentence("How are you");
        
        assertEquals("how", sentence.getFirstWord().toString());
        assertEquals("you", sentence.getLastWord().toString());
        assertEquals(
            new Word[] { new Word("how"), new Word("are"), new Word("you") },
            sentence.getWords()
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTwoSentences() {
        Sentence sentence = new Sentence("How are you. How are you?");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptySentence() {
        Sentence sentence = new Sentence("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullSentence() {
        Sentence sentence = new Sentence(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidSentence() {
        Sentence sentence = new Sentence("123 456 213123 342");
    }

}
