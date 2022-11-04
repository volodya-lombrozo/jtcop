package com.github.lombrozo.testnames;

public class WrongTestName extends Exception {
    public WrongTestName(
        final String test
    ) {
        super(String.format("Test name '%s' doesn't follow pattern rules", test));
    }
}
