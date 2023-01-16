/*
 * MIT License
 *
 * Copyright (c) 2022 Volodya
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

/**
 * Rule to check test case on not camel case name.
 */
public final class NotCamelCase implements Rule {

    /**
     * The test case.
     */
    final TestCase test;

    /**
     * Ctor.
     *
     * @param test The test case
     */
    public NotCamelCase(final TestCase test) {
        this.test = test;
    }

    @Override
    public void validate() throws WrongTestName {
        if (this.notCamelCase()) {
            throw new WrongTestName(
                this.test,
                "test has to be written by using Camel Case"
            );
        }
    }

    private boolean notCamelCase() {
        int stack = 0;
        for (final char c : this.test.name().toCharArray()) {
            if (Character.isUpperCase(c) && stack == 0) {
                return true;
            } else if (stack != 0) {
                stack = 0;
            }
            stack++;
        }
        return false;
    }
}
