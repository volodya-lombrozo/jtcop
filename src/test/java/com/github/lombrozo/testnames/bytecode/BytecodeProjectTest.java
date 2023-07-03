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
package com.github.lombrozo.testnames.bytecode;

import com.github.lombrozo.testnames.ProductionClass;
import com.github.lombrozo.testnames.Project;
import com.github.lombrozo.testnames.TestClass;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test cases for {@link BytecodeProject}.
 *
 * @since 0.1.17
 */
class BytecodeProjectTest {

    @Test
    @SuppressWarnings("JTCOP.RuleNotContainsTestWord")
    void parsesGeneratedTestClasses(@TempDir final Path temp) throws Exception {
        final ResourceOf resource = new ResourceOf("generated/RuleTest.class");
        Files.write(temp.resolve("RuleTest.class"), new BytesOf(resource).asBytes());
        final Collection<TestClass> classes = new BytecodeProject(
            temp,
            temp
        ).testClasses();
        MatcherAssert.assertThat(
            String.format("We expect to parse only one  test class, but got: %s", classes),
            classes,
            Matchers.hasSize(1)
        );
        final String name = classes.iterator().next().name();
        final String expected = "RuleTest";
        MatcherAssert.assertThat(
            String.format("Expected name of the test class is %s, but was %s", expected, name),
            name,
            Matchers.equalTo(expected)
        );
    }

    @Test
    void parsesGeneratedProductionClasses(@TempDir final Path temp) throws Exception {
        final ResourceOf resource = new ResourceOf("generated/RuleName.class");
        Files.write(temp.resolve("RuleName.class"), new BytesOf(resource).asBytes());
        final Collection<ProductionClass> classes = new BytecodeProject(
            temp,
            temp
        ).productionClasses();
        MatcherAssert.assertThat(
            String.format("We expect to parse only one production class, but got: %s", classes),
            classes,
            Matchers.hasSize(1)
        );
        final String name = classes.iterator().next().name();
        final String expected = "RuleName";
        MatcherAssert.assertThat(
            String.format(
                "Expected name of the production class is %s, but was %s",
                expected,
                name
            ),
            name,
            Matchers.equalTo(expected)
        );
    }

    @Test
    void handlesAbsentFolder(@TempDir final Path temp) {
        final Path absent = temp.resolve("absent");
        final Project project = new BytecodeProject(absent, absent);
        final Collection<ProductionClass> prod = project.productionClasses();
        final Collection<TestClass> tests = project.testClasses();
        MatcherAssert.assertThat(
            String.format(
                "Project with absent folder should return empty production classes, but was %s",
                prod
            ),
            prod,
            Matchers.empty()
        );
        MatcherAssert.assertThat(
            String.format(
                "Project with absent folder should return empty test classes, but was %s",
                tests
            ),
            tests,
            Matchers.empty()
        );
    }
}
