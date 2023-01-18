package com.github.lombrozo.testnames;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class ComplexComplaint implements Complaint {

    private final Collection<Complaint> complaints;

    public ComplexComplaint(Complaint... complaints) {
        this(Arrays.asList(complaints));
    }

    public ComplexComplaint(final Collection<Complaint> complaints) {
        this.complaints = complaints;
    }

    @Override
    public String message() {
        return this.complaints.stream()
            .map(Complaint::message)
            .collect(Collectors.joining("\n", "\n", ""));
    }
}
