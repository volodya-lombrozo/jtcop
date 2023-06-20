package com.github.lombrozo.testnames.complaints;

import com.github.lombrozo.testnames.Complaint;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The complaint with link to the rule description.
 *
 * @since 0.1.15
 */
public class LinkedComplaint implements Complaint {

    /**
     * The complaint message.
     */
    private final String message;

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
     * @param message The complaint message.
     * @param suggestion The suggestion how to solve the problem
     * @param rule The rule name
     * @param document The document name to the rule description in the default repo.
     */
    public LinkedComplaint(
        final String message,
        final String suggestion,
        final Class<?> rule,
        final String document
    ) {
        this(message, suggestion, rule.getSimpleName(), LinkedComplaint.link(document));
    }

    /**
     * Constructor.
     * @param message The complaint message.
     * @param suggestion The suggestion how to solve the problem
     * @param rule The rule name
     * @param link The link to the rule description
     */
    private LinkedComplaint(
        final String message,
        final String suggestion,
        final String rule,
        final URL link
    ) {
        this.message = message;
        this.suggestion = suggestion;
        this.rule = rule;
        this.link = link;
    }

    @Override
    public String message() {
        return new Complaint.Text(
            String.format(
                "Problem: %s.%n\tPossible solution: %s.%n\tYou can also ignore the rule by adding @SuppressWarnings(\"JTCOP.%s\") annotation.%n\tRule code: %s.%n\tYou can read more about the rule here: %s",
                this.message,
                this.suggestion,
                this.rule,
                this.rule,
                this.link
            )
        ).message();
    }

    /**
     * Parses URL from String
     * @param document String representation of the doc markdown file in the repo.
     * @return URL link
     */
    private static URL link(final String document) {
        try {
            return new URL(
                String.format(
                    "https://github.com/volodya-lombrozo/jtcop/blob/main/docs/rules/%s",
                    document
                )
            );
        } catch (final MalformedURLException ex) {
            throw new IllegalStateException(
                String.format("We have  problems with parsing rule documentation link %s",
                    document
                ),
                ex
            );
        }
    }
}
