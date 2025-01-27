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

package com.github.lombrozo.testnames.javaparser;

import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.rules.RuleAllTestsHaveProductionClass;
import com.github.lombrozo.testnames.rules.RuleNotCamelCase;
import com.github.lombrozo.testnames.rules.RuleNotContainsTestWord;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.cactoos.io.InputStreamOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test case for {@link JavaParserTestClass}.
 *
 * @since 0.1.0
 */
@SuppressWarnings("PMD.TooManyMethods")
final class JavaParserTestClassTest {

    @Test
    void getsNames() {
        final String[] expected = {"creates", "removes", "updates"};
        final Set<String> all = JavaTestClasses.SIMPLE.toTestClass()
            .all()
            .stream()
            .map(TestCase::name)
            .collect(Collectors.toSet());
        MatcherAssert.assertThat(
            String.format(
                "We expected that test class will contain only three test cases: %s, but was %s",
                Arrays.toString(expected),
                all
            ),
            all,
            Matchers.containsInAnyOrder(expected)
        );
    }

    @Test
    void returnsEmptyListIfItDoesNotHaveAnyCases() {
        final Collection<TestCase> all = JavaTestClasses.WITHOUT_TESTS.toTestClass().all();
        MatcherAssert.assertThat(
            String.format(
                "We expected that test class %s will not contain any test cases, but was %s",
                JavaTestClasses.WITHOUT_TESTS,
                all
            ),
            all,
            Matchers.empty()
        );
    }

    @Test
    void getsNamesFromParameterizedCase() {
        final String cases = "checksCases";
        MatcherAssert.assertThat(
            String.format(
                "We expected that test class %s will contain only one test case %s",
                JavaTestClasses.PARAMETERIZED,
                cases
            ),
            JavaTestClasses.PARAMETERIZED
                .toTestClass()
                .all()
                .stream()
                .map(TestCase::name)
                .collect(Collectors.toSet()),
            Matchers.containsInAnyOrder(cases)
        );
    }

    @Test
    void throwsExceptionIfFileNotFound(@TempDir final Path temp) {
        final Path test = temp.resolve("TestNotFound.java");
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new JavaParserTestClass(test).all(),
            String.format(
                "We expected that exception will be thrown, because file %s not found",
                test
            )
        );
    }

    @Test
    void throwsExceptionIfFileIsNotJava(@TempDir final Path temp) {
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new JavaParserTestClass(
                temp,
                JavaParserProject.resolver(),
                new InputStreamOf("Not Java")
            ).all(),
            String.format(
                "We expected that exception will be thrown, because file %s is not Java",
                temp
            )
        );
    }

    @Test
    void returnsEmptyListOfSuppressedRules() {
        final Collection<String> suppressed = JavaTestClasses.SIMPLE.toTestClass().suppressed();
        MatcherAssert.assertThat(
            String.format(
                "We expected that test class %s will not contain any suppressed rules, but was %s",
                JavaTestClasses.SIMPLE,
                suppressed
            ),
            suppressed,
            Matchers.empty()
        );
    }

    @Test
    void returnsNonEmptyListOfSuppressedRules() {
        final Collection<String> all = JavaTestClasses.SUPPRESSED_CLASS
            .toTestClass()
            .suppressed();
        final String msg = String.format(
            "We expected that test class %s will contain only one suppressed rule %s",
            JavaTestClasses.SUPPRESSED_CLASS,
            RuleAllTestsHaveProductionClass.NAME
        );
        MatcherAssert.assertThat(msg, all, Matchers.hasSize(1));
        MatcherAssert.assertThat(
            msg,
            all.iterator().next(),
            Matchers.equalTo(RuleAllTestsHaveProductionClass.NAME)
        );
    }

    @Test
    void parsesWithLotsOfSuppressingRules() {
        final Collection<String> all = JavaTestClasses.MANY_SUPPRESSED
            .toTestClass()
            .suppressed();
        final String[] expected = {
            RuleAllTestsHaveProductionClass.NAME,
            RuleNotCamelCase.NAME,
            RuleNotContainsTestWord.NAME,
        };
        final String msg = String.format(
            "We expected that test class %s will contain only three suppressed rules: %s, but was %s",
            JavaTestClasses.MANY_SUPPRESSED,
            Arrays.toString(expected),
            all
        );
        MatcherAssert.assertThat(msg, all, Matchers.hasSize(3));
        MatcherAssert.assertThat(msg, all, Matchers.hasItems(expected));
    }

    @Test
    void excludesProjectSuppressedRules() {
        final String custom = "Custom";
        final String project = "Project";
        final String[] expected = {
            custom,
            project,
            RuleAllTestsHaveProductionClass.NAME,
            RuleNotCamelCase.NAME,
            RuleNotContainsTestWord.NAME,
        };
        final Collection<String> all = JavaTestClasses.MANY_SUPPRESSED
            .toTestClass(custom, project)
            .suppressed();
        final String msg = String.format(
            "We expected that test class %s will contain exactly five suppressed rules: %s, but was %s",
            JavaTestClasses.MANY_SUPPRESSED,
            Arrays.toString(expected),
            all
        );
        MatcherAssert.assertThat(msg, all, Matchers.hasSize(5));
        MatcherAssert.assertThat(msg, all, Matchers.hasItems(expected));
    }

    @Test
    void parsesAnnotationWithSuppressedRules() {
        final Collection<String> all = JavaTestClasses.SUPPRESSED_ANNOTATION
            .toTestClass()
            .suppressed();
        final String expected = RuleAllTestsHaveProductionClass.NAME;
        final String msg = String.format("Expected exactly %s rule, but was %s", expected, all);
        MatcherAssert.assertThat(
            msg, all, Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            msg, all, Matchers.hasItem(expected)
        );
    }

    @Test
    void parsesInterfaceWithSuppressedRules() {
        final Collection<String> all = JavaTestClasses.SUPPRESSED_INTERFACE
            .toTestClass()
            .suppressed();
        final String expected = RuleAllTestsHaveProductionClass.NAME;
        final String msg = String.format("Expected only %s rule, but was %s", expected, all);
        MatcherAssert.assertThat(msg, all, Matchers.hasSize(1));
        MatcherAssert.assertThat(msg, all, Matchers.hasItem(expected));
    }

    @Test
    void parsesJavaClassWithTextBlock() {
        MatcherAssert.assertThat(
            "We expected that test class will parsed successfully and contain only one test case",
            JavaTestClasses.JAVA_17_TEST
                .toTestClass()
                .all(),
            Matchers.hasSize(1)
        );
    }
}
