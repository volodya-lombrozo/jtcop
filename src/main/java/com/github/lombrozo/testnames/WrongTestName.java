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

package com.github.lombrozo.testnames;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Wrong test case name exception.
 *
 * @since 0.1.0
 */
public final class WrongTestName extends Exception {

    /**
     * Ctor.
     *
     * @param test Test with violation
     */
    public WrongTestName(final TestCase test) {
        super(
            String.format(
                "Test name '%s#%s' doesn't follow naming rules, test path: %s",
                test.className(),
                test.name(),
                test.path()
            )
        );
    }

    /**
     * Ctor.
     *
     * @param test Test with violation
     * @param explanation The explanation
     */
    public WrongTestName(final TestCase test, final String explanation) {
        super(
            String.format(
                "Test name '%s#%s' doesn't follow naming rules, because %s, test path: %s",
                test.className(),
                test.name(),
                explanation,
                test.path()
            )
        );
    }

    /**
     * Ctor.
     *
     * @param exceptions The exceptions
     */
    public WrongTestName(final WrongTestName... exceptions) {
        this(Arrays.asList(exceptions));
    }

    /**
     * Ctor.
     *
     * @param all Other {@link WrongTestName} exceptions
     */
    public WrongTestName(final Collection<WrongTestName> all) {
        super(
            all.stream()
                .map(Throwable::getMessage)
                .collect(Collectors.joining("\n", "\n", ""))
        );
    }
}
