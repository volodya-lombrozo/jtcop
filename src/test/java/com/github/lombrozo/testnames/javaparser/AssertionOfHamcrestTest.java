/*
 * MIT License
 *
 * Copyright (c) 2022-2023 Volodya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.lombrozo.testnames.javaparser;

import com.github.lombrozo.testnames.Assertion;
import com.github.lombrozo.testnames.TestCase;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link AssertionOfHamcrest}.
 *
 * @since 0.1.15
 */
class AssertionOfHamcrestTest {

    @Test
    void parsesAllHamcrestAssertions() {
        final int expected = 3;
        final Collection<Assertion> all = AssertionOfHamcrestTest.method(
            "withMessages"
        ).assertions();
        MatcherAssert.assertThat(
            String.format(
                "We expect %d assertions in the test case, but was %d",
                expected,
                all.size()
            ),
            all,
            Matchers.hasSize(expected)
        );
    }

    @Test
    void parsesAllHamcrestMessages() {
        final Collection<Assertion> all = AssertionOfHamcrestTest.method(
            "withMessages"
        ).assertions();
        MatcherAssert.assertThat(
            String.format(
                "We expect all assertions to have messages, but was %s",
                all.stream()
                    .map(Assertion::explanation)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList())
            ),
            all.stream()
                .map(Assertion::explanation)
                .allMatch(Optional::isPresent),
            Matchers.is(true)
        );
    }

    @Test
    void parsesAllHamcrestAssertionsWithoutMessages() {
        final int expected = 4;
        final Collection<Assertion> all = AssertionOfHamcrestTest.method(
            "withoutMessages"
        ).assertions();
        MatcherAssert.assertThat(
            String.format(
                "We expect %d assertions in the test case, but was %d",
                expected,
                all.size()
            ),
            all,
            Matchers.hasSize(expected)
        );
    }

    @Test
    void checksIfAllHamcrestAssertionsAreActuallyWithoutMessages() {
        final Collection<Assertion> all = AssertionOfHamcrestTest.method(
            "withoutMessages"
        ).assertions();
        MatcherAssert.assertThat(
            String.format(
                "We expect all assertions to have no messages, but was %s",
                all.stream()
                    .map(Assertion::explanation)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList())
            ),
            all.stream()
                .map(Assertion::explanation)
                .noneMatch(Optional::isPresent),
            Matchers.is(true)
        );
    }

    @Test
    void ignoresJUnitAssertions() {
        final String name = "junitAssertions";
        final List<AssertionOfHamcrest> all = JavaTestClasses.TEST_WITH_HAMCREST_ASSERTIONS
            .toJavaParserClass()
            .methods(new ByName(name))
            .findFirst()
            .orElseThrow(() -> new MethodNotFound(name))
            .statements()
            .map(AssertionOfHamcrest::new)
            .filter(AssertionOfHamcrest::isAssertion)
            .collect(Collectors.toList());
        MatcherAssert.assertThat(
            String.format("We expect empty assertion list, but was %s", all),
            all,
            Matchers.empty()
        );
    }

    /**
     * Returns test case by name.
     * @param name Name of test case.
     * @return Test case.
     * @todo #151:30min The method AssertionOfHamcrestTest#method is duplicated.
     *  The method AssertionOfHamcrestTest#method is duplicated in the class AssertionOfJUnitTest.
     *  It's better to move it to the separate class and use it from both classes.
     *  When it's done, remove this puzzle.
     */
    private static TestCase method(final String name) {
        return JavaTestClasses.TEST_WITH_HAMCREST_ASSERTIONS
            .toTestClass().all().stream()
            .filter(method -> name.equals(method.name()))
            .findFirst()
            .orElseThrow(
                () -> {
                    throw new IllegalStateException(String.format("Method not found: %s", name));
                }
            );
    }

}
