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

import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.rules.RuleAllTestsHaveProductionClass;
import com.github.lombrozo.testnames.rules.RuleNotCamelCase;
import com.github.lombrozo.testnames.rules.RuleNotContainsTestWord;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;
import org.cactoos.io.InputStreamOf;
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
    void getsNames() {
        MatcherAssert.assertThat(
            JavaTestClasses.SIMPLE.javaParserClass()
                .all()
                .stream()
                .map(TestCase::name)
                .collect(Collectors.toSet()),
            Matchers.containsInAnyOrder(
                "creates", "removes", "updates"
            )
        );
    }

    @Test
    void returnsEmptyListIfItDoesNotHaveAnyCases() {
        MatcherAssert.assertThat(
            JavaTestClasses.WITHOUT_TESTS.javaParserClass().all(),
            Matchers.empty()
        );
    }

    @Test
    void getsNamesFromParameterizedCase() {
        MatcherAssert.assertThat(
            JavaTestClasses.PARAMETERIZED
                .javaParserClass()
                .all()
                .stream()
                .map(TestCase::name)
                .collect(Collectors.toSet()),
            Matchers.containsInAnyOrder("checksCases")
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
    void returnsEmptyListOfSuppressedRules() {
        MatcherAssert.assertThat(
            JavaTestClasses.SIMPLE.javaParserClass().suppressed(),
            Matchers.empty()
        );
    }

    @Test
    void returnsNonEmptyListOfSuppressedRules() {
        final Collection<String> all = JavaTestClasses.SUPPRESSED_CLASS
            .javaParserClass()
            .suppressed();
        MatcherAssert.assertThat(all, Matchers.hasSize(1));
        MatcherAssert.assertThat(
            all.iterator().next(),
            Matchers.equalTo(RuleAllTestsHaveProductionClass.NAME)
        );
    }

    @Test
    void parsesWithLotsOfSuppressingRules() {
        final Collection<String> all = JavaTestClasses.MANY_SUPPRESSED
            .javaParserClass()
            .suppressed();
        MatcherAssert.assertThat(all, Matchers.hasSize(3));
        MatcherAssert.assertThat(
            all,
            Matchers.hasItems(
                RuleAllTestsHaveProductionClass.NAME,
                RuleNotCamelCase.NAME,
                RuleNotContainsTestWord.NAME
            )
        );
    }

    @Test
    void excludesProjectSuppressedRules() {
        final String custom = "Custom";
        final String project = "Project";
        final Collection<String> all = JavaTestClasses.MANY_SUPPRESSED
            .javaParserClass(custom, project)
            .suppressed();
        MatcherAssert.assertThat(all, Matchers.hasSize(5));
        MatcherAssert.assertThat(
            all,
            Matchers.hasItems(
                RuleAllTestsHaveProductionClass.NAME,
                RuleNotCamelCase.NAME,
                RuleNotContainsTestWord.NAME,
                custom,
                project
            )
        );
    }

    @Test
    void parsesAnnotationWithSuppressedRules() {
        final Collection<String> all = JavaTestClasses.SUPPRESSED_ANNOTATION
            .javaParserClass()
            .suppressed();
        MatcherAssert.assertThat(all, Matchers.hasSize(1));
        MatcherAssert.assertThat(all, Matchers.hasItem(RuleAllTestsHaveProductionClass.NAME));
    }

    @Test
    void parsesInterfaceWithSuppressedRules() {
        final Collection<String> all = JavaTestClasses.SUPPRESSED_INTERFACE
            .javaParserClass()
            .suppressed();
        MatcherAssert.assertThat(all, Matchers.hasSize(1));
        MatcherAssert.assertThat(all, Matchers.hasItem(RuleAllTestsHaveProductionClass.NAME));
    }
}
