package com.github.lombrozo.testnames;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RuleForAllTestsTest {

    @Test
    void validatesAllWithoutExceptions() {
        try {
            new RuleForAllTests(() -> Arrays.asList("removes", "creates")).validate();
        } catch (final WrongTestName ex) {
            Assertions.fail(ex);
        }
    }

    @Test
    void validatesAllWithExceptions() {
        Assertions.assertThrows(
            WrongTestName.class,
            () -> new RuleForAllTests(() -> Arrays.asList("creates", "test")).validate()
        );
    }
}