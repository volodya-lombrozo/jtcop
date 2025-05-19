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
import com.github.lombrozo.testnames.TestClassCharacteristics;
import com.github.lombrozo.testnames.complaints.ComplaintWithRule;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Checks that test class has only methods annotated with @Test annotation.
 *
 * @since 0.1.19
 * @todo #194:90min Incorporate this rule into the Cop class.
 *  This rule should be incorporated into the Cop class. When this is done, we should add
 *  integration tests for that rule and remove this puzzle.
 */
public final class RuleOnlyTestMethods implements Rule {

    /**
     * The test class.
     */
    private final TestClass klass;

    /**
     * Constructor.
     * @param klass The test class to check.
     */
    public RuleOnlyTestMethods(final TestClass klass) {
        this.klass = klass;
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList(this.getClass().getSimpleName());
    }

    @Override
    public Collection<Complaint> complaints() {
        final Collection<Complaint> result;
        final TestClassCharacteristics props = this.klass.characteristics();
        if (props.numberOfMethods() == props.numberOfTests()) {
            result = Collections.emptyList();
        } else {
            result = Collections.singleton(
                new ComplaintWithRule(
                    String.format(
                        "All methods of the test class \"%s\" should be annotated with @Test annotation",
                        this.klass.name()
                    ),
                    this.getClass()
                )
            );
        }
        return result;
    }
}
