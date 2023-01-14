package com.github.lombrozo.testnames;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public final class WrongTestName extends Exception {
    public WrongTestName(final String test) {
        super(String.format("Test name '%s' doesn't follow naming rules", test));
    }

    public WrongTestName(final String test, final String explanation) {
        super(
            String.format(
                "Test name '%s' doesn't follow naming rules, because %s",
                test,
                explanation
            )
        );
    }

    public WrongTestName(final WrongTestName... exceptions) {
        this(Arrays.asList(exceptions));
    }

    public WrongTestName(Collection<WrongTestName> all) {
        super(
            all.stream()
                .map(Throwable::getMessage)
                .collect(Collectors.joining("\n", "\n", ""))
        );
    }
}
