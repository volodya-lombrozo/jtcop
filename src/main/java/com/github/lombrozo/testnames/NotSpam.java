package com.github.lombrozo.testnames;

public class NotSpam implements Rule {
    private final TestCase test;

    public NotSpam(final TestCase test) {
        this.test = test;
    }

    @Override
    public void validate() throws WrongTestName {
        if (!notSpam()) {
            throw new WrongTestName(test, "test name doesn't "
                + "have to contain duplicated symbols");
        }
    }

    private boolean notSpam() {
        int stack = 0;
        char prev = '!';
        for (final char c : test.name().toCharArray()) {
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
