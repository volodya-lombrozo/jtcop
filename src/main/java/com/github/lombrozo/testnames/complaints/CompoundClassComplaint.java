package com.github.lombrozo.testnames.complaints;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.TestClass;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class CompoundClassComplaint implements Complaint {

    private final TestClass clazz;
    private final Collection<Complaint> complaints;

    public CompoundClassComplaint(final TestClass clazz, Complaint... complaints) {
        this.clazz = clazz;
        this.complaints = Arrays.asList(complaints);
    }

    @Override
    public String message() {
        final AtomicInteger counter = new AtomicInteger(1);
        return String.format(
            "Class %s has some complaints, the path %s:%s",
            this.clazz.name(),
            this.clazz.path(),
            this.complaints.stream()
                .map(Complaint::message)
                .map(message -> String.format("\n\t%d) %s", counter.getAndIncrement(), message))
                .collect(Collectors.joining())
        );
    }
}
