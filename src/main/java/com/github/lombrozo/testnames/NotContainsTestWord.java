package com.github.lombrozo.testnames;

public class NotContainsTestWord implements Rule {

    final TestCase test;

    public NotContainsTestWord(final TestCase test) {
        this.test = test;
    }

    @Override
    public void validate() throws WrongTestName {
        if (containsTest()) {
            throw new WrongTestName(test, "test name doesn't have to contain the word 'test'");
        }
    }

    private boolean containsTest() {
        return test.name().matches(".*[Tt][Ee][Ss][Tt].*");
    }
}
