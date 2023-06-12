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

import com.github.javaparser.ast.expr.MethodCallExpr;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link AssertionOfJavaParser}.
 *
 * @since 0.1.15
 * @todo 164:30min Enable AssertionOfJavaParserTest#extractMessagesFromAllAssertions test.
 *  This test is disabled because it fails. Apparently, we have the bug in the parser of JUnit
 *  assertions. We need to fix it. After that, we should enable this test:
 *  {@link AssertionOfJavaParserTest#extractMessagesFromAllAssertions()}
 */
class AssertionOfJavaParserTest {

    /**
     * Expectation message for the tests that check the number of assertions.
     */
    private static final String EXACTLY_FORM = "Expected exactly %d assertions, but was %d";

    @Test
    void parsesJunitAssertionOnly() {
        final List<AssertionOfJavaParser> all = AssertionOfJavaParserTest.method("junit")
            .map(AssertionOfJavaParser::new)
            .filter(AssertionOfJavaParser::isAssertion)
            .collect(Collectors.toList());
        final int expected = 1;
        MatcherAssert.assertThat(
            String.format(AssertionOfJavaParserTest.EXACTLY_FORM, expected, all.size()),
            all,
            Matchers.hasSize(expected)
        );
    }

    @Test
    void parsesHamcrestAssertionOnly() {
        final List<AssertionOfJavaParser> all = AssertionOfJavaParserTest
            .method("hamcrestAssertion")
            .map(AssertionOfJavaParser::new)
            .filter(AssertionOfJavaParser::isAssertion)
            .collect(Collectors.toList());
        final int expected = 2;
        MatcherAssert.assertThat(
            String.format(AssertionOfJavaParserTest.EXACTLY_FORM, expected, all.size()),
            all,
            Matchers.hasSize(expected)
        );
    }

    @Test
    void parsesSeveralAssertionsFromDifferentLibraries() {
        final List<AssertionOfJavaParser> all = AssertionOfJavaParserTest
            .method("severalFrameworks")
            .map(AssertionOfJavaParser::new)
            .filter(AssertionOfJavaParser::isAssertion)
            .collect(Collectors.toList());
        final int expected = 4;
        MatcherAssert.assertThat(
            String.format(AssertionOfJavaParserTest.EXACTLY_FORM, expected, all.size()),
            all,
            Matchers.hasSize(expected)
        );
    }

    @Test
    @Disabled
    void extractsMessagesFromAllAssertions() {
        final List<AssertionOfJavaParser> all = AssertionOfJavaParserTest
            .method("severalFrameworks")
            .map(AssertionOfJavaParser::new)
            .filter(AssertionOfJavaParser::isAssertion)
            .collect(Collectors.toList());
        MatcherAssert.assertThat(
            String.format(
                "All assertions should have messages, but here are the assertions without messages: %s",
                all.stream()
                    .filter(assertion -> !assertion.explanation().isPresent())
                    .collect(Collectors.toList())
            ),
            all.stream()
                .map(AssertionOfJavaParser::explanation)
                .allMatch(Optional::isPresent),
            Matchers.is(true)
        );
    }

    @Test
    void parsesAssertionsWithoutMessage() {
        final List<AssertionOfJavaParser> all = AssertionOfJavaParserTest
            .method("assertionsWithoutMesssages")
            .map(AssertionOfJavaParser::new)
            .filter(AssertionOfJavaParser::isAssertion)
            .collect(Collectors.toList());
        MatcherAssert.assertThat(
            String.format(
                "We expect, that all assertions won't have messages, but was %s",
                all.stream()
                    .filter(assertion -> assertion.explanation().isPresent())
                    .collect(Collectors.toList())
            ),
            all.stream()
                .map(AssertionOfJavaParser::explanation)
                .noneMatch(Optional::isPresent),
            Matchers.is(true)
        );
    }

    /**
     * Returns all statements of the method with the given name.
     * @param name Name of the method.
     * @return All statements of the method with the given name.
     */
    private static Stream<MethodCallExpr> method(final String name) {
        return JavaTestClasses.TEST_WITH_ASSERTIONS.toJavaParserClass()
            .methods(new ByName(name))
            .findFirst()
            .orElseThrow(() -> new MethodNotFound(name))
            .statements();
    }
}
