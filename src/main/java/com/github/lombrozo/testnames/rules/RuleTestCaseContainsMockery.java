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
import com.github.lombrozo.testnames.Parameters;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.complaints.ComplaintWithRule;
import com.github.lombrozo.testnames.javaparser.NumberOfMockitoMocks;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Mockery rule.
 *
 * @since 1.3.4
 * @todo #393:45min Add support for detecting mocks from other frameworks.
 *  We should add support for detecting not only Mockito mocks, but mocks from
 *  other frameworks too. We should even try to detect "generic" mocks without
 *  a need to stick to the specific framework or library.
 */
public final class RuleTestCaseContainsMockery implements Rule {

    /**
     * Test case.
     */
    private final TestCase test;

    /**
     * Allowed number of mocks.
     */
    private final int allowed;

    /**
     * Ctor.
     * @param test Test case.
     * @param params Parameters.
     */
    public RuleTestCaseContainsMockery(final TestCase test, final Parameters params) {
        this(test, params.intValue("maxNumberOfMocks").orElse(2));
    }

    /**
     * Ctor.
     *
     * @param tst Test case
     * @param allwd Allowed number of mocks
     */
    public RuleTestCaseContainsMockery(final TestCase tst, final int allwd) {
        this.test = tst;
        this.allowed = allwd;
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList(this.getClass().getSimpleName());
    }

    @Override
    public Collection<Complaint> complaints() {
        final Long mocks = new NumberOfMockitoMocks(this.test).value();
        return new RuleConditional(
            () -> mocks > (long) this.allowed,
            new ComplaintWithRule(
                String.format(
                    "Method '%s' contains excessive number of mocks: %s. max allowed: %s",
                    this.test.name(),
                    mocks,
                    this.allowed
                ),
                this.getClass()
            )
        ).complaints();
    }
}
