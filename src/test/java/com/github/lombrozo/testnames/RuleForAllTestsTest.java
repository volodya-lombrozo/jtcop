package com.github.lombrozo.testnames;

import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RuleForAllTestsTest {

    @Test
    void validatesAllWithoutExceptions() {
        try {
            new RuleForAllTests(Cases.correct()).validate();
        } catch (final WrongTestName ex) {
            Assertions.fail(ex);
        }
    }

    @Test
    void validatesAllWithExceptions() {
        Assertions.assertThrows(
            WrongTestName.class,
            () -> new RuleForAllTests(Cases.wrong()).validate()
        );
    }
}