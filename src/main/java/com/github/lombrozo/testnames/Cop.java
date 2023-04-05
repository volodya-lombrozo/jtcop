package com.github.lombrozo.testnames;

import com.github.lombrozo.testnames.rules.AllTestsHaveProductionClassRule;
import com.github.lombrozo.testnames.rules.AllTestsInPresentSimple;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Cop {

    private final Project project;

    public Cop(final Project proj) {
        this.project = proj;
    }

    public Collection<Complaint> check() {
        final Collection<Complaint> res = new ArrayList<>(
            new AllTestsHaveProductionClassRule(this.project).complaints()
        );
        final List<Complaint> list = this.project.testClasses().stream()
            .map(AllTestsInPresentSimple::new)
            .map(AllTestsInPresentSimple::complaints)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        res.addAll(list);
        return res;
    }
}
