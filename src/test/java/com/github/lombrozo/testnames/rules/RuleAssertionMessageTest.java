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

import com.github.lombrozo.testnames.Assertion;
import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.TestCase;
import java.util.Collection;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * The test case for {@link RuleAssertionMessage}.
 *
 * @since 0.1.15
 */
class RuleAssertionMessageTest {

    @Test
    void checksAssertionMessageWithoutComplaints() {
        MatcherAssert.assertThat(
            "Fake assertion message should not have complaints.",
            new RuleAssertionMessage(new TestCase.Fake(new Assertion.Fake())).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void checksAssertionMessageOfCaseWithoutAssertions() {
        final Collection<Complaint> complaints = new RuleAssertionMessage(
            new TestCase.Fake()
        ).complaints();
        MatcherAssert.assertThat(
            "Case without assertions should have one complaint.",
            complaints,
            Matchers.hasSize(1)
        );
        final Complaint next = complaints.iterator().next();
        MatcherAssert.assertThat(
            "Complaint should have message about missing assertion.",
            next.message(),
            Matchers.containsString("doesn't have assertion statements")
        );
    }

    @Test
    void checksAssertionMessageOfCaseWithoutAssertionMessage() {
        final Collection<Complaint> complaints = new RuleAssertionMessage(
            new TestCase.Fake(new Assertion.Empty())
        ).complaints();
        MatcherAssert.assertThat(
            "Case without assertion message should have one complaint.",
            complaints,
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            "Complaint should have message about missing assertion message.",
            complaints.iterator().next().message(),
            Matchers.containsString("has assertion without message")
        );
    }
}
