package com.github.lombrozo.testnames;

import java.util.Collection;

public final class RuleForAllTests implements Rule {

    private final JavaTestCode tests;

    RuleForAllTests(final JavaTestCode tests) {
        this.tests = tests;
    }

    @Override
    public void validate() throws WrongTestName {
        final Collection<String> names = tests.names();
        for (String test : names) {
            new PresentSimpleRule(test).validate();
        }
    }
}