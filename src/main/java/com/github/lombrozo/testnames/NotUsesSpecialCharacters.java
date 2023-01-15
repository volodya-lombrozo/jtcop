package com.github.lombrozo.testnames;

public class NotUsesSpecialCharacters implements Rule {
    private final TestCase test;

    public NotUsesSpecialCharacters(final TestCase test) {
        this.test = test;
    }

    @Override
    public void validate() throws WrongTestName {
        if (usesSpecialCharacters()) {
            throw new WrongTestName(test, "test name shouldn't contain special characters "
                + "like '$' or '_'");
        }
    }

    private boolean usesSpecialCharacters() {
        return test.name().contains("$") || test.name().contains("_");
    }
}
