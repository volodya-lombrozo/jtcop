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

import com.github.lombrozo.testnames.TestClass;
import com.github.lombrozo.testnames.TestClassCharacteristics;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link RuleInheritanceInTests}.
 * @since 1.2
 */
final class RuleInheritanceInTestsTest {

    @Test
    void doesNotFoundParents() {
        MatcherAssert.assertThat(
            "Should not find any complaints if there are no parents",
            new RuleInheritanceInTests(new TestClass.Fake()).complaints(),
            Matchers.emptyIterable()
        );
    }

    @Test
    void hasSomeParent() {
        final String parent = "some.parent.Parent";
        final TestClass.Fake clazz = new TestClass.Fake(new TestClassCharacteristics.Fake(parent));
        MatcherAssert.assertThat(
            "Should find complaint if there is a parent",
            new RuleInheritanceInTests(clazz).complaints().iterator().next().message(),
            Matchers.containsString(
                String.format(
                    "The test class '%s' has the parent class '%s'. Inheritance in tests is dangerous for maintainability",
                    clazz.name(),
                    parent
                )
            )
        );
    }
}
