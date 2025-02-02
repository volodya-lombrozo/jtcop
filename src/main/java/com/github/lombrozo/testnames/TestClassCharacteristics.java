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
package com.github.lombrozo.testnames;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Test class characteristics.
 *
 * @since 0.1.19
 */
public interface TestClassCharacteristics {

    /**
     * Returns true if the test class is a JUnit extension.
     * @return True if the test class is a JUnit extension, false otherwise.
     */
    boolean isJUnitExtension();

    /**
     * Returns true if the test class is an integration test.
     * @return True if the test class is an integration test, false otherwise.
     */
    boolean isIntegrationTest();

    /**
     * The number of tests in the class.
     * @return The number of tests in the class.
     */
    int numberOfTests();

    /**
     * The total number of methods in the class.
     * @return The total number of methods in the class.
     */
    int numberOfMethods();

    /**
     * The parent class name.
     * @return The parent class name.
     */
    String parent();

    /**
     * Fake implementation for test characteristics.
     *
     * @since 0.1.19
     */
    @EqualsAndHashCode
    @ToString
    final class Fake implements TestClassCharacteristics {

        /**
         * The default parent class name.
         */
        private static final String DEFAULT_PARENT = "java.lang.Object";

        /**
         * Is the test class a JUnit extension?
         */
        private final boolean junit;

        /**
         * The number of tests in the class.
         */
        private final int tests;

        /**
         * The total number of methods in the class.
         */
        private final int methods;

        /**
         * Is the test class an Integration test?
         */
        private final boolean integration;

        /**
         * The parent class name.
         */
        private final String parent;

        /**
         * Constructor.
         * @param extension Is the test class a JUnit extension?
         */
        public Fake(final boolean extension) {
            this(extension, 0, 0, false, Fake.DEFAULT_PARENT);
        }

        /**
         * Constructor.
         * @param parent The parent class name.
         */
        public Fake(final String parent) {
            this(false, 0, 0, false, parent);
        }

        /**
         * Constructor.
         * @param ntests The number of tests in the class.
         * @param nmethods The total number of methods in the class.
         */
        public Fake(final int ntests, final int nmethods) {
            this(false, ntests, nmethods, false, Fake.DEFAULT_PARENT);
        }

        /**
         * Constructor.
         * @param extension Is the test class a JUnit extension?
         * @param ntests The number of tests in the class.
         * @param nmethods The total number of methods in the class.
         * @param integration Is the test class an Integration test?
         * @param parent The parent class name.
         * @checkstyle ParameterNumberCheck (10 lines)
         */
        public Fake(
            final boolean extension,
            final int ntests,
            final int nmethods,
            final boolean integration,
            final String parent
        ) {
            this.junit = extension;
            this.tests = ntests;
            this.methods = nmethods;
            this.integration = integration;
            this.parent = parent;
        }

        @Override
        public boolean isJUnitExtension() {
            return this.junit;
        }

        @Override
        public boolean isIntegrationTest() {
            return false;
        }

        @Override
        public int numberOfTests() {
            return this.tests;
        }

        @Override
        public int numberOfMethods() {
            return this.methods;
        }

        @Override
        public String parent() {
            return this.parent;
        }
    }

    /**
     * Fake implementation of integration test characteristics.
     *
     * @since 0.1.20
     */
    @EqualsAndHashCode
    @ToString
    @SuppressWarnings("PMD.TestClassWithoutTestCases")
    final class IntegrationTest implements TestClassCharacteristics {

        @Override
        public boolean isJUnitExtension() {
            return false;
        }

        @Override
        public boolean isIntegrationTest() {
            return true;
        }

        @Override
        public int numberOfTests() {
            return 0;
        }

        @Override
        public int numberOfMethods() {
            return 0;
        }

        @Override
        public String parent() {
            return Fake.DEFAULT_PARENT;
        }
    }
}
