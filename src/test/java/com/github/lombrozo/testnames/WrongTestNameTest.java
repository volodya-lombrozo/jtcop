package com.github.lombrozo.testnames;

import java.util.UUID;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class WrongTestNameTest {

    @Test
    void createsUsingSingleParamConstructor() {
        final String name = UUID.randomUUID().toString();
        MatcherAssert.assertThat(
            new WrongTestName(name).getMessage(),
            Matchers.equalTo(String.format("Test name '%s' doesn't follow naming rules", name))
        );
    }

    @Test
    void createsUsingNameAndReason() {
        final String name = UUID.randomUUID().toString();
        final String reason = UUID.randomUUID().toString();
        MatcherAssert.assertThat(
            new WrongTestName(name, reason).getMessage(),
            Matchers.equalTo(
                String.format(
                    "Test name '%s' doesn't follow naming rules, because %s",
                    name,
                    reason
                )
            )
        );
    }

    @Test
    void createsUsingSeveralExceptions() {
        MatcherAssert.assertThat(
            new WrongTestName(
                new WrongTestName("test1"),
                new WrongTestName("test2")
            ).getMessage(),
            Matchers.allOf(
                Matchers.containsString("test1"),
                Matchers.containsString("test2")
            )
        );
    }


}