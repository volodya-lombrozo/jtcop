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

package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.complaints.ComplaintLinked;
import com.github.lombrozo.testnames.complaints.ComplaintWrongTestName;
import java.util.Collection;
import java.util.stream.Stream;

/**
 * Test case without a 'test' word in name.
 *
 * @since 0.1.0
 */
public final class RuleNotContainsTestWord implements Rule {

    /**
     * The name of the rule.
     */
    public static final String NAME = "RuleNotContainsTestWord";

    /**
     * The test case.
     */
    private final TestCase test;

    /**
     * Ctor.
     *
     * @param test The test case to check
     */
    RuleNotContainsTestWord(final TestCase test) {
        this.test = test;
    }

    @Override
    public Collection<Complaint> complaints() {
        return new RuleConditional(
            this::containsTest,
            new ComplaintLinked(
                new ComplaintWrongTestName(
                    this.test,
                    "test name doesn't have to contain the word 'test'"
                ).message(),
                "Remove 'test' word from the test name",
                this.getClass(),
                "test-word.md"
            )
        ).complaints();
    }

    /**
     * Is contains the 'test' word.
     *
     * @return The result
     */
    private boolean containsTest() {
        return Stream.of("test", "TEST", "Test").anyMatch(this.test.name()::contains);
    }
}
