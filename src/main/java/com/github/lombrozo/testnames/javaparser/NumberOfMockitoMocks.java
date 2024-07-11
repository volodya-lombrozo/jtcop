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
import java.util.regex.Pattern;
import org.cactoos.Scalar;

/**
 * Number of Mockito mocks in the test case.
 *
 * @since 1.3.4
 */
public final class NumberOfMockitoMocks implements Scalar<Long> {

    /**
     * Mockito Mock Pattern.
     */
    private static final Pattern MOCK_PATTERN =
        Pattern.compile("^(mock\\(.*?\\);)|(Mockito\\.mock\\(.*?\\);)$");
    /**
     * Test case.
     */
    private final TestCase test;

    /**
     * Ctor.
     * @param tst Test case
     */
    public NumberOfMockitoMocks(final TestCase tst) {
        this.test = tst;
    }

    @Override
    public Long value() {
        return this.test.statements().stream()
            .filter(
                statement ->
                    NumberOfMockitoMocks.MOCK_PATTERN.matcher(statement).find()
            ).count();
    }
}
