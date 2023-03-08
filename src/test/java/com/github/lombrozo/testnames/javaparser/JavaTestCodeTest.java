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

import com.github.lombrozo.testnames.Cases;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test case for {@link com.github.lombrozo.testnames.javaparser.JavaTestCode}.
 *
 * @since 0.1.0
 */
final class JavaTestCodeTest {

    @Test
    void getsNames(@TempDir final Path temp) throws Exception {
        final String java = "TestSimple.java";
        final Path path = temp.resolve(java);
        Files.write(path, new BytesOf(new ResourceOf(java)).asBytes());
        final Cases cases = new JavaTestCode(path);
        MatcherAssert.assertThat(
            cases.all(),
            Matchers.containsInAnyOrder(
                new JavaParserCase(
                    "TestSimple",
                    "creates",
                    path
                ), new JavaParserCase(
                    "TestSimple",
                    "removes",
                    path
                ),
                new JavaParserCase(
                    "TestSimple",
                    "updates",
                    path
                )
            )
        );
    }

    @Test
    void returnsEmptyListIfItDoesNotHaveAnyCases(@TempDir final Path temp) throws Exception {
        final String java = "TestWithoutTests.java";
        final Path path = temp.resolve(java);
        Files.write(path, new BytesOf(new ResourceOf(java)).asBytes());
        MatcherAssert.assertThat(((Cases) new JavaTestCode(path)).all(), Matchers.empty());
    }

    @Test
    void getsNamesFromParameterizedCase(@TempDir final Path temp) throws Exception {
        final String java = "TestParameterized.java";
        final Path path = temp.resolve(java);
        Files.write(path, new BytesOf(new ResourceOf(java)).asBytes());
        final Cases cases = new JavaTestCode(path);
        MatcherAssert.assertThat(
            cases.all(),
            Matchers.containsInAnyOrder(
                new JavaParserCase(
                    "TestParameterized",
                    "checksCases",
                    path
                )
            )
        );
    }

    @Test
    void throwsExceptionIfFileNotFound(@TempDir final Path temp) {
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new JavaTestCode(temp.resolve("TestNotFound.java")).all()
        );
    }

    @Test
    void throwsExceptionIfFileIsNotJava(@TempDir final Path temp) throws Exception {
        final Path path = temp.resolve("TestNotJava.txt");
        Files.write(path, "Not Java".getBytes());
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new JavaTestCode(path).all()
        );
    }

}
