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
package com.github.lombrozo.testnames;

import java.util.Arrays;
import java.util.Collection;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link ProjectWithoutJUnitExtensions}.
 *
 * @since 0.1.17
 */
final class ProjectWithoutJUnitExtensionsTest {

    @Test
    void returnsProductionClassesFromOrigin() {
        final ProductionClass[] expected = {
            new ProductionClass.Fake(),
            new ProductionClass.Fake(),
        };
        final Project project = new ProjectWithoutJUnitExtensions(new Project.Fake(expected));
        final Collection<ProductionClass> actual = project.productionClasses();
        MatcherAssert.assertThat(
            String.format(
                "Expected %s production classes, but got %s",
                Arrays.toString(expected),
                actual
            ),
            actual,
            Matchers.containsInAnyOrder(expected)
        );
    }

    @Test
    void filtersJUnitExtensions() {
        final Collection<TestClass> actual = new ProjectWithoutJUnitExtensions(
            new Project.Fake(
                new TestClass.Fake(true),
                new TestClass.Fake(true),
                new TestClass.Fake(true)
            )
        ).testClasses();
        MatcherAssert.assertThat(
            String.format(
                "Expected empty collection, because each test class implements JUnit extension, but got %s",
                actual
            ),
            actual,
            Matchers.empty()
        );
    }

    @Test
    void skipsNotJUnitExtensions() {
        final TestClass[] expected = {
            new TestClass.Fake("SomeTest"),
            new TestClass.Fake("AnotherTest"),
        };
        final Collection<TestClass> actual = new ProjectWithoutJUnitExtensions(
            new Project.Fake(expected)
        ).testClasses();
        MatcherAssert.assertThat(
            String.format(
                "Expected %s test classes, but got %s",
                Arrays.toString(expected),
                actual
            ),
            actual,
            Matchers.containsInAnyOrder(expected)
        );
    }
}
