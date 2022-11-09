package com.github.lombrozo.testnames;

public class NotUsesSpecialCharacters implements Rule {
    private final String test;

    public NotUsesSpecialCharacters(final String test) {
        this.test = test;
    }

    @Override
    public void validate() throws WrongTestName {
        if (usesSpecialCharacters()) {
            throw new WrongTestName(test, "test name shouldn't contain special characters "
                + "like '$' or '_'.");
        }
    }

    private boolean usesSpecialCharacters() {
        return test.contains("$") || test.contains("_");
    }
}
