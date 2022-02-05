package com.hr.jtexgen;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.hr.jtexgen.parser.ParserSuite;
import com.hr.jtexgen.generator.GeneratorSuite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    ParserSuite.class,
    GeneratorSuite.class,
})
public class MainSuite {
    
}
