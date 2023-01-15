package com.github.lombrozo.testnames;

import java.util.Collection;

final class RuleForAllTests implements Rule {

    private final Cases tests;

    RuleForAllTests(final Cases test) {
        this.tests = test;
    }

    @Override
    public void validate() throws WrongTestName {
        final Collection<TestCase> names = this.tests.all();
        for (final TestCase test : names) {
            new PresentSimpleRule(test.name()).validate();
        }
    }
}