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
package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.ProductionClass;
import com.github.lombrozo.testnames.Project;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.TestClass;
import com.github.lombrozo.testnames.complaints.ComplaintLinked;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The rule that checks that all tests have production class.
 *
 * @since 0.2
 */
public final class RuleAllTestsHaveProductionClass implements Rule {

    /**
     * The name of the rule.
     */
    public static final String NAME = "RuleAllTestsHaveProductionClass";

    /**
     * The pattern to replace the underscore sign "_".
     */
    private static final Pattern UNDERSCORE = Pattern.compile("_");

    /**
     * The pattern to replace the dollar sign "$".
     */
    private static final Pattern DOLLAR = Pattern.compile("\\$");

    /**
     * The project to check.
     */
    private final Project project;

    /**
     * Test class to check.
     */
    private final TestClass test;

    /**
     * Primary ctor.
     * @param proj The project to check.
     * @param test The test to check.
     */
    public RuleAllTestsHaveProductionClass(final Project proj, final TestClass test) {
        this.project = proj;
        this.test = test;
    }

    @Override
    public Collection<Complaint> complaints() {
        final Map<String, ProductionClass> classes = this.project.productionClasses()
            .stream()
            .filter(clazz -> RuleAllTestsHaveProductionClass.isNotPackageInfo(clazz.name()))
            .collect(
                Collectors.toMap(
                    RuleAllTestsHaveProductionClass::correspondingTest,
                    Function.identity(),
                    (first, second) -> first
                )
            );
        final Collection<Complaint> complaints = new ArrayList<>(0);
        final String name = RuleAllTestsHaveProductionClass.clean(this.test.name());
        if (!classes.containsKey(name)
            && !this.test.characteristics().isIntegrationTest()
            && RuleAllTestsHaveProductionClass.isNotPackageInfo(this.test.name())) {
            complaints.add(
                new ComplaintLinked(
                    String.format("Test %s doesn't have corresponding production class", name),
                    String.format(
                        "Either rename or move the test class %s",
                        this.test.path()
                    ),
                    this.getClass(),
                    "all-have-production-class.md"
                )
            );
        }
        return complaints;
    }

    /**
     * Returns the name of the test class that corresponds to the production class.
     * @param clazz Production class.
     * @return The name of the test class that corresponds to the production class.
     */
    private static String correspondingTest(final ProductionClass clazz) {
        return String.format("%sTest", RuleAllTestsHaveProductionClass.clean(clazz.name()));
    }

    /**
     * Removes that not important part of the name.
     * @param original The original name.
     * @return The cleaned name.
     */
    private static String clean(final String original) {
        final String plain;
        if (original.endsWith(".java")) {
            plain = original.substring(0, original.length() - 5);
        } else if (original.endsWith(".class")) {
            plain = original.substring(0, original.length() - 6);
        } else {
            plain = original;
        }
        return RuleAllTestsHaveProductionClass.DOLLAR.matcher(
            RuleAllTestsHaveProductionClass.UNDERSCORE.matcher(plain).replaceAll("")
        ).replaceAll("");
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
