package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.ProductionClass;
import com.github.lombrozo.testnames.Project;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.TestClass;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AllTestsHaveProductionClassRule implements Rule {

    private final Project project;

    AllTestsHaveProductionClassRule(final Project proj) {
        this.project = proj;
    }

    @Override
    public Collection<Complaint> complaints() {
        final Map<String, ProductionClass> classes = this.project.productionClasses().stream().collect(
            Collectors.toMap(t -> String.format("%sTest", t.name()), Function.identity())
        );
        final Collection<Complaint> complaints = new ArrayList<>(0);
        final Collection<TestClass> tests = this.project.testClasses();
        for (final TestClass test : tests) {
            if (!classes.containsKey(test.name())) {
                complaints.add(new Complaint.Fake("Test doesn't match"));
            }
        }
        return complaints;
    }
}
