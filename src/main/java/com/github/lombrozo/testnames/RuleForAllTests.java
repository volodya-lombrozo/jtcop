package com.github.lombrozo.testnames;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class RuleForAllTests implements Rule {

    private final JavaTestCode tests;

    public RuleForAllTests(final JavaTestCode tests) {
        this.tests = tests;
    }

    @Override
    public void validate() throws WrongTestName {
        final Collection<String> names = tests.names();
        final List<WrongTestName> exceptions = new ArrayList<>(names.size());
        for (String test : names) {
            try {
                new PresentSimpleRule(test).validate();
            } catch (WrongTestName ex){
                exceptions.add(ex);
            }
        }
        if(!exceptions.isEmpty()){
            throw new WrongTestName(exceptions);
        }
    }
}
