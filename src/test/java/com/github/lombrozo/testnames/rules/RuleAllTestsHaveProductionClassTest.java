/*
 * MIT License
 *
 * Copyright (c) 2022-2023 Volodya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.ProductionClass;
import com.github.lombrozo.testnames.Project;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.TestClass;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link RuleAllTestsHaveProductionClass}.
 *
 * @since 0.2
 */
final class RuleAllTestsHaveProductionClassTest {

    @Test
    void checksThatAllHaveCorrespondingProductionClass() {
        MatcherAssert.assertThat(
            "Should not have complaints, because all tests have corresponding production class",
            new RuleAllTestsHaveProductionClass(
                new Project.Fake(
                    new ProductionClass.Fake("Identical"),
                    new TestClass.Fake("IdenticalTest", new TestCase.Fake())
                )
            ).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void checksThatClassHasCorrespondingProductionClassWithExtension() {
        MatcherAssert.assertThat(
            "Should not have complaints, because all tests have corresponding production class, even with .java extension",
            new RuleAllTestsHaveProductionClass(
                new Project.Fake(
                    new ProductionClass.Fake("Hello.java"),
                    new TestClass.Fake("HelloTest.java")
                )
            ).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void checksThatClassHasCorrespondingProductionClassWithClassExtension() {
        MatcherAssert.assertThat(
            "Should not have complaints, because all tests have corresponding production class, even with .class extension",
            new RuleAllTestsHaveProductionClass(
                new Project.Fake(
                    new ProductionClass.Fake("Hello.class"),
                    new TestClass.Fake("HelloTest.class")
                )
            ).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void checksThatClassHasCorrespondingProductionClassWithDifferentExtensions() {
        MatcherAssert.assertThat(
            "Should not have complaints, because all tests have corresponding production class, even if they have different extensions",
            new RuleAllTestsHaveProductionClass(
                new Project.Fake(
                    new ProductionClass.Fake("ComplaintClass.java"),
                    new TestClass.Fake("ComplaintClassTest.class")
                )
            ).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void checksThatDoesNotHaveCorrespondingProductionClass() {
        final Collection<Complaint> complaints = new RuleAllTestsHaveProductionClass(
            new Project.Fake(new TestClass.Fake())
        ).complaints();
        MatcherAssert.assertThat(
            "Should have complaints, because doesn't have corresponding production class",
            complaints,
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            "Should have complaint with correct message",
            complaints.iterator().next().message(),
            Matchers.containsString(
                "Test FakeClassTest doesn't have corresponding production class."
            )
        );
    }

    @Test
    void doesNotCheckBecauseSuppressed() {
        MatcherAssert.assertThat(
            "Should not have complaints, because suppressed",
            new RuleSuppressed(
                new RuleAllTestsHaveProductionClass(
                    new Project.Fake(
                        new TestClass.Fake(
                            Collections.singletonList(RuleAllTestsHaveProductionClass.NAME)
                        )
                    )
                )
            ).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void filtersPackageInfoProductionClasses() {
        final String info = "package-info.java";
        final Collection<Complaint> complaints = new RuleAllTestsHaveProductionClass(
            new Project.Fake(
                new ProductionClass.Fake(info),
                new ProductionClass.Fake(info),
                new ProductionClass.Fake(info)
            )
        ).complaints();
        MatcherAssert.assertThat(
            String.format(
                "Should filter package-info.java production classes, but some complaints was found %s",
                complaints
            ),
            complaints,
            Matchers.empty()
        );
    }

    @Test
    void filtersPackageInfoClasses() {
        final String info = "package-info.java";
        final Collection<Complaint> complaints = new RuleAllTestsHaveProductionClass(
            new Project.Fake(
                new TestClass.Fake(info),
                new TestClass.Fake(info),
                new TestClass.Fake(info)
            )
        ).complaints();
        MatcherAssert.assertThat(
            String.format(
                "Should filter package-ingo.java test classes, but some complaints was found %s",
                complaints
            ),
            complaints,
            Matchers.empty()
        );
    }

    @Test
    void handlesClassesWithTheSameNames() {
        final String name = "Hello";
        final Collection<Complaint> complaints = new RuleAllTestsHaveProductionClass(
            new Project.Fake(
                Arrays.asList(new ProductionClass.Fake(name), new ProductionClass.Fake(name)),
                Collections.singleton(new TestClass.Fake("HelloTest"))
            )
        ).complaints();
        MatcherAssert.assertThat(
            String.format("Should not have complaints, but was %s", complaints),
            complaints,
            Matchers.empty()
        );
    }

    @Test
    void handlesCasesWithUnderscores() {
        MatcherAssert.assertThat(
            "Should not have complaints, because all tests have corresponding production class, but with underscores",
            new RuleAllTestsHaveProductionClass(
                new Project.Fake(
                    new ProductionClass.Fake("IdenticalName"),
                    new TestClass.Fake("Identical_Name_Test", new TestCase.Fake())
                )
            ).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void handlesCasesWithDollarSign() {
        MatcherAssert.assertThat(
            "Should not have complaints, because all tests have corresponding production class, but with dollar sign",
            new RuleAllTestsHaveProductionClass(
                new Project.Fake(
                    new ProductionClass.Fake("EObool$EOnot"),
                    new TestClass.Fake("EObool$EOnot$Test", new TestCase.Fake())
                )
            ).complaints(),
            Matchers.empty()
        );
    }
}
