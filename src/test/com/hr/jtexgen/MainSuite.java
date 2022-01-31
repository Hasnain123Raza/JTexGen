package com.hr.jtexgen;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.hr.jtexgen.parser.ParserSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    ParserSuite.class,
})
public class MainSuite {
    
}
