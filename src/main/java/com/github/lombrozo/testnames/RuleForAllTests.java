package com.github.lombrozo.testnames;

public class RuleForAllTests implements Rule {

    private JavaTestCode tests;

    public RuleForAllTests(final JavaTestCode tests) {
        this.tests = tests;
    }

    @Override
    public void validate() throws WrongTestName {
        for (String test : tests.names()) {
            new PresentSimpleRule(test).validate();
        }
    }
}
