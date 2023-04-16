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

package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.TestCase;
import java.util.stream.Stream;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test for {@link RuleNotContainsTestWord}.
 * @since 0.2.0
 */
class RuleNotContainsTestWordTest {

    @ParameterizedTest
    @MethodSource("cases")
    void checksSeveralNames(final TestCase test, final boolean empty) {
        MatcherAssert.assertThat(
            new RuleNotContainsTestWord(test).complaints().isEmpty(),
            Matchers.equalTo(empty)
        );
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private static Stream<Arguments> cases() {
        return Stream.of(
            RuleNotContainsTestWordTest.args("test", false),
            RuleNotContainsTestWordTest.args("TEST", false),
            RuleNotContainsTestWordTest.args("Test", false),
            RuleNotContainsTestWordTest.args("tesT", true),
            RuleNotContainsTestWordTest.args("teSt", true),
            RuleNotContainsTestWordTest.args("tSst", true),
            RuleNotContainsTestWordTest.args("tESt", true),
            RuleNotContainsTestWordTest.args("tEsT", true),
            RuleNotContainsTestWordTest.args("tEST", true),
            RuleNotContainsTestWordTest.args("executesTo", true),
            RuleNotContainsTestWordTest.args("createsTo", true),
            RuleNotContainsTestWordTest.args("executesTO", true)
        );
    }

    private static Arguments args(final String name, final boolean correct) {
        return Arguments.of(new TestCase.Fake(name), correct);
    }
}
