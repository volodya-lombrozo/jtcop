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

import java.nio.file.Path;
import java.util.Collection;
import org.cactoos.io.InputStreamOf;
import org.cactoos.io.ResourceOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test case for {@link TestClassJavaParser}.
 *
 * @since 0.1.0
 */
final class TestClassJavaParserTest {

    @Test
    void getsNames(@TempDir final Path temp) throws Exception {
        MatcherAssert.assertThat(
            new TestClassJavaParser(temp, new ResourceOf("TestSimple.java").stream()).all(),
            Matchers.containsInAnyOrder(
                new TestCaseJavaParser(
                    "creates",
                    temp
                ), new TestCaseJavaParser(
                    "removes",
                    temp
                ),
                new TestCaseJavaParser(
                    "updates",
                    temp
                )
            )
        );
    }

    @Test
    void returnsEmptyListIfItDoesNotHaveAnyCases(@TempDir final Path temp) throws Exception {
        MatcherAssert.assertThat(
            new TestClassJavaParser(temp, new ResourceOf("TestWithoutTests.java").stream()).all(),
            Matchers.empty()
        );
    }

    @Test
    void getsNamesFromParameterizedCase(@TempDir final Path temp) throws Exception {
        MatcherAssert.assertThat(
            new TestClassJavaParser(
                temp,
                new ResourceOf("TestParameterized.java").stream()
            ).all(),
            Matchers.containsInAnyOrder(
                new TestCaseJavaParser(
                    "checksCases",
                    temp
                )
            )
        );
    }

    @Test
    void throwsExceptionIfFileNotFound(@TempDir final Path temp) {
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new TestClassJavaParser(temp.resolve("TestNotFound.java")).all()
        );
    }

    @Test
    void throwsExceptionIfFileIsNotJava(@TempDir final Path temp) {
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new TestClassJavaParser(temp, new InputStreamOf("Not Java")).all()
        );
    }

    @Test
    void returnsEmptyListOfSuppressedRules(@TempDir final Path path) throws Exception {
        MatcherAssert.assertThat(
            new TestClassJavaParser(
                path,
                new ResourceOf("TestSimple.java").stream()
            ).suppressed(),
            Matchers.empty()
        );
    }

    @Test
    void returnsNonEmptyListOfSuppressedRules(@TempDir final Path path) throws Exception {
        final Collection<String> all = new TestClassJavaParser(
            path,
            new ResourceOf("TestWithSuppressed.java").stream()
        ).suppressed();
        MatcherAssert.assertThat(all, Matchers.hasSize(1));
        MatcherAssert.assertThat(
            all.iterator().next(),
            Matchers.equalTo("RuleAllTestsHaveProductionClass")
        );
    }

    @Test
    void parsesWithLotsOfSuppressingRules(@TempDir final Path path) throws Exception {
        final Collection<String> all = new TestClassJavaParser(
            path,
            new ResourceOf("TestWithLotsOfSuppressed.java").stream()
        ).suppressed();
        MatcherAssert.assertThat(all, Matchers.hasSize(3));
        MatcherAssert.assertThat(
            all,
            Matchers.hasItems(
                "RuleAllTestsHaveProductionClass",
                "RuleNotCamelCase",
                "RuleNotContainsTestWord"
            )
        );
    }

    @Test
    void parsesAnnotationWithSuppressedRules(@TempDir final Path path) throws Exception {
        final Collection<String> all = new TestClassJavaParser(
            path,
            new ResourceOf("SuppressedAnnotation.java").stream()
        ).suppressed();
        MatcherAssert.assertThat(all, Matchers.hasSize(1));
        MatcherAssert.assertThat(all, Matchers.hasItem("RuleAllTestsHaveProductionClass"));
    }

    @Test
    void parsesInterfaceWithSuppressedRules(@TempDir final Path path) throws Exception {
        final Collection<String> all = new TestClassJavaParser(
            path,
            new ResourceOf("SuppressedInterface.java").stream()
        ).suppressed();
        MatcherAssert.assertThat(all, Matchers.hasSize(1));
        MatcherAssert.assertThat(all, Matchers.hasItem("RuleAllTestsHaveProductionClass"));
    }

}
