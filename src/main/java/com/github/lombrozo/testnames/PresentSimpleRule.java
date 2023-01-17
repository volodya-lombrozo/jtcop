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

/**
 * The rule checks if test case in present simple.
 *
 * @since 0.1.0
 */
public final class PresentSimpleRule implements Rule {

    /**
     * The rules.
     */
    private final Collection<Rule> all;

    /**
     * Ctor.
     *
     * @param test The test case to check
     */
    public PresentSimpleRule(final TestCase test) {
        this.all = Arrays.asList(
            new NotCamelCase(test),
            new NotContainsTestWord(test),
            new NotSpam(test),
            new NotUsesSpecialCharacters(test),
            new PresentTense(test)
        );
    }

    @Override
    public void validate() throws WrongTestName {
        for (final Rule rule : this.all) {
            rule.validate();
        }
    }
}
