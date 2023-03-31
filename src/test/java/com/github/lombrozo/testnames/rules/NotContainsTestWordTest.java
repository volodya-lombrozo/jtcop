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
 * Test for {@link NotContainsTestWord}.
 * @since 0.2.0
 */
class NotContainsTestWordTest {

    @ParameterizedTest
    @MethodSource("cases")
    void checksSeveralNames(final TestCase test, final boolean empty) {
        MatcherAssert.assertThat(
            new NotContainsTestWord(test).complaints().isEmpty(),
            Matchers.equalTo(empty)
        );
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private static Stream<Arguments> cases() {
        return Stream.of(
            NotContainsTestWordTest.args("test", false),
            NotContainsTestWordTest.args("TEST", false),
            NotContainsTestWordTest.args("Test", false),
            NotContainsTestWordTest.args("tesT", true),
            NotContainsTestWordTest.args("teSt", true),
            NotContainsTestWordTest.args("tSst", true),
            NotContainsTestWordTest.args("tESt", true),
            NotContainsTestWordTest.args("tEsT", true),
            NotContainsTestWordTest.args("tEST", true),
            NotContainsTestWordTest.args("executesTo", true),
            NotContainsTestWordTest.args("createsTo", true),
            NotContainsTestWordTest.args("executesTO", true)
        );
    }

    private static Arguments args(final String name, final boolean correct) {
        return Arguments.of(new TestCase.FakeCase(name), correct);
    }
}
