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
 * Tests for {@link JavaParserProject}.
 *
 * @since 0.2
 */
final class JavaParserProjectTest {

    @Test
    void returnsProductionClasses(@TempDir final Path tmp) throws IOException {
        final String name = "ProductionClass.java";
        Files.write(tmp.resolve(name), "".getBytes(StandardCharsets.UTF_8));
        final Collection<ProductionClass> classes = new JavaParserProject(
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
        Files.write(tmp.resolve(name), "final class TestClass{}".getBytes(StandardCharsets.UTF_8));
        final Collection<TestClass> classes = new JavaParserProject(
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
        final Collection<TestClass> classes = new JavaParserProject(
            temp,
            temp,
            Arrays.asList(exclusions)
        ).testClasses();
        MatcherAssert.assertThat(
            String.format("Project has to have exactly one test class, but was %d", classes.size()),
            classes,
            Matchers.hasSize(1)
        );
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

    @Test
    void ignoresAnnotations(@TempDir final Path temp) throws IOException {
        Files.copy(
            JavaTestClasses.PACKAGE_INFO.inputStream(),
            temp.resolve("AnnotationDeclaration.java")
        );
        final Collection<TestClass> classes = new JavaParserProject(
            temp,
            temp
        ).testClasses();
        MatcherAssert.assertThat(
            String.format("Project has to ignore annotations, but was: %s", classes),
            classes,
            Matchers.empty()
        );
    }

    @Test
    void ignoresPackageInfoClasses(@TempDir final Path temp) throws IOException {
        Files.copy(JavaTestClasses.PACKAGE_INFO.inputStream(), temp.resolve("package-info.java"));
        final Collection<TestClass> classes = new JavaParserProject(
            temp,
            temp
        ).testClasses();
        MatcherAssert.assertThat(
            String.format("Project has to ignore package-info.java classes, but was: %s", classes),
            classes,
            Matchers.empty()
        );
    }

    @Test
    void ignoresInterfaces(@TempDir final Path temp) throws IOException {
        Files.copy(
            JavaTestClasses.SUPPRESSED_INTERFACE.inputStream(),
            temp.resolve("SuppressedInterface.java")
        );
        final Collection<TestClass> classes = new JavaParserProject(
            temp,
            temp
        ).testClasses();
        MatcherAssert.assertThat(
            String.format("Project has to ignore interfaces, but was: %s", classes),
            classes,
            Matchers.empty()
        );
    }

    @Test
    void returnsClassParentsForJUnitCallback(@TempDir final Path temp) throws IOException {
        Files.copy(
            JavaTestClasses.JUNIT_CALLBACK.inputStream(),
            temp.resolve("JUnitCallback.java")
        );
        final Collection<TestClass> classes = new JavaParserProject(temp, temp).testClasses();
        MatcherAssert.assertThat(
            String.format("Project has to return exactly one class, but was: %s", classes),
            classes,
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            "Class has to be JUnit extension, but was not",
            classes.iterator().next().characteristics().isJUnitExtension(),
            Matchers.is(true)
        );
    }

    @Test
    void returnsClassParentsForJUnitExtension(@TempDir final Path temp) throws IOException {
        Files.copy(
            JavaTestClasses.JUNIT_CONDITION.inputStream(),
            temp.resolve("JUnitCondition.java")
        );
        final Collection<TestClass> classes = new JavaParserProject(temp, temp).testClasses();
        MatcherAssert.assertThat(
            String.format("Project has to return exactly one class, but was: %s", classes),
            classes,
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            "Class has to be JUnit extension, but was not",
            classes.iterator().next().characteristics().isJUnitExtension(),
            Matchers.is(true)
        );
    }
}
