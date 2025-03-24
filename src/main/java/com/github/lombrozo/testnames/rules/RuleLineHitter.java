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

import com.github.lombrozo.testnames.Assertion;
import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.complaints.ComplaintLinked;
import java.util.Collection;

/**
 * The rule checks if test case contains a "Line Hitter"
 * antipattern.
 *
 *  @since 1.0.1
 */
@SuppressWarnings("OOP.ErSuffixCheck")
public final class RuleLineHitter implements Rule {

    /**
     * The test case.
     */
    private final TestCase test;

    /**
     * Primary ctor.
     *
     * @param test The test case to check
     */
    public RuleLineHitter(final TestCase test) {
        this.test = test;
    }

    @Override
    public Collection<Complaint> complaints() {
        return new RuleConditional(
            this::containsLineHitter,
            new ComplaintLinked(
                String.format(
                    "Method '%s' contains line hitter anti-pattern",
                    this.test.name()
                ),
                "Write valuable assertion for this test",
                this.getClass(),
                "line-hitter.md"
            )
        ).complaints();
    }

    /**
     * Just checks if collections contain line hitter.
     *
     * @return True if contains line hitter
     */
    private Boolean containsLineHitter() {
        return this.test.assertions()
            .stream()
            .anyMatch(Assertion::isLineHitter);
    }
}
