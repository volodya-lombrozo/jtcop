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
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.TestClass;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The rule that checks that all tests have production class.
 *
 * @since 0.2
 * @todo #1:30min Add documentation for RuleAllTestsHaveProductionClass rule.
 *  We have to add documentation to that rule and place it under the docs/rules directory.
 *  The documentation should contain the rule description, examples of correct and incorrect
 *  test class names.
 */
public final class RuleAllTestsHaveProductionClass implements Rule {

    /**
     * The project to check.
     */
    private final Project project;

    /**
     * Primary ctor.
     * @param proj The project to check.
     */
    public RuleAllTestsHaveProductionClass(final Project proj) {
        this.project = proj;
    }

    @Override
    public Collection<Complaint> complaints() {
        final Map<String, ProductionClass> classes = this.project.productionClasses()
            .stream()
            .filter(clazz -> RuleAllTestsHaveProductionClass.isNotPackageInfo(clazz.name()))
            .collect(
                Collectors.toMap(
                    RuleAllTestsHaveProductionClass::correspondingTestName,
                    Function.identity(),
                    (first, second) -> first
                )
            );
        final Collection<Complaint> complaints = new ArrayList<>(0);
        final Collection<TestClass> tests = this.project.testClasses().stream()
            .filter(test -> RuleAllTestsHaveProductionClass.isNotPackageInfo(test.name()))
            .collect(Collectors.toList());
        for (final TestClass test : tests) {
            final String name = test.name();
            if (!classes.containsKey(name)
                && RuleAllTestsHaveProductionClass.isNotSuppressed(test)) {
                complaints.add(
                    new Complaint.Text(
                        String.format(
                            "Test %s doesn't have corresponding production class.\n\tYou can either rename or move the test class %s:\n\tMore about that rule you can read here %s",
                            name,
                            test.path(),
                            "https://www.yegor256.com/2023/01/19/layout-of-tests.html#test-classes"
                        )
                    )
                );
            }
        }
        return complaints;
    }

    /**
     * Returns the name of the test class that corresponds to the production class.
     * @param klass The production class.
     * @return The name of the test class.
     */
    private static String correspondingTestName(final ProductionClass klass) {
        final String name = klass.name();
        final String plain;
        if (name.endsWith(".java")) {
            plain = String.format("%sTest.java", name.substring(0, name.length() - 5));
        } else {
            plain = String.format("%sTest", name);
        }
        return plain;
    }

    /**
     * Checks that the test class is not suppressed.
     * @param klass The test class to check.
     * @return True if the test class is not suppressed.
     */
    private static boolean isNotSuppressed(final TestClass klass) {
        return !klass.suppressed().contains("RuleAllTestsHaveProductionClass");
    }

    /**
     * Checks that the name is not package-info.java.
     * @param name The name to check.
     * @return True if the name is not package-info.java.
     */
    private static boolean isNotPackageInfo(final String name) {
        return !"package-info.java".equals(name);
    }
}
