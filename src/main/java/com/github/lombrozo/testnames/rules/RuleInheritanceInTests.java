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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * The rule that checks a test class doesn't use inheritance.
 * @since 1.2.0
 */
@SuppressWarnings("PMD.TestClassWithoutTestCases")
public final class RuleInheritanceInTests implements Rule {

    /**
     * Test class.
     */
    private final TestClass clazz;

    /**
     * Constructor.
     * @param clazz Test class.
     */
    public RuleInheritanceInTests(final TestClass clazz) {
        this.clazz = clazz;
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList(this.getClass().getSimpleName());
    }

    @Override
    public Collection<Complaint> complaints() {
        final Collection<Complaint> result;
        final String parent = this.clazz.characteristics().parent();
        if ("java.lang.Object".equals(parent)) {
            result = Collections.emptyList();
        } else {
            result = Collections.singleton(
                new ComplaintWithRule(
                    String.format(
                        "The test class '%s' has the parent class '%s', while inheritance in tests is dangerous for maintainability",
                        this.clazz.name(),
                        parent
                    ),
                    this.getClass()
                )
            );
        }
        return result;
    }
}
