package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.TestClass;
import com.github.lombrozo.testnames.TestClassCharacteristics;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class RuleInheritenceInTestsTest {

    @Test
    void doesNotFoundParents() {
        MatcherAssert.assertThat(
            "Should not find any complaints if there are no parents",
            new RuleInheritenceInTests(new TestClass.Fake()).complaints(),
            Matchers.emptyIterable()
        );
    }

    @Test
    void hasSomeParent() {
        final String parent = "some.parent.Parent";
        final TestClass.Fake clazz = new TestClass.Fake(new TestClassCharacteristics.Fake(parent));
        MatcherAssert.assertThat(
            "Should find complaint if there is a parent",
            new RuleInheritenceInTests(clazz).complaints().iterator().next().message(),
            Matchers.containsString(
                String.format(
                    "The test class '%s' has parent class '%s', inheritance in tests is dangerous for maintainability",
                    clazz.name(),
                    parent
                )
            )
        );
    }
}
