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
package com.github.lombrozo.testnames;

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
     * The number of tests in the class.
     * @return The number of tests in the class.
     */
    int numberOfTests();

    /**
     * The total number of methods in the class.
     * @return The total number of methods in the class.
     */
    int numberOfMethods();

    class Fake implements TestClassCharacteristics {

        private final boolean junit;
        private final int tests;
        private final int methods;

        public Fake(
            final boolean junit,
            final int tests,
            final int methods
        ) {
            this.junit = junit;
            this.tests = tests;
            this.methods = methods;
        }

        @Override
        public boolean isJUnitExtension() {
            return this.junit;
        }

        @Override
        public int numberOfTests() {
            return this.tests;
        }

        @Override
        public int numberOfMethods() {
            return this.methods;
        }
    }

}
