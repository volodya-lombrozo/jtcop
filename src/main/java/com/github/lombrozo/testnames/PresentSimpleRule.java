package com.github.lombrozo.testnames;

public final class PresentSimpleRule implements Rule {

    private final String test;

    public PresentSimpleRule(final String test) {
        this.test = test;
    }

    @Override
    public void validate() throws WrongTestName {
        if (!valid()) {
            throw new WrongTestName(test);
        }
    }

     boolean valid() {
        return notContainsTest()
            && notCamelCase()
            && notUsesSpecialCharacters()
            && notSpam()
            && presentTense();
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

    private boolean notContainsTest() {
        return !test.matches(".*[Tt][Ee][Ss][Tt].*");
    }

    private boolean notUsesSpecialCharacters() {
        return !test.contains("$") && !test.contains("_");
    }

    private boolean notCamelCase() {
        int stack = 0;
        for (final char c : test.toCharArray()) {
            if (Character.isUpperCase(c) && stack == 0) {
                return false;
            } else if (stack != 0) {
                stack = 0;
            }
            stack++;
        }
        return true;
    }

    private boolean presentTense() {
        final char[] chars = test.toCharArray();
        char prev = '!';
        for (final char c : chars) {
            if (Character.isUpperCase(c)) {
                return prev == 's';
            } else {
                prev = c;
            }
        }
        return prev == 's';
    }
}
