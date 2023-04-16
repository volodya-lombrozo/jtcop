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

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.complaints.ComplaintWrongTestName;
import java.util.Collection;

/**
 * The rule checks if test case in present tense.
 *
 * @since 0.1.0
 */
public final class RulePresentTense implements Rule {

    /**
     * The test case.
     */
    private final TestCase test;

    /**
     * Ctor.
     *
     * @param tst The test case to check
     */
    RulePresentTense(final TestCase tst) {
        this.test = tst;
    }

    @Override
    public Collection<Complaint> complaints() {
        return new RuleConditional(
            () -> !this.presentTense(),
            new ComplaintWrongTestName(
                this.test,
                "the test name has to be written using present tense"
            )
        ).complaints();
    }

    /**
     * Is test case name in present tense.
     *
     * @return The result
     * @checkstyle ReturnCountCheck (20 lines)
     */
    @SuppressWarnings("PMD.OnlyOneReturn")
    private boolean presentTense() {
        final char[] chars = this.test.name().toCharArray();
        char prev = '!';
        for (final char chr : chars) {
            if (Character.isUpperCase(chr)) {
                return prev == 's';
            } else {
                prev = chr;
            }
        }
        return prev == 's';
    }
}
