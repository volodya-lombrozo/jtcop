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

import com.github.lombrozo.testnames.Assertion;
import com.github.lombrozo.testnames.Field;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.rules.RuleEveryTestHasProductionClass;
import com.github.lombrozo.testnames.rules.RuleNotCamelCase;
import com.github.lombrozo.testnames.rules.RuleNotContainsTestWord;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.cactoos.list.ListOf;
import org.cactoos.set.SetOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link JavaParserTestCase}.
 *
 * @since 0.2
 */
@SuppressWarnings("PMD.TooManyMethods")
final class JavaParserTestCaseTest {

    /**
     * Message for assertion.
     */
    private static final String MESSAGE = "The '%s' assertion has to contain an explanation";

    /**
     * Not found message for assertions.
     */
    private static final String NOT_FOUND = "Assertion not found";

    /**
     * Method not found message.
     */
    private static final String METHOD_NFOUND = "Method %s not found";

    @Test
    void convertsToString() {
        final String name = "name";
        MatcherAssert.assertThat(
            "Test case should be converted to string which contains its name",
            new JavaParserTestCase(name).toString(),
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
            "Test cases with the same name should have the same hash code",
            new JavaParserTestCase(name).hashCode(),
            Matchers.is(new JavaParserTestCase(name).hashCode())
        );
    }

    @Test
    void equalsIfBothTheSame() {
        final String name = "nme";
        MatcherAssert.assertThat(
            "Test cases with the same name should be equal",
            new JavaParserTestCase(name),
            Matchers.equalTo(new JavaParserTestCase(name))
        );
    }

    @Test
    void parsesSuppressedAnnotations() {
        final String test = "cheksTest";
        final Collection<String> suppressed = JavaTestClasses.ONLY_METHODS_SUPPRESSED
            .testCase(test)
            .suppressed();
        final String[] expected = {RuleNotContainsTestWord.NAME, RuleNotCamelCase.NAME};
        final String msg = String.format(
            "The '%s' method has to contain %d suppressed annotations %s, but was %s",
            test, expected.length,
            Arrays.toString(expected),
            suppressed
        );
        MatcherAssert.assertThat(msg, suppressed, Matchers.hasSize(2));
        MatcherAssert.assertThat(msg, suppressed, Matchers.hasItems(expected));
    }

    @Test
    void parsesSingleSuppressedAnnotation() {
        final String test = "checksSingle";
        final Collection<String> suppressed = JavaTestClasses.ONLY_METHODS_SUPPRESSED
            .testCase(test)
            .suppressed();
        final String expected = RuleNotContainsTestWord.NAME;
        final String msg = String.format(
            "The '%s' method has to contain single suppressed annotation '%s', but was %s",
            test,
            expected,
            suppressed
        );
        MatcherAssert.assertThat(msg, suppressed, Matchers.hasSize(1));
        MatcherAssert.assertThat(msg, suppressed, Matchers.hasItems(expected));
    }

    @Test
    void parsesSuppressedAnnotationsForClassAndMethodTogether() {
        final String test = "cheksTest";
        final Collection<String> suppressed = JavaTestClasses.MANY_SUPPRESSED
            .testCase(test)
            .suppressed();
        final String[] expected = {
            RuleEveryTestHasProductionClass.SECOND_NAME,
            RuleNotContainsTestWord.NAME,
            RuleNotCamelCase.NAME,
            "AnotherRule",
            "UnknownRule",
        };
        final String msg = String.format(
            "The '%s' method has to contain %d suppressed annotations %s, but was %s",
            test,
            expected.length,
            Arrays.toString(expected),
            suppressed
        );
        MatcherAssert.assertThat(msg, suppressed, Matchers.hasSize(5));
        MatcherAssert.assertThat(msg, suppressed, Matchers.hasItems(expected));
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    void parsesMethodsAssertionsForJUnit() {
        final JavaParserTestClass parser = JavaTestClasses.TEST_WITH_ASSERTIONS.toTestClass();
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
            String.format(JavaParserTestCaseTest.MESSAGE, assertion),
            assertion.explanation().isPresent(),
            Matchers.is(true)
        );
        MatcherAssert.assertThat(
            String.format("The '%s' assertion message is wrong", assertion),
            assertion.explanation().get(),
            Matchers.equalTo("JUnit explanation")
        );
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    void parsesAssertionInsideLoop() {
        final JavaParserTestClass parser = JavaTestClasses.TEST_WITH_ASSERTIONS.toTestClass();
        final String method = "checksTheCaseFrom353issueWithAssertionInLoop";
        final TestCase tested = parser.all().stream()
            .filter(test -> method.equals(test.name()))
            .findFirst()
            .orElseThrow(
                () -> new AssertionError(
                    String.format(JavaParserTestCaseTest.METHOD_NFOUND, method)
                )
            );
        final Assertion assertion = tested.assertions().stream()
            .findFirst()
            .orElseThrow(() -> new AssertionError(JavaParserTestCaseTest.NOT_FOUND));
        MatcherAssert.assertThat(
            String.format(JavaParserTestCaseTest.MESSAGE, assertion),
            assertion.explanation().isPresent(),
            Matchers.is(true)
        );
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    void parsesAssertionInsideIfStatement() {
        final JavaParserTestClass parser = JavaTestClasses.TEST_WITH_ASSERTIONS.toTestClass();
        final String method = "checksTheCaseFrom357issueWithAssertionInIfStatement";
        final TestCase tested = parser.all().stream()
            .filter(test -> method.equals(test.name()))
            .findFirst()
            .orElseThrow(
                () -> new AssertionError(
                    String.format(JavaParserTestCaseTest.METHOD_NFOUND, method)
                )
            );
        final Assertion assertion = tested.assertions().stream()
            .findFirst()
            .orElseThrow(() -> new AssertionError(JavaParserTestCaseTest.NOT_FOUND));
        MatcherAssert.assertThat(
            String.format("The '%s' assertion has to contain some explanation", assertion),
            assertion.explanation().isPresent(),
            Matchers.is(true)
        );
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    void parsesAssertionInsideClosure() {
        final JavaParserTestClass parser = JavaTestClasses.TEST_WITH_ASSERTIONS.toTestClass();
        final String method = "checksTheCaseFrom357issueWithAssertionInClosure";
        final TestCase tested = parser.all().stream()
            .filter(test -> method.equals(test.name()))
            .findFirst()
            .orElseThrow(
                () -> new AssertionError(
                    String.format(JavaParserTestCaseTest.METHOD_NFOUND, method)
                )
            );
        final Assertion assertion = tested.assertions().stream()
            .findFirst()
            .orElseThrow(() -> new AssertionError(JavaParserTestCaseTest.NOT_FOUND));
        MatcherAssert.assertThat(
            String.format(JavaParserTestCaseTest.MESSAGE, assertion),
            assertion.explanation().isPresent(),
            Matchers.is(true)
        );
    }

    @Test
    @SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
    void parsesAssertionInsideLambdaAsParam() {
        final JavaParserTestClass parser = JavaTestClasses.TEST_WITH_ASSERTIONS.toTestClass();
        final String method = "checksTheCaseFrom357issueWithAssertionAsParam";
        final TestCase tested = parser.all().stream()
            .filter(test -> method.equals(test.name()))
            .findFirst()
            .orElseThrow(
                () -> new AssertionError(
                    String.format(JavaParserTestCaseTest.METHOD_NFOUND, method)
                )
            );
        final Assertion assertion = tested.assertions().stream()
            .findFirst()
            .orElseThrow(() -> new AssertionError(JavaParserTestCaseTest.NOT_FOUND));
        MatcherAssert.assertThat(
            String.format(JavaParserTestCaseTest.MESSAGE, assertion),
            assertion.explanation().isPresent(),
            Matchers.is(true)
        );
    }

    @Test
    void parsesAssertionInsideTryStatement() {
        final JavaParserTestClass parser = JavaTestClasses.TEST_WITH_ASSERTIONS.toTestClass();
        final String method = "checksTheCaseFrom413issueWithAssertionInTryStatement";
        final TestCase tested = parser.all().stream()
            .filter(test -> method.equals(test.name()))
            .findFirst()
            .orElseThrow(
                () -> new AssertionError(
                    String.format(JavaParserTestCaseTest.METHOD_NFOUND, method)
                )
            );
        final Assertion assrtion = tested.assertions().stream()
            .findFirst()
            .orElseThrow(() -> new AssertionError(JavaParserTestCaseTest.NOT_FOUND));
        MatcherAssert.assertThat(
            String.format(JavaParserTestCaseTest.MESSAGE, assrtion),
            assrtion.explanation().isPresent(),
            Matchers.is(true)
        );
    }

    @Test
    void parsesAssertionInsideCatchBlock() {
        final JavaParserTestClass parser = JavaTestClasses.TEST_WITH_ASSERTIONS.toTestClass();
        final String method = "checksTheCaseFrom413issueWithAssertionInCatchBlock";
        final TestCase tested = parser.all().stream()
            .filter(test -> method.equals(test.name()))
            .findFirst()
            .orElseThrow(
                () -> new AssertionError(
                    String.format(JavaParserTestCaseTest.METHOD_NFOUND, method)
                )
            );
        final Assertion assrtion = tested.assertions().stream()
            .findFirst()
            .orElseThrow(() -> new AssertionError(JavaParserTestCaseTest.NOT_FOUND));
        MatcherAssert.assertThat(
            String.format(JavaParserTestCaseTest.MESSAGE, assrtion),
            assrtion.explanation().isPresent(),
            Matchers.is(true)
        );
    }

    @Test
    void parsesPackageJava() {
        final JavaParserTestClass parser = JavaTestClasses.PACKAGE_INFO.toTestClass();
        MatcherAssert.assertThat(
            "Java package has to be parsed, but doesn't have to contain test cases",
            parser.all(),
            Matchers.hasSize(0)
        );
    }

    @Test
    void checksIfJUnitExtensionForEnum() {
        MatcherAssert.assertThat(
            "Java enum has to be parsed, but doesn't have to be a JUnit extension",
            JavaTestClasses.ENUM.toTestClass().characteristics().isJUnitExtension(),
            Matchers.is(false)
        );
    }

    @Test
    void parsesMockery() {
        final JavaParserTestClass parser = JavaTestClasses.MOCKERY_TEST.toTestClass();
        final int tests = parser.characteristics().numberOfTests();
        final int expected = 1;
        MatcherAssert.assertThat(
            String.format(
                "Mockery has to be parsed: %s, but number of tests (%s) doesn't match with expected %s",
                parser.all(),
                tests,
                expected
            ),
            tests,
            new IsEqual<>(expected)
        );
    }

    @Test
    void parsesSuppressedMockery() {
        final JavaParserTestClass parser = JavaTestClasses.MOCKERY_SUPPRESSED.toTestClass();
        final Collection<String> suppressed = parser.suppressed();
        final Set<String> expected = new SetOf<>("RuleTestCaseContainsMockery");
        MatcherAssert.assertThat(
            String.format(
                "Suppressed mockery parsed: %s, but suppression (%s) does not match with expected (%s)",
                parser.all(),
                suppressed,
                expected
            ),
            suppressed,
            new IsEqual<>(expected)
        );
    }

    @Test
    void parsesStatements() {
        final Collection<String> statements = new ListOf<>(
            JavaTestClasses.MOCKERY_TEST.toTestClass().all()
        ).get(0).statements();
        final List<String> expected = new ListOf<>(
            "Mockito.when(Mockito.mock(List.class).get(0)).thenReturn(\"jeff\");",
            "Mockito.when(Mockito.mock(Map.class).get(\"test\")).thenReturn(\"jeff\");",
            "Mockito.when(Mockito.mock(Set.class).add(1)).thenReturn(true);"
        );
        MatcherAssert.assertThat(
            String.format(
                "Parsed statements %s do not match with expected %s",
                statements,
                expected
            ),
            statements,
            new IsEqual<>(expected)
        );
    }

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
}
