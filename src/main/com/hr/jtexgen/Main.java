package com.hr.jtexgen;

import com.hr.jtexgen.parser.Text;

import com.hr.jtexgen.generator.TextChain;

import java.io.IOException;

/**
 * Main class for the JTexGen project.
 * 
 * @author Hasnain Raza
 */
public class Main {

    /**
     * Main method.
     * 
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        // Text text = null;

        // try {
        //     text = Text.fromFile("input.txt");
        // } catch (IllegalArgumentException e) {
        //     e.printStackTrace();
        //     System.exit(-1);
        // } catch (IOException e) {
        //     e.printStackTrace();
        //     System.exit(-1);
        // }

        // TextChain textChain = new TextChain(text);

        // System.out.println(textChain.generate(512, 1024));

        Text text1 = null;
        Text text2 = null;

        try {
            text1 = Text.fromFile("input1.txt");
            text2 = Text.fromFile("input2.txt");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        TextChain textChain1 = new TextChain(text1);
        TextChain textChain2 = new TextChain(text2);

        textChain1.merge(textChain2, 0.5);

        System.out.println(textChain1.generate(512, 1024));
    }

}