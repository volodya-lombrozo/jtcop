/*
 * MIT License
 *
 * Copyright (c) 2022-2025 Volodya Lombrozo
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

package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.TestCase;
import java.util.stream.Stream;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test for {@link RuleNotContainsTestWord}.
 * @since 0.2.0
 */
final class RuleNotContainsTestWordTest {

    @ParameterizedTest
    @MethodSource("contains")
    void checksNamesWithoutTestWord(final TestCase test) {
        MatcherAssert.assertThat(
            String.format(
                "We expect that test name contains 'test' word, but %s doesn't contain it",
                test.name()
            ),
            !new RuleNotContainsTestWord(test).complaints().isEmpty()
        );
    }

    @ParameterizedTest
    @MethodSource("absent")
    void checksNamesWithTestWord(final TestCase test) {
        MatcherAssert.assertThat(
            String.format(
                "We expect that test name doesn't contain 'test' word, but %s contains it",
                test.name()
            ),
            new RuleNotContainsTestWord(test).complaints().isEmpty()
        );
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private static Stream<Arguments> contains() {
        return Stream.of("test", "TEST", "Test", "IntegrationTestIT", "AnotherTestIT")
            .map(TestCase.Fake::new)
            .map(Arguments::of);
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private static Stream<Arguments> absent() {
        return Stream.of(
                "tesT", "teSt", "tSst",
                "tESt", "tEsT", "tEST", "executesTo", "createsTo", "executesTO",
                "IntegrationIT", "ServiceIT", "RepositoryIT"
            )
            .map(TestCase.Fake::new)
            .map(Arguments::of);
    }

}
