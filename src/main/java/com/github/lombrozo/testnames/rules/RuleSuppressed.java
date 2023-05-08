package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.TestClass;
import java.util.Collection;
import java.util.Collections;

/**
 * Suppressed rule.
 *
 * @since 0.1.14
 */
public class RuleSuppressed implements Rule {
    private final Rule delegate;
    private final Collection<String> suppressed;

    public RuleSuppressed(final Rule rule, final TestClass test) {
        this(rule, test.suppressed());
    }

    RuleSuppressed(final Rule rule) {
        this(rule, Collections.singleton(rule.getClass().getSimpleName()));
    }

    RuleSuppressed(final Rule rule, final TestCase test) {
        this(rule, test.suppressed());
    }

    private RuleSuppressed(final Rule rule, final Collection<String> hidden) {
        this.delegate = rule;
        this.suppressed = hidden;
    }

    @Override
    public Collection<Complaint> complaints() {
        if (this.isSuppressed()) {
            return Collections.emptyList();
        }
        return this.delegate.complaints();
    }

    private boolean isSuppressed() {
        return this.suppressed.stream().anyMatch(
            hidden -> hidden.equals(this.delegate.getClass().getSimpleName())
        );
    }
}
