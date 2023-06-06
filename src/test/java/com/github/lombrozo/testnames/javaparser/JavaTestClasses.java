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

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import org.cactoos.io.ResourceOf;

/**
 * Test classes.
 *
 * @since 0.1.14
 */
@SuppressWarnings("JTCOP.RuleAllTestsHaveProductionClass")
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
     * Test class with many suppressed methods and class-level suppressed annotations.
     */
    MANY_SUPPRESSED("TestWithLotsOfSuppressed.java"),

    /**
     * Test class with assertions.
     */
    TEST_WITH_ASSERTIONS("TestWithAssertions.java"),

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
    WRONG_NAME("TestWrongName.java");

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
     * Creates {@link TestClassJavaParser} for current class.
     * @param suppressed Rules excluded for entire project.
     * @return Concrete test class implementation - {@link TestClassJavaParser}.
     */
    TestClassJavaParser javaParserClass(final String... suppressed) {
        return new TestClassJavaParser(
            Paths.get("."),
            this.inputStream(),
            Arrays.asList(suppressed)
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
}
