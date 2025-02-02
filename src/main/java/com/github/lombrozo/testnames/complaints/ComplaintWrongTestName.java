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
package com.github.lombrozo.testnames.complaints;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.TestCase;
import lombok.ToString;

/**
 * When test name is wrong.
 *
 * @since 0.2
 */
@ToString
public final class ComplaintWrongTestName implements Complaint {

    /**
     * The test case.
     */
    private final TestCase test;

    /**
     * The complaint message.
     */
    private final String explanation;

    /**
     * Ctor.
     * @param tcase The tcase case
     * @param expl The explanation of the complaint
     */
    public ComplaintWrongTestName(
        final TestCase tcase,
        final String expl
    ) {
        this.test = tcase;
        this.explanation = expl;
    }

    @Override
    public String message() {
        return String.format(
            "Test name '%s' doesn't follow naming rules, because %s",
            this.test.name(),
            this.explanation
        );
    }
}
