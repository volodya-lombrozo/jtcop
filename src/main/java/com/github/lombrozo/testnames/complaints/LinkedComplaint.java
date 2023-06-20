package com.github.lombrozo.testnames.complaints;

import com.github.lombrozo.testnames.Complaint;
import java.net.MalformedURLException;
import java.net.URL;

public class LinkedComplaint implements Complaint {

    private final String message;
    private final String suggestion;
    private final String rule;
    private final URL link;

    public LinkedComplaint(
        final String message,
        final String suggestion,
        final Class<?> rule,
        final String document
    ) {
        this(message, suggestion, rule.getSimpleName(), LinkedComplaint.link(document));
    }

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
                "Problem: %s.%nPossible solution: %s.%nYou can also ignore the rule by adding @SuppressWarnings(\"JTCOP.%s\") annotation.%nRule code: %s.%nYou can read more about the rule here: %s",
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
