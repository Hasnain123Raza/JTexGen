package com.hr.jtexgen;

import java.io.IOException;

import com.hr.jtexgen.parser.Text;

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
        Text text = null;

        try {
            text = Text.fromFile("input.txt");
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            System.exit(-1);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println(text);
        System.out.println("Hello, World!");
    }

}