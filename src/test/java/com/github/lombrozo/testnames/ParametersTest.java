/*
 * MIT License
 *
 * Copyright (c) 2022-2024 Volodya Lombrozo
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
package com.github.lombrozo.testnames;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link Parameters}.
 * @since 1.3
 */
final class ParametersTest {

    @Test
    void retrievesMaxNumberOfMocks() {
        final String key = "maxNumberOfMocks";
        final int expected = 10;
        MatcherAssert.assertThat(
            "Can't retrieve max number of mocks",
            new Parameters(key, expected).intValue(key)
                .orElseThrow(() -> new IllegalStateException("No value found")),
            Matchers.equalTo(expected)
        );
    }

    @Test
    void failsBecauseTheTypeIsDifferent() {
        final String key = "failed";
        MatcherAssert.assertThat(
            "Error message is not correct",
            Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Parameters(key, "10").intValue(key)
            ).getMessage(),
            Matchers.equalTo("'10' should be an integer, but was 'class java.lang.String'")
        );
    }

    @Test
    void findsNoValue() {
        MatcherAssert.assertThat(
            "Value should not be found",
            new Parameters().intValue("notFound").isPresent(),
            Matchers.is(false)
        );
    }
}
