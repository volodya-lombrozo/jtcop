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
import com.github.lombrozo.testnames.complaints.ComplaintWithRule;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * The rule checks if test case contains a "Line Hitter"
 * antipattern.
 * Previousely this class has a name `RuleLineHitter`, but I had to rename it for
 * the following reasons:
 * "Class 'RuleLineHitter' has bad naming, class ends with '-er' suffix"
 * You can read more about this issue
 * <a href="https://github.com/l3r8yJ/oop-cop/issues/105">here</a>
 *
 * @since 1.0.1
 */
public final class LineHitterRule implements Rule {

    /**
     * The test case.
     */
    private final TestCase test;

    /**
     * Primary ctor.
     *
     * @param test The test case to check
     */
    public LineHitterRule(final TestCase test) {
        this.test = test;
    }

    @Override
    public List<String> aliases() {
        return Arrays.asList("LineHitterRule");
    }

    @Override
    public Collection<Complaint> complaints() {
        return new RuleConditional(
            this::containsLineHitter,
            new ComplaintWithRule(
                String.format(
                    "Method '%s' doesn't have any valuable assertion, which is known as \"Line-Hitter\" anti-pattern",
                    this.test.name()
                ),
                this.getClass()
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
