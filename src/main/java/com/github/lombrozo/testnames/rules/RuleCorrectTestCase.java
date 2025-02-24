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
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The rule checks if test case in present simple.
 *
 * @since 0.1.0
 */
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public final class RuleCorrectTestCase implements Rule {

    /**
     * The rules.
     */
    private final Collection<Rule> all;

    /**
     * Ctor.
     *
     * @param test The test case to check
     * @param parameters Parameters to use for the check.
     */
    RuleCorrectTestCase(final TestCase test, final Parameters parameters) {
        this.all = Stream.of(
            new RuleNotCamelCase(test),
            new RuleNotContainsTestWord(test),
            new RuleNotSpam(test),
            new RuleNotUsesSpecialCharacters(test),
            new RulePresentTense(test),
            new RuleAssertionMessage(test),
            new RuleLineHitter(test),
            new RuleTestCaseContainsMockery(test, parameters)
        ).map(rule -> new RuleSuppressed(rule, test)).collect(Collectors.toList());
    }

    @Override
    public Collection<Complaint> complaints() {
        return this.all.stream()
            .map(Rule::complaints)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }
}
