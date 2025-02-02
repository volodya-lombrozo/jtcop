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
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.TestClass;
import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link RuleCorrectTestCases}.
 *
 * @since 0.1.0
 */
final class RuleCorrectTestCasesTest {

    @Test
    void validatesAllWithoutExceptions() {
        MatcherAssert.assertThat(
            "All tests in present simple should not have complaints.",
            new RuleCorrectTestCases(
                new TestClass.Fake(
                    new TestCase.Fake("removes", new Assertion.Fake()),
                    new TestCase.Fake("creates", new Assertion.Fake())
                )
            ).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void validatesAllWithExceptions() {
        MatcherAssert.assertThat(
            "Test class which tests are not in present simple should have one complaint.",
            new RuleCorrectTestCases(
                new TestClass.Fake(
                    new TestCase.Fake("remove", new Assertion.Fake()),
                    new TestCase.Fake("create", new Assertion.Fake())
                )
            ).complaints(),
            Matchers.allOf(Matchers.hasSize(1))
        );
    }

    @Test
    void skipsSomeSuppressedChecksOnCaseLevel() {
        MatcherAssert.assertThat(
            "Should skip suppressed checks on method level.",
            new RuleCorrectTestCases(
                new TestClass.Fake(
                    new TestCase.Fake(
                        "remove",
                        Collections.singletonList("RulePresentTense"),
                        Collections.singletonList(new Assertion.Fake()),
                        Collections.emptyList()
                    ),
                    new TestCase.Fake(
                        "create",
                        Collections.singletonList("RulePresentTense"),
                        Collections.singletonList(new Assertion.Fake()),
                        Collections.emptyList()
                    )
                )
            ).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void skipsSomeSuppressedChecksOnClassLevel() {
        final TestClass.Fake klass = new TestClass.Fake(
            Collections.singletonList("RuleCorrectTestCases"),
            new TestCase.Fake("remove", new Assertion.Fake()),
            new TestCase.Fake("create", new Assertion.Fake())
        );
        MatcherAssert.assertThat(
            "Should skip suppressed checks on class level.",
            new RuleSuppressed(new RuleCorrectTestCases(klass), klass).complaints(),
            Matchers.empty()
        );
    }
}
