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
package com.github.lombrozo.testnames.javaparser;

import com.github.lombrozo.testnames.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link JavaParserField}.
 * @since 1.4
 */
final class JavaParserFieldTest {

    @Test
    void parsesStaticFields() {
        MatcherAssert.assertThat(
            "Parsed static fields do not match with expected",
            new ListOf<>(
                JavaTestClasses.TEST_WITH_JUNIT_ASSERTIONS.toTestClass().fields()
            ).stream().filter(Field::isStatic).map(Field::name).collect(Collectors.toList()),
            Matchers.allOf(
                Matchers.contains("DEFAULT_EXPLANATION", "DEFAULT_SUPPLIER"),
                Matchers.not(Matchers.contains("number"))
            )
        );
    }

    @Test
    void parsesInstanceFields() {
        MatcherAssert.assertThat(
            "Parsed instance fields do not match with expected",
            new ListOf<>(
                JavaTestClasses.TEST_WITH_JUNIT_ASSERTIONS.toTestClass().fields()
            ).stream().filter(f -> !f.isStatic()).map(Field::name).collect(Collectors.toList()),
            Matchers.allOf(
                Matchers.contains("number"),
                Matchers.not(Matchers.contains("DEFAULT_EXPLANATION", "DEFAULT_SUPPLIER"))
            )
        );
    }

    @Test
    void parsesSuppressedWarningsAnnotations() {
        final List<String> suppressed = new ListOf<>(
            JavaTestClasses.TEST_WITH_JUNIT_ASSERTIONS.toTestClass().fields()
        ).stream().map(Field::suppressed).flatMap(Collection::stream).collect(Collectors.toList());
        MatcherAssert.assertThat(
            String.format(
                "Parsed suppressed warnings annotations do not match with expected, found: %s",
                suppressed
            ),
            suppressed,
            Matchers.containsInAnyOrder("Monkey", "Donkey", "Elephant", "Crocodile")
        );
    }
}
