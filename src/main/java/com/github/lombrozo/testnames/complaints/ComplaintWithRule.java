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
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * The complaint with the reference to rule name.
 *
 * @since 0.1.15
 * @todo #527:60min Add line and filename to the complaint message.
 *  Currently, we don't have such attributes, but it can be helpful to include
 *  line number and filename into the complaint message. E.g.:
 *  ```text
 *  [org/foo/bar/Foo.java:12]: ... (RuleNotContainsTestWord)
 *  ```
 *  Note, that filename path should be relativized to the `src/test/java` directory.
 */
@ToString
@EqualsAndHashCode
public final class ComplaintWithRule implements Complaint {

    /**
     * The complaint message.
     */
    private final String complaint;

    /**
     * Rule name.
     */
    private final String rule;

    /**
     * Constructor.
     * @param complaint The complaint message.
     * @param rule The rule name
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public ComplaintWithRule(final String complaint, final Class<?> rule) {
        this(complaint, rule.getSimpleName());
    }

    /**
     * Constructor.
     * @param complaint The complaint message.
     * @param rule The rule name
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    private ComplaintWithRule(final String complaint, final String rule) {
        this.complaint = complaint;
        this.rule = rule;
    }

    @Override
    public String message() {
        return new Complaint.Text(String.format("%s (%s)", this.complaint, this.rule)).message();
    }
}
