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
package com.github.lombrozo.testnames.complaints;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.TestClass;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link ComplaintClass}.
 * @since 0.2
 */
class ComplaintClassTest {

    @Test
    void returnsSimpleMessageIfDoesNotHaveComplaints() {
        final String expected = "The test class FakeClassTest (FakeClassTest:) has encountered some problems. Please review the results for more information.";
        MatcherAssert.assertThat(
            String.format("ComplaintClass returns wrong message, expected: %s", expected),
            new ComplaintClass(new TestClass.Fake()).message(),
            Matchers.equalTo(expected)
        );
    }

    @Test
    void returnsCompoundMessageIfHasSeveralComplaints() {
        final String expected = "The test class FakeClassTest (FakeClassTest:) has encountered some problems. Please review the results for more information.\n\t1) haha\n\t2) haha";
        MatcherAssert.assertThat(
            String.format(
                "ComplaintClass returns wrong message for several complaints, expected: %s",
                expected
            ),
            new ComplaintClass(
                new TestClass.Fake(),
                new Complaint.Text("haha"),
                new Complaint.Text("haha")
            ).message(),
            Matchers.equalTo(expected)
        );
    }
}
