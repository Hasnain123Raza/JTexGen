package com.hr.jtexgen;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class MainTest {
    
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(MainSuite.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.getTestHeader());
            System.out.println(failure.getMessage());
            System.out.println(failure.getTrace());
        }

        System.out.println(result.wasSuccessful() ? "All tests passed." : "Test(s) failed.");
    }

}
