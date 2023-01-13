package com.github.lombrozo.testnames;

import java.util.Collection;

final class RuleForAllTests implements Rule {

    private final Names tests;

    RuleForAllTests(final Names test) {
        this.tests = test;
    }

    @Override
    public void validate() throws WrongTestName {
        final Collection<String> names = this.tests.names();
        for (final String test : names) {
            new PresentSimpleRule(test).validate();
        }
    }
}