package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.ProductionClass;
import com.github.lombrozo.testnames.Project;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.TestClass;
import java.util.Collection;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class AllTestsHaveProductionClassRuleTest {

    @Test
    void checksThatAllHaveCorrespondingProductionClass() {
        MatcherAssert.assertThat(
            new AllTestsHaveProductionClassRule(
                new Project.Fake(
                    new ProductionClass.Fake("Identical"),
                    new TestClass.Fake("IdenticalTest", new TestCase.Fake())
                )
            ).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void checksThatDoesNotHaveCorrespondingProductionClass() {
        final Collection<Complaint> complaints = new AllTestsHaveProductionClassRule(
            new Project.Fake(new TestClass.Fake())
        ).complaints();
        MatcherAssert.assertThat(
            complaints,
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            complaints.iterator().next().message(),
            Matchers.equalTo("Test doesn't match")
        );
    }
}
