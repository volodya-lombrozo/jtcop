package com.github.lombrozo.testnames;

import com.github.lombrozo.testnames.rules.AllTestsInPresentSimple;
import java.util.Collection;
import java.util.stream.Collectors;

public class Cop {

    private final Project project;

    public Cop(final Project proj) {
        this.project = proj;
    }

    public Collection<Complaint> check() {
        return this.project.testClasses().stream()
            .map(AllTestsInPresentSimple::new)
            .map(AllTestsInPresentSimple::complaints)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }
}
