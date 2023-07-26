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

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link CharacteristicsJavaParser}.
 *
 * @since 0.1.19
 */
final class CharacteristicsJavaParserTest {

    @Test
    void createsCharacteristics() {
        MatcherAssert.assertThat(
            "Characteristics should be created successfully",
            JavaTestClasses.SIMPLE.toTestClass().characteristics(),
            Matchers.notNullValue()
        );
    }

    @Test
    void checksIfNotJUnitExtension() {
        MatcherAssert.assertThat(
            "We expect that simple test is not a JUnit extension or condition",
            JavaTestClasses.SIMPLE
                .toTestClass()
                .characteristics()
                .isJUnitExtension(),
            Matchers.is(false)
        );
    }

    @Test
    void checksIfJUnitExtension() {
        MatcherAssert.assertThat(
            "We expect that JUnit condition is a JUnit extension",
            JavaTestClasses.JUNIT_CONDITION
                .toTestClass()
                .characteristics()
                .isJUnitExtension(),
            Matchers.is(true)
        );
    }

    @Test
    void retrievesNumberOfTests() {
        final int expected = 3;
        MatcherAssert.assertThat(
            String.format(
                "We expect that a simple test class contains exactly %d tests",
                expected
            ),
            JavaTestClasses.SIMPLE
                .toTestClass()
                .characteristics()
                .numberOfTests(),
            Matchers.is(expected)
        );
    }

    @Test
    void retrievesTotalNumberOfMethods() {
        final int expected = 3;
        MatcherAssert.assertThat(
            String.format(
                "We expect that a simple test class contains exactly %d methods",
                expected
            ),
            JavaTestClasses.SIMPLE
                .toTestClass()
                .characteristics()
                .numberOfTests(),
            Matchers.is(expected)
        );
    }
}
