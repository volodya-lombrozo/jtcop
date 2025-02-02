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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import lombok.Data;

/**
 * The test case.
 *
 * @since 0.1.0
 */
public interface TestCase {

    /**
     * The name of test case.
     *
     * @return The name of test case as string.
     */
    String name();

    /**
     * The suppressed rules.
     * @return The suppressed rules.
     */
    Collection<String> suppressed();

    /**
     * The method assertions.
     * @return The list of assertions.
     */
    Collection<Assertion> assertions();

    /**
     * The method statements.
     * @return The list of statements.
     */
    Collection<String> statements();

    /**
     * The fake test case.
     *
     * @since 0.1.0
     */
    @Data
    @SuppressWarnings({"PMD.TestClassWithoutTestCases", "PMD.DataClass"})
    final class Fake implements TestCase {

        /**
         * The fake name.
         */
        private static final String FAKE_NAME = "FakeCase";

        /**
         * The name of test.
         */
        private final String name;

        /**
         * The suppressed rules.
         */
        private final Collection<String> suppressed;

        /**
         * The method assertions.
         */
        private final Collection<Assertion> assertions;

        /**
         * Statements.
         */
        private final Collection<String> statements;

        /**
         * Ctor.
         */
        public Fake() {
            this(Fake.FAKE_NAME);
        }

        /**
         * Primary ctor.
         *
         * @param name The name of test case
         * @checkstyle ParameterNameCheck (6 lines)
         */
        public Fake(final String name) {
            this(name, Collections.emptyList());
        }

        /**
         * Ctor.
         * @param asserts The method assertions.
         */
        public Fake(final Assertion... asserts) {
            this(
                Fake.FAKE_NAME,
                Collections.emptyList(),
                Arrays.asList(asserts),
                Collections.emptyList()
            );
        }

        /**
         * Ctor.
         * @param name The name of test case
         * @param asserts Assertions of test case
         */
        public Fake(final String name, final Assertion... asserts) {
            this(
                name,
                Collections.emptyList(),
                Arrays.asList(asserts),
                Collections.emptyList()
            );
        }

        /**
         * Ctor.
         * @param name The name of test case
         * @param suppressed The suppressed rules
         */
        public Fake(final String name, final Collection<String> suppressed) {
            this(
                name,
                suppressed,
                Collections.emptyList(),
                Collections.emptyList()
            );
        }

        /**
         * Ctor.
         * @param name The name of test case
         * @param suppressed The suppressed rules
         * @param assertions The method assertions
         * @param statements The method statements
         * @checkstyle ParameterNumberCheck (2 lines)
         */
        public Fake(
            final String name,
            final Collection<String> suppressed,
            final Collection<Assertion> assertions,
            final Collection<String> statements
        ) {
            this.name = name;
            this.suppressed = suppressed;
            this.assertions = assertions;
            this.statements = statements;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public Collection<String> suppressed() {
            return Collections.unmodifiableCollection(this.suppressed);
        }

        @Override
        public Collection<Assertion> assertions() {
            return Collections.unmodifiableCollection(this.assertions);
        }

        @Override
        public Collection<String> statements() {
            return Collections.unmodifiableCollection(this.statements);
        }
    }
}
