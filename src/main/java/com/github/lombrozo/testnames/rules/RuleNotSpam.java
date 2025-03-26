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
import java.util.Collections;
import java.util.List;

/**
 * Rule to check test case on not spam.
 *
 * @since 0.1.0
 */
public final class RuleNotSpam implements Rule {

    /**
     * The test case.
     */
    private final TestCase test;

    /**
     * Ctor.
     *
     * @param test The test case
     */
    RuleNotSpam(final TestCase test) {
        this.test = test;
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList(this.getClass().getSimpleName());
    }

    @Override
    public Collection<Complaint> complaints() {
        return new RuleConditional(
            () -> !this.notSpam(),
            new ComplaintLinked(
                new ComplaintWrongTestName(
                    this.test,
                    "test name doesn't have to contain duplicated symbols"
                ).message(),
                "Remove duplicated symbols from the test name",
                this.getClass(),
                "not-spam.md"
            )
        ).complaints();
    }

    /**
     * Check symbols duplication in test case name.
     *
     * @return The result
     * @checkstyle ReturnCountCheck (30 lines)
     */
    @SuppressWarnings("PMD.OnlyOneReturn")
    private boolean notSpam() {
        int stack = 0;
        char prev = '!';
        for (final char chr : this.test.name().toCharArray()) {
            if (chr == prev) {
                ++stack;
            } else {
                stack = 0;
                prev = chr;
            }
            if (stack > 2) {
                return false;
            }
        }
        return true;
    }
}
