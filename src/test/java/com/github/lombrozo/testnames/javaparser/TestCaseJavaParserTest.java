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

import com.github.lombrozo.testnames.Assertion;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.rules.RuleAllTestsHaveProductionClass;
import com.github.lombrozo.testnames.rules.RuleNotCamelCase;
import com.github.lombrozo.testnames.rules.RuleNotContainsTestWord;
import java.util.Collection;
import java.util.Optional;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TestCaseJavaParser}.
 *
 * @since 0.2
 */
class TestCaseJavaParserTest {

    @Test
    void convertsToString() {
        final String name = "name";
        MatcherAssert.assertThat(
            new TestCaseJavaParser(name).toString(),
            Matchers.stringContainsInOrder(
                String.format(
                    "method=var %s()",
                    name
                )
            )
        );
    }

    @Test
    void hasTheSameHashCode() {
        final String name = "nm";
        MatcherAssert.assertThat(
            new TestCaseJavaParser(name).hashCode(),
            Matchers.is(new TestCaseJavaParser(name).hashCode())
        );
    }

    @Test
    void equalsIfBothTheSame() {
        final String name = "nme";
        MatcherAssert.assertThat(
            new TestCaseJavaParser(name),
            Matchers.equalTo(new TestCaseJavaParser(name))
        );
    }

    @Test
    void parsesSuppressedAnnotations() {
        final Collection<String> suppressed = JavaTestClasses.ONLY_METHODS_SUPPRESSED
            .testCase("cheksTest")
            .suppressed();
        MatcherAssert.assertThat(suppressed, Matchers.hasSize(2));
        MatcherAssert.assertThat(
            suppressed,
            Matchers.hasItems(RuleNotContainsTestWord.NAME, RuleNotCamelCase.NAME)
        );
    }

    @Test
    void parsesSingleSuppressedAnnotation() {
        final Collection<String> suppressed = JavaTestClasses.ONLY_METHODS_SUPPRESSED
            .testCase("checksSingle")
            .suppressed();
        MatcherAssert.assertThat(suppressed, Matchers.hasSize(1));
        MatcherAssert.assertThat(
            suppressed,
            Matchers.hasItems(RuleNotContainsTestWord.NAME)
        );
    }

    @Test
    void parsesSuppressedAnnotationsForClassAndMethodTogether() {
        final Collection<String> suppressed = JavaTestClasses.MANY_SUPPRESSED
            .testCase("cheksTest")
            .suppressed();
        MatcherAssert.assertThat(suppressed, Matchers.hasSize(5));
        MatcherAssert.assertThat(
            suppressed,
            Matchers.hasItems(
                RuleAllTestsHaveProductionClass.NAME,
                RuleNotContainsTestWord.NAME,
                RuleNotCamelCase.NAME,
                "AnotherRule",
                "UnknownRule"
            )
        );
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    void parsesMethodsAssertionsForJUnit() {
        final TestClassJavaParser parser = JavaTestClasses.TEST_WITH_ASSERTIONS.toTestClass();
        final String method = "junit";
        final Optional<TestCase> tested = parser.all().stream()
            .filter(test -> method.equals(test.name()))
            .findFirst();
        MatcherAssert.assertThat(
            String.format("Java class has to contain '%s' method", method),
            tested.isPresent(),
            Matchers.is(true)
        );
        final Collection<Assertion> assertions = tested.get().assertions();
        MatcherAssert.assertThat(
            String.format("The '%s' method has to contain at least one assertion", method),
            assertions,
            Matchers.hasSize(1)
        );
        final Assertion assertion = assertions.iterator().next();
        MatcherAssert.assertThat(
            String.format("The '%s' assertion has to contain an explanation", assertion),
            assertion.explanation().isPresent(),
            Matchers.is(true)
        );
        MatcherAssert.assertThat(
            String.format("The '%s' assertion message is wrong", assertion),
            assertion.explanation().get(),
            Matchers.equalTo("JUnit explanation")
        );
    }
}
