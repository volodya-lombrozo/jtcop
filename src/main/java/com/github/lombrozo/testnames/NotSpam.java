package com.github.lombrozo.testnames;

public class NotSpam implements Rule {
    private final String test;

    public NotSpam(final String test) {
        this.test = test;
    }

    @Override
    public void validate() throws WrongTestName {
        if (!notSpam()) {
            throw new WrongTestName(test, "test name doesn't "
                + "have to contain duplicated symbols.");
        }
    }

    private boolean notSpam() {
        int stack = 0;
        char prev = '!';
        for (final char c : test.toCharArray()) {
            if (c == prev) {
                stack++;
            } else {
                stack = 0;
                prev = c;
            }
            if (stack > 2) {
                return false;
            }
        }
        return true;
    }
}
