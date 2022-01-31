package com.hr.jtexgen.parser;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.hr.jtexgen.parser.Word;

public class WordTest {
    
    @Test
    public void testLowerCaseFormat() {
        Word word = new Word("Hello");
        assertEquals("hello", word.toString());
    }

    @Test
    public void testAlphabeticFormat() {
        Word word = new Word("{}123hello123..");
        assertEquals("hello", word.toString());
    }

    @Test
    public void testTrimFormat() {
        Word word = new Word("  hello  ");
        assertEquals("hello", word.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyWord() {
        Word word = new Word("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullWord() {
        Word word = new Word(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidWord() {
        Word word = new Word("123..?.456");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSpacedWord() {
        Word word = new Word("hello world");
    }

}
