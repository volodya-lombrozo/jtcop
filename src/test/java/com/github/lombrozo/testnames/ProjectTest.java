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

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test class for {@link Project}.
 *
 * @since 0.1.17
 */
class ProjectTest {

    @Test
    void createsCombinedProject() {
        MatcherAssert.assertThat(
            "Should have combined production classes",
            new Project.Combined(
                new Project.Fake(new ProductionClass.Fake("One")),
                new Project.Fake(new ProductionClass.Fake("Two"))
            ).productionClasses(),
            Matchers.hasSize(2)
        );
    }

    @Test
    void createsWithoutTests() {
        final Project.WithoutTests without = new Project.WithoutTests(
            new Project.Fake(new ProductionClass.Fake(), new TestClass.Fake())
        );
        MatcherAssert.assertThat(
            "Project without tests should have no test classes",
            without.testClasses(),
            Matchers.empty()
        );
        MatcherAssert.assertThat(
            "Project without tests should have production classes",
            without.productionClasses(),
            Matchers.hasSize(1)
        );
    }
}
