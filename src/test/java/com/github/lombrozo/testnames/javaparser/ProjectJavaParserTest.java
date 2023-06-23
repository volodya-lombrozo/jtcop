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
package com.github.lombrozo.testnames.javaparser;

import com.github.lombrozo.testnames.ProductionClass;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.TestClass;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for {@link ProjectJavaParser}.
 *
 * @since 0.2
 */
final class ProjectJavaParserTest {

    @Test
    void returnsProductionClasses(@TempDir final Path tmp) throws IOException {
        final String name = "ProductionClass.java";
        Files.write(tmp.resolve(name), "".getBytes(StandardCharsets.UTF_8));
        final Collection<ProductionClass> classes = new ProjectJavaParser(
            tmp,
            tmp
        ).productionClasses();
        MatcherAssert.assertThat(
            String.format(
                "Project has to have exactly one production class, but was %d",
                classes.size()
            ),
            classes,
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            String.format(
                "Production class name is wrong. Expected: %s, but was: %s",
                name,
                classes.iterator().next().name()
            ),
            classes.iterator().next().name(),
            Matchers.equalTo(name)
        );
    }

    @Test
    void returnsNotProductionClasses(@TempDir final Path tmp) throws IOException {
        final String name = "TestClass.java";
        Files.write(tmp.resolve(name), "".getBytes(StandardCharsets.UTF_8));
        final Collection<TestClass> classes = new ProjectJavaParser(
            tmp,
            tmp
        ).testClasses();
        MatcherAssert.assertThat(
            String.format("Project has to have exactly one test class, but was %d", classes.size()),
            classes,
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            String.format(
                "Test class name is wrong. Expected: %s, but was: %s",
                name,
                classes.iterator().next().name()
            ),
            classes.iterator().next().name(),
            Matchers.equalTo(name)
        );
    }

    @Test
    void ignoresAllRulesThatWasAddedToExclusions(@TempDir final Path temp) throws IOException {
        Files.copy(JavaTestClasses.WRONG_NAME.inputStream(), temp.resolve("WrongName.java"));
        final String[] exclusions = {"JTCOP.CustomRule", "FreeExclusion"};
        final Collection<TestClass> classes = new ProjectJavaParser(
            temp,
            temp,
            Arrays.asList(exclusions)
        ).testClasses();
        MatcherAssert.assertThat(classes, Matchers.hasSize(1));
        final Set<String> clevel = classes.stream()
            .map(TestClass::suppressed)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        final Set<String> mlevel = classes.stream()
            .map(TestClass::all)
            .flatMap(Collection::stream)
            .collect(Collectors.toList()).stream()
            .map(TestCase::suppressed)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
        MatcherAssert.assertThat(
            String.format(
                "The class level suppression list is wrong. Expected: %s, but was: %s",
                Arrays.toString(exclusions), clevel
            ),
            clevel,
            Matchers.containsInAnyOrder(exclusions)
        );
        MatcherAssert.assertThat(
            String.format(
                "The method level suppression list is wrong. Expected: %s, but was: %s",
                Arrays.toString(exclusions), mlevel
            ),
            mlevel,
            Matchers.containsInAnyOrder(exclusions)
        );
    }
}
