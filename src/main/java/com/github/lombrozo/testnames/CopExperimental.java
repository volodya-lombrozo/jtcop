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
package com.github.lombrozo.testnames;

import com.github.lombrozo.testnames.rules.RuleOnlyTestMethods;
import com.github.lombrozo.testnames.rules.RuleSuppressed;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Experimental cop which checks only experimental rules.
 *
 * @since 0.1.20
 * @todo #231:90min Refactor Cop and CopExperimental.
 *  We need to refactor Cop and CopExperimental classes because they both have significant
 *  code duplication. In order to do so we have to decide if we introduce one more interface
 *  for Cop, or just make Cop more suitable for experimental features, or create one more
 *  abstraction. It worth mention that Cop and Rule interface rather similar. So, maybe it makes
 *  sense to create a tree of rules instead.
 */
public class CopExperimental {

    /**
     * Project.
     */
    private final Project project;

    /**
     * Constructor.
     * @param project Project.
     */
    public CopExperimental(final Project project) {
        this.project = project;
    }

    /**
     * Checks the project.
     * @return The complaints.
     */
    public Collection<Complaint> inspection() {
        return this.project.testClasses().stream()
            .flatMap(this::classLevelRules)
            .map(Rule::complaints)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
    }

    /**
     * Creates the class-level rules.
     * @param klass The test class to check.
     * @return The class-level rules.
     */
    private Stream<Rule> classLevelRules(final TestClass klass) {
        return Stream.of(
            new RuleSuppressed(new RuleOnlyTestMethods(klass), klass)
        );
    }
}
