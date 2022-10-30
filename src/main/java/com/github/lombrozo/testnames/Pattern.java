package com.github.lombrozo.testnames;

public class Pattern {

    private final String name;

    public Pattern(final String name) {
        this.name = name;
    }

    public boolean valid() {
        return notContainsTest()
            && notCamelCase()
            && notUsesSpecialCharacters()
            && notSpam()
            && presentTense();
    }

    private boolean notSpam() {
        int stack = 0;
        char prev = '!';
        for (final char c : name.toCharArray()) {
            if(c == prev){
                stack++;
            } else {
                stack = 0;
                prev = c;
            }
            if(stack > 2){
                return false;
            }
        }
        return true;
    }

    private boolean notContainsTest() {
        return !name.matches(".*[Tt][Ee][Ss][Tt].*");
    }

    private boolean notUsesSpecialCharacters() {
        return !name.contains("$") && !name.contains("_");
    }

    private boolean notCamelCase() {
        int stack = 0;
        for (final char c : name.toCharArray()) {
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
        final char[] chars = name.toCharArray();
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
