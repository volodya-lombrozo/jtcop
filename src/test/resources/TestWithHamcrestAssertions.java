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

package com.github.lombrozo.testnames;

import java.util.function.Supplier;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestWithHamcrestAssertions {

    /**
     * Default explanation for assertions.
     */
    private final static String DEFAULT_EXPLANATION = "Hamcrest explanation";

    /**
     * Default supplier for assertions.
     */
    private final static Supplier<String> DEFAULT_SUPPLIER = () -> TestWithJUnitAssertions.DEFAULT_EXPLANATION;

    @Test
    void withMessages() {
        MatcherAssert.assertThat(
            DEFAULT_EXPLANATION,
            "1",
            org.hamcrest.Matchers.equalTo("1")
        );
        MatcherAssert.assertThat(
            DEFAULT_SUPPLIER.get(),
            "1",
            org.hamcrest.Matchers.equalTo("1")
        );
        MatcherAssert.assertThat(
            "Hamcrest explanation",
            true,
            org.hamcrest.Matchers.is(true)
        );
    }

    @Test
    void withoutMessages() {
        MatcherAssert.assertThat("1", org.hamcrest.Matchers.equalTo("1"));
        MatcherAssert.assertThat(1, org.hamcrest.Matchers.equalTo(1));
        MatcherAssert.assertThat(1L, org.hamcrest.Matchers.equalTo(1L));
        MatcherAssert.assertThat(1.0, org.hamcrest.Matchers.is(1.0));
    }

    @Test
    void junitAssertions() {
        Assertions.assertEquals("1", "1", "JUnit explanation");
        Assertions.assertTrue(true, "JUnit explanation");
        Assertions.assertFalse(true, "JUnit explanation");
        Assertions.assertThrows(
            RuntimeException.class,
            () -> {
                throw new RuntimeException("JUnit explanation");
            },
            "JUnit explanation"
        );
    }
}