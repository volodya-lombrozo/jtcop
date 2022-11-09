package com.github.lombrozo.testnames;

import java.util.Collection;
import java.util.stream.Collectors;

public class WrongTestName extends Exception {
    public WrongTestName(
        final String test
    ) {
        super(String.format("Test name '%s' doesn't follow pattern rules", test));
    }

    public WrongTestName(Collection<WrongTestName> all) {
        super(all.stream().map(Throwable::getMessage)
            .collect(Collectors.joining("\n"))
        );
    }
}
