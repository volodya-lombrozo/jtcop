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
import java.net.MalformedURLException;
import java.net.URL;
import lombok.ToString;

/**
 * The complaint with link to the rule description.
 *
 * @since 0.1.15
 */
@ToString
public final class ComplaintLinked implements Complaint {

    /**
     * The complaint message.
     */
    private final String complaint;

    /**
     * The suggestion how to solve the problem.
     */
    private final String suggestion;

    /**
     * Rule name.
     */
    private final String rule;

    /**
     * The link to the rule description.
     */
    private final URL link;

    /**
     * Constructor.
     * @param complaint The complaint message.
     * @param suggestion The suggestion how to solve the problem
     * @param rule The rule name
     * @param document The document name to the rule description in the default repo.
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    public ComplaintLinked(
        final String complaint,
        final String suggestion,
        final Class<?> rule,
        final String document
    ) {
        this(complaint, suggestion, rule.getSimpleName(), ComplaintLinked.url(document));
    }

    /**
     * Constructor.
     * @param complaint The complaint message.
     * @param suggestion The suggestion how to solve the problem
     * @param rule The rule name
     * @param link The link to the rule description
     * @checkstyle ParameterNumberCheck (10 lines)
     */
    private ComplaintLinked(
        final String complaint,
        final String suggestion,
        final String rule,
        final URL link
    ) {
        this.complaint = complaint;
        this.suggestion = suggestion;
        this.rule = rule;
        this.link = link;
    }

    @Override
    public String message() {
        return new Complaint.Text(
            String.format(
                "%s.%n\t%s.%n\tYou can also ignore the rule by adding @SuppressWarnings(\"JTCOP.%s\") annotation.%n\tRule: %s.%n\tYou can read more about the rule here: %s",
                this.complaint,
                this.suggestion,
                this.rule,
                this.rule,
                this.link
            )
        ).message();
    }

    /**
     * Parses URL from String.
     * @param document String representation of the doc markdown file in the repo.
     * @return URL link
     */
    private static URL url(final String document) {
        try {
            return new URL(
                String.format(
                    "https://github.com/volodya-lombrozo/jtcop/blob/main/docs/rules/%s",
                    document
                )
            );
        } catch (final MalformedURLException ex) {
            throw new IllegalStateException(
                String.format(
                    "We have  problems with parsing rule documentation link %s",
                    document
                ),
                ex
            );
        }
    }
}
