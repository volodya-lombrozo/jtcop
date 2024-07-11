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
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import org.cactoos.io.ResourceOf;

/**
 * Test classes.
 *
 * @since 0.1.14
 */
@SuppressWarnings({"JTCOP.RuleAllTestsHaveProductionClass", "JTCOP.RuleCorrectTestName"})
enum JavaTestClasses {

    /**
     * Test class with only suppressed methods.
     */
    ONLY_METHODS_SUPPRESSED("TestOnlyMethodsSuppressed.java"),

    /**
     * Test class without tests.
     */
    WITHOUT_TESTS("TestWithoutTests.java"),

    /**
     * Test class with parameterized tests.
     */
    PARAMETERIZED("TestParameterized.java"),

    /**
     * Test class with simple correct tests.
     */
    SIMPLE("TestSimple.java"),

    /**
     * Test class with suppressed methods and suppressed class-level annotation.
     */
    SUPPRESSED_CLASS("TestWithSuppressed.java"),

    /**
     * Java annotation with suppressed annotation.
     */
    SUPPRESSED_ANNOTATION("SuppressedAnnotation.java"),

    /**
     * Java interface with suppressed annotation.
     */
    SUPPRESSED_INTERFACE("SuppressedInterface.java"),

    /**
     * Java class which implements JUnit extension interface.
     */
    JUNIT_CALLBACK("JUnitAfterAllCallback.java"),

    /**
     * Java class which implements JUnit extension interface.
     */
    JUNIT_CONDITION("JUnitCondition.java"),

    /**
     * Test class with many suppressed methods and class-level suppressed annotations.
     */
    MANY_SUPPRESSED("TestWithLotsOfSuppressed.java"),

    /**
     * Test class with assertions.
     */
    TEST_WITH_ASSERTIONS("TestWithAssertions.java"),

    /**
     * Package info class.
     */
    PACKAGE_INFO("package-info.java"),

    /**
     * Annotation.
     */
    ANNOTATION_DECLARATION("AnnotationDeclaration.java"),

    /**
     * Test class with JUnit assertions.
     */
    TEST_WITH_JUNIT_ASSERTIONS("TestWithJUnitAssertions.java"),

    /**
     * Test class with Hamcrest assertions.
     */
    TEST_WITH_HAMCREST_ASSERTIONS("TestWithHamcrestAssertions.java"),

    /**
     * Test class with mistakes in test names.
     */
    WRONG_NAME("TestWrongName.java"),

    /**
     * Enum class.
     */
    ENUM("EnumInTests.java"),

    /**
     * Integration test.
     */
    INTEGRATION_TEST("IntegrationTest.java"),

    /**
     * Assert true line hitter for hamcrest.
     */
    HAMCREST_ASSERT_TRUE_LINE_HITTER("HamcrestAssertTrueHitter.java"),

    /**
     * Java class with java 17 features.
     */
    JAVA_17_TEST("Java17Test.java"),

    /**
     * Assert true line hitter for junit.
     */
    JUNIT_ASSERT_TRUE_LINE_HITTER("JunitAssertTrueHitter.java"),

    /**
     * Test case with mockery.
     */
    MOCKERY_TEST("Mockery.java"),

    /**
     * Test case with suppressed mockery.
     */
    MOCKERY_SUPPRESSED("MockerySuppressed.java");

    /**
     * Java file name in resources.
     */
    private final String file;

    /**
     * Constructor.
     * @param java Java file name.
     */
    JavaTestClasses(final String java) {
        this.file = java;
    }

    /**
     * Creates {@link JavaParserTestClass} for current class.
     * @param suppressed Rules excluded for entire project.
     * @return Concrete test class implementation - {@link JavaParserTestClass}.
     */
    JavaParserTestClass toTestClass(final String... suppressed) {
        return new JavaParserTestClass(
            Paths.get("."),
            this.inputStream(),
            Arrays.asList(suppressed)
        );
    }

    /**
     * Returns method by name.
     * @param name Method name.
     * @return Method.
     */
    JavaParserMethod method(final String name) {
        return new JavaParserClass(this.inputStream())
            .methods(new ByName(name))
            .findFirst()
            .orElseThrow(() -> new MethodNotFound(name));
    }

    /**
     * Returns test case by name.
     * @param name Test case name.
     * @return Test case.
     */
    TestCase testCase(final String name) {
        return this.toTestClass().all()
            .stream()
            .filter(method -> name.equals(method.name()))
            .findFirst()
            .orElseThrow(
                () -> {
                    throw new MethodNotFound(name);
                }
            );
    }

    /**
     * Returns input stream for current class.
     * @return Input stream.
     */
    @SuppressWarnings("PMD.AvoidCatchingGenericException")
    InputStream inputStream() {
        try {
            return new ResourceOf(this.file).stream();
            //@checkstyle IllegalCatchCheck (1 line)
        } catch (final Exception exception) {
            throw new IllegalStateException("Can't read class '%s' from filesystem", exception);
        }
    }

    /**
     * Exception thrown when method not found.
     *
     * @since 0.1.15
     */
    private static final class MethodNotFound extends IllegalStateException {

        /**
         * Ctor.
         * @param name Name of method.
         */
        MethodNotFound(final String name) {
            super(String.format("Method not found: %s", name));
        }
    }
}
