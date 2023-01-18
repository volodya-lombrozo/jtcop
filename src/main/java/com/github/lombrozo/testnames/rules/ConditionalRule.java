package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.Rule;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Supplier;

public class ConditionalRule implements Rule {

    private final Supplier<Boolean> predicate;
    private final Complaint complaint;

    public ConditionalRule(
        final Supplier<Boolean> predicate,
        final Complaint complaint
    ) {
        this.predicate = predicate;
        this.complaint = complaint;
    }

    @Override
    public Collection<Complaint> complaints() {
        final Collection<Complaint> res;
        if (this.predicate.get()) {
            res = Collections.singleton(this.complaint);
        } else {
            res = Collections.emptyList();
        }
        return res;
    }
}
