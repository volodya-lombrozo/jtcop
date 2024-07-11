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
package com.github.lombrozo.testnames.javaparser;

import com.github.lombrozo.testnames.TestCase;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link NumberOfMockitoMocks}.
 *
 * @since 1.3.4
 */
final class NumberOfMockitoMocksTest {

    @Test
    void returnsNumberOfMocks() throws Exception {
        final TestCase test = new ListOf<>(
            JavaTestClasses.MOCKERY_TEST.toTestClass().all()
        ).get(0);
        final Long mocks = new NumberOfMockitoMocks(test).value();
        final long expected = 3L;
        MatcherAssert.assertThat(
            String.format(
                "Test case parsed: %s, but number of mocks (%s) does not match with expected (%s)",
                test.toString(),
                mocks,
                expected
            ),
            mocks,
            new IsEqual<>(expected)
        );
    }
}
