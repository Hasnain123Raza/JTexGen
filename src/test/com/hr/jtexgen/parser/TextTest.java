package com.hr.jtexgen.parser;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

import com.hr.jtexgen.parser.Text;

public class TextTest {

    @Test
    public void testNormalText() {
        Text text = new Text("How are you. What are you doing?\nIs everything ok?");

        assertEquals(
            new Sentence[] {
                new Sentence("How are you"),
                new Sentence("What are you doing"),
                new Sentence("Is everything ok")
            },
            text.getSentences()
        );
    }

    @Test
    public void testAbnormalText() {
        Text text = new Text("How are you. ??123??\nIs everything ok?");
        assertEquals(
            new Sentence[] {
                new Sentence("How are you"),
                new Sentence("Is everything ok")
            },
            text.getSentences()
        );
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyText() {
        Text text = new Text("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullText() {
        Text text = new Text(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidText() {
        Text text = new Text("123..?.456");
    }

}
