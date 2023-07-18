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
import com.github.lombrozo.testnames.TestClass;
import java.util.Collection;
import java.util.Collections;

/**
 * Suppressed rule.
 *
 * @since 0.1.14
 */
public final class RuleSuppressed implements Rule {
    /**
     * The delegate.
     */
    private final Rule delegate;

    /**
     * The suppressed rules.
     */
    private final Collection<String> suppressed;

    /**
     * Ctor.
     * @param rule The delegate
     * @param test The test
     */
    public RuleSuppressed(final Rule rule, final TestClass test) {
        this(rule, test.suppressed());
    }

    /**
     * Ctor.
     * @param rule The delegate
     */
    RuleSuppressed(final Rule rule) {
        this(rule, Collections.singleton(rule.getClass().getSimpleName()));
    }

    /**
     * Ctor.
     * @param rule Rule to suppress
     * @param test Test case
     */
    RuleSuppressed(final Rule rule, final TestCase test) {
        this(rule, test.suppressed());
    }

    /**
     * Ctor.
     * @param rule The delegate
     * @param hidden The suppressed rules
     */
    private RuleSuppressed(final Rule rule, final Collection<String> hidden) {
        this.delegate = rule;
        this.suppressed = hidden;
    }

    @Override
    public Collection<Complaint> complaints() {
        final Collection<Complaint> result;
        if (this.isSuppressed()) {
            result = Collections.emptyList();
        } else {
            result = this.delegate.complaints();
        }
        return result;
    }

    /**
     * Check if suppressed.
     * @return True if suppressed
     */
    private boolean isSuppressed() {
        return this.suppressed.stream().anyMatch(
            hidden -> hidden.equals(this.delegate.getClass().getSimpleName())
        );
    }
}
