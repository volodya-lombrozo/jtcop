/*
 * MIT License
 *
 * Copyright (c) 2022-2025 Volodya Lombrozo
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
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.TestClass;
import com.github.lombrozo.testnames.complaints.ComplaintWithRule;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The rule that checks the correct name of an integration test class.
 *
 * @since 0.1.17
 */
public final class RuleCorrectTestName implements Rule {

    /**
     * Allowed prefixes for integration and unit test class names.
     */
    private static final String[] ALLOWED_PREFIXES = {"IT", "ITCase", "Test", "Tests", "TestCase"};

    /**
     * The test class to check.
     */
    private final TestClass test;

    /**
     * Constructor.
     * @param klass The test class to check.
     */
    public RuleCorrectTestName(final TestClass klass) {
        this.test = klass;
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList(this.getClass().getSimpleName());
    }

    @Override
    public Collection<Complaint> complaints() {
        final Collection<Complaint> complaints;
        final String name = this.test.name();
        if (RuleCorrectTestName.isIncorrectName(name)) {
            complaints = Collections.singleton(
                new ComplaintWithRule(
                    String.format(
                        "Test class name should start or end with one of the following prefixes: %s",
                        Arrays.toString(RuleCorrectTestName.ALLOWED_PREFIXES)
                    ),
                    this.getClass()
                )
            );
        } else {
            complaints = Collections.emptySet();
        }
        return complaints;
    }

    /**
     * Checks if the name of the test class is incorrect.
     * @param name The name of the test class.
     * @return True if the name is incorrect.
     */
    private static boolean isIncorrectName(final String name) {
        final String clear = RuleCorrectTestName.withoutExtension(name);
        return Arrays.stream(RuleCorrectTestName.ALLOWED_PREFIXES)
            .noneMatch(prefix -> clear.startsWith(prefix) || clear.endsWith(prefix));
    }

    /**
     * Removes extension from the name if it exists.
     * @param with The name of the test class with extension.
     * @return The name of the test class without extension.
     */
    private static String withoutExtension(final String with) {
        final String result;
        if (with.endsWith(".java")) {
            result = with.substring(0, with.length() - 5);
        } else if (with.endsWith(".class")) {
            result = with.substring(0, with.length() - 6);
        } else {
            result = with;
        }
        return result;
    }
}
