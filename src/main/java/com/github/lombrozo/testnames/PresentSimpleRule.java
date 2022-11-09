package com.github.lombrozo.testnames;

import java.util.Arrays;
import java.util.Collection;

public final class PresentSimpleRule implements Rule {

    private final Collection<Rule> all;

    public PresentSimpleRule(final String test) {
        this.all = Arrays.asList(
            new NotCamelCase(test),
            new NotContainsTestWord(test),
            new NotSpam(test),
            new NotUsesSpecialCharacters(test),
            new PresentTense(test)
        );
    }

    @Override
    public void validate() throws WrongTestName {
        for (final Rule rule : all) {
            rule.validate();
        }
    }
}
