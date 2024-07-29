/*
 * MIT License
 *
 * Copyright (c) 2022-2024 Volodya Lombrozo
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
import com.github.lombrozo.testnames.TestClass;
import com.github.lombrozo.testnames.complaints.ComplaintClass;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The rule to check all tests on {@link RuleCorrectTestCase}.
 *
 * @since 0.1.0
 */
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public final class RuleCorrectTestCases implements Rule {

    /**
     * All test cases.
     */
    private final TestClass tests;

    /**
     * Parameters to use for the check.
     */
    private final Parameters parameters;

    /**
     * Ctor.
     * @param tests The test cases to check.
     */
    public RuleCorrectTestCases(final TestClass tests) {
        this(tests, new Parameters());
    }

    /**
     * Ctor.
     *
     * @param cases The cases to check
     * @param parameters The parameters to use for the check.
     */
    public RuleCorrectTestCases(final TestClass cases, final Parameters parameters) {
        this.tests = cases;
        this.parameters = parameters;
    }

    @Override
    public Collection<Complaint> complaints() {
        final List<Complaint> list = this.tests.all().stream()
            .map(
                test ->
                    new RuleSuppressed(
                        new RuleCorrectTestCase(test, this.parameters), test
                    )
            ).map(Rule::complaints)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        final Collection<Complaint> result;
        if (list.isEmpty()) {
            result = Collections.emptyList();
        } else {
            result = Collections.singleton(new ComplaintClass(this.tests, list));
        }
        return result;
    }
}
