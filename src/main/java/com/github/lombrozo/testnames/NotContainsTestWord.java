package com.github.lombrozo.testnames;

public class NotContainsTestWord implements Rule {

    public NotContainsTestWord(final String test) {
        this.test = test;
    }

    final String test;

    @Override
    public void validate() throws WrongTestName {
        if (containsTest()) {
            throw new WrongTestName(test, "test name doesn't have to contain the word 'test'.");
        }
    }

    private boolean containsTest() {
        return test.matches(".*[Tt][Ee][Ss][Tt].*");
    }
}
