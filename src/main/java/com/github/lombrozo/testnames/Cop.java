/*
 * MIT License
 *
 * Copyright (c) 2022-2024 Volodya Lombrozo
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
package com.github.lombrozo.testnames;

import com.github.lombrozo.testnames.rules.RuleAllTestsHaveProductionClass;
import com.github.lombrozo.testnames.rules.RuleCorrectTestCases;
import com.github.lombrozo.testnames.rules.RuleCorrectTestName;
import com.github.lombrozo.testnames.rules.RuleInheritanceInTests;
import com.github.lombrozo.testnames.rules.RuleOnlyTestMethods;
import com.github.lombrozo.testnames.rules.RuleSuppressed;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The cop that checks the project.
 *
 * @since 0.2
 */
final class Cop {

    /**
     * The project to check.
     */
    private final Project project;

    /**
     * The law to check the project.
     */
    private final Function<Suspect, Stream<Rule>> law;

    /**
     * Ctor.
     * @param proj The project to check.
     * @param mocks Max number of mocks allowed
     * @todo #393:35min Refactor Cop ctor to accept number of arguments.
     *  We should refactor ctor to accept a number of argument from one
     *  object with them. For instance: new Args(new Arg("maxNumberOfMocks", 2), ...).
     *  The same object should be applied to Cop#regular method.
     */
    Cop(final Project proj, final int mocks) {
        this(proj, Cop.regular(mocks));
    }

    /**
     * Ctor.
     * @param project The project to check.
     * @param law The law to check the project.
     */
    Cop(
        final Project project,
        final Function<Suspect, Stream<Rule>> law
    ) {
        this.project = project;
        this.law = law;
    }

    /**
     * Checks the project.
     * @return The complaints.
     */
    Collection<Complaint> inspection() {
        return this.project.testClasses().stream()
            .map(testClass -> new Suspect(this.project, testClass))
            .flatMap(this.law)
            .map(Rule::complaints)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    /**
     * Experimental law.
     * @return The experimental law which will be applied to projects that uses `experimental`
     *  features.
     */
    static Function<Suspect, Stream<Rule>> experimental() {
        return suspect -> Stream.of(
            new RuleSuppressed(new RuleOnlyTestMethods(suspect.test()), suspect.test())
        );
    }

    /**
     * Regular law.
     * @return The regular law which will be applied to all projects.
     */
    private static Function<Suspect, Stream<Rule>> regular(final int mocks) {
        return suspect -> Stream.of(
            new RuleSuppressed(
                new RuleAllTestsHaveProductionClass(suspect.project(), suspect.test()),
                suspect.test()
            ),
            new RuleSuppressed(new RuleCorrectTestName(suspect.test()), suspect.test()),
            new RuleSuppressed(new RuleInheritanceInTests(suspect.test()), suspect.test()),
            new RuleSuppressed(new RuleCorrectTestCases(suspect.test(), mocks), suspect.test())
        );
    }
}
