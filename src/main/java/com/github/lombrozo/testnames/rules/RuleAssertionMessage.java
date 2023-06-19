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
package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.Assertion;
import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.TestCase;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * The rule that checks that test method has assertion and the assertion message is not empty.
 *
 * @since 0.1.15
 */
class RuleAssertionMessage implements Rule {

    /**
     * The test case.
     */
    private final TestCase method;

    /**
     * Ctor.
     * @param test The test case.
     */
    RuleAssertionMessage(final TestCase test) {
        this.method = test;
    }

    @Override
    public Collection<Complaint> complaints() {
        final Collection<Assertion> assertions = this.method.assertions();
        final Collection<Complaint> res = new ArrayList<>(0);
        if (assertions.isEmpty()) {
            res.add(new EmptyAssertions(this.method));
        }
        assertions.stream()
            .filter(assertion -> !assertion.explanation().isPresent())
            .map(assertion -> new EmptyAssertionMessage(this.method, assertion))
            .forEach(res::add);
        return Collections.unmodifiableCollection(res);
    }

    /**
     * The complaint about empty assertions.
     * @since 0.1.15
     */
    private static final class EmptyAssertions implements Complaint {

        /**
         * The test case.
         */
        private final TestCase method;

        /**
         * Ctor.
         * @param test The test case.
         */
        EmptyAssertions(final TestCase test) {
            this.method = test;
        }

        @Override
        public String message() {
            return String.format("Method %s doesn't have assertion statements", this.method.name());
        }
    }

    /**
     * The complaint about empty assertion message.
     *
     * @since 0.1.15
     */
    private static final class EmptyAssertionMessage implements Complaint {

        /**
         * The test case.
         */
        private final TestCase method;

        /**
         * The assertion.
         */
        private final Assertion assertion;

        /**
         * Ctor.
         * @param test The test case.
         * @param check The assertion.
         */
        EmptyAssertionMessage(final TestCase test, final Assertion check) {
            this.method = test;
            this.assertion = check;
        }

        @Override
        public String message() {
            return String.format(
                "Method '%s' has assertion without message: '%s', please add the explanation message to make the test more readable",
                this.method.name(),
                this.assertion
            );
        }
    }

}
