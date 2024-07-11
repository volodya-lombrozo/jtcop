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
import com.github.lombrozo.testnames.javaparser.JavaTestClasses;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link RuleTestCaseContainsMockery}.
 *
 * @since 1.3.4
 */
final class RuleTestCaseContainsMockeryTest {

    @Test
    void complaintsAboutMockery() {
        final Complaint complaint = new ListOf<>(
            new RuleTestCaseContainsMockery(
                new ListOf<>(
                    JavaTestClasses.MOCKERY_TEST.toTestClass().all()
                ).get(0),
                2
            ).complaints()
        ).get(0);
        final String text =
            "'testsSomething' contains excessive number of mocks: 3. max allowed: 2";
        MatcherAssert.assertThat(
            String.format(
                "Complaint received: %s, but message does not contain text '%s'",
                complaint,
                text
            ),
            complaint.message(),
            Matchers.containsString(text)
        );
    }
}
