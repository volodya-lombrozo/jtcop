package com.github.lombrozo.testnames;

public class NotCamelCase implements Rule {
    final String test;

    public NotCamelCase(final String test) {
        this.test = test;
    }

    @Override
    public void validate() throws WrongTestName {
        if (notCamelCase()) {
            throw new WrongTestName(test, "test has to be written by using Camel Case.");
        }
    }

    private boolean notCamelCase() {
        int stack = 0;
        for (final char c : test.toCharArray()) {
            if (Character.isUpperCase(c) && stack == 0) {
                return true;
            } else if (stack != 0) {
                stack = 0;
            }
            stack++;
        }
        return false;
    }
}
