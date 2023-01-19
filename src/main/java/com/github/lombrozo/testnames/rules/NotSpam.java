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
import com.github.lombrozo.testnames.Case;
import com.github.lombrozo.testnames.complaints.WrongTestNameComplaint;
import java.util.Collection;

/**
 * Rule to check test case on not spam.
 *
 * @since 0.1.0
 */
public final class NotSpam implements Rule {

    /**
     * The test case.
     */
    private final Case test;

    /**
     * Ctor.
     *
     * @param test The test case
     */
    NotSpam(final Case test) {
        this.test = test;
    }

    @Override
    public Collection<Complaint> complaints() {
        return new ConditionalRule(
            () -> !this.notSpam(),
            new WrongTestNameComplaint(
                this.test,
                "test name doesn't have to contain duplicated symbols"
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
