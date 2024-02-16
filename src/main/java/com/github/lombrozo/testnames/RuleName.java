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
package com.github.lombrozo.testnames;

import java.util.Locale;

/**
 * Rule name that understands prefix.
 * @since 0.1.14
 */
public class RuleName {

    /**
     * Prefix for the rule name.
     */
    private static final String PREFIX = "JTCOP";

    /**
     * The origin rule name.
     */
    private final String name;

    /**
     * Ctor.
     * @param name The origin rule name.
     */
    public RuleName(final String name) {
        this.name = name;
    }

    /**
     * Checks if the rule name has prefix.
     * @return True if the rule name has prefix.
     */
    public boolean hasPrefix() {
        return this.name.toUpperCase(Locale.ROOT).startsWith(RuleName.PREFIX);
    }

    /**
     * Retrieves rule name without prefix.
     * @return Rule name without prefix.
     */
    public String withoutPrefix() {
        final String result;
        if (this.hasPrefix()) {
            result = this.name.substring(RuleName.PREFIX.length() + 1);
        } else {
            result = this.name;
        }
        return result;
    }
}
