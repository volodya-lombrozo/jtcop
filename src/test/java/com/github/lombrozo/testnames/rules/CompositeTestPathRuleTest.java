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

package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.WrongTestName;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test case for {@link com.github.lombrozo.testnames.rules.CompositeTestPathRule}.
 *
 * @since 0.1.0
 */
final class CompositeTestPathRuleTest {

    @Test
    void checksSeveralFilesInDirectorySuccessfully(@TempDir final Path temp) throws Exception {
        Files.write(
            temp.resolve("Test1.java"),
            new BytesOf(
                new ResourceOf("TestParameterized.java")
            ).asBytes()
        );
        Files.write(
            temp.resolve("Test2.java"),
            new BytesOf(
                new ResourceOf("TestSimple.java")
            ).asBytes()
        );
        try {
            new CompositeTestPathRule(temp).complaints();
        } catch (final WrongTestName ex) {
            Assertions.fail(ex);
        }
    }

    @Test
    void checksBrokenClass(@TempDir final Path temp) throws Exception {
        Files.write(
            temp.resolve("Broken.java"),
            "class BrokenClass {".getBytes(StandardCharsets.UTF_8)
        );
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new CompositeTestPathRule(temp).complaints()
        );
    }

    @Test
    void ignoresDirectoriesAndNonJavaFiles(@TempDir final Path temp) throws Exception {
        Files.write(
            temp.resolve("some.txt"),
            "Simple text".getBytes(StandardCharsets.UTF_8)
        );
        Files.createDirectories(temp.resolve("subdir").resolve("deep"));
        try {
            new CompositeTestPathRule(temp).complaints();
        } catch (final WrongTestName ex) {
            Assertions.fail(ex);
        }
    }

    @Test
    void failsIfTestNameIsWrong(@TempDir final Path temp) throws Exception {
        Files.write(
            temp.resolve("Test3.java"),
            new BytesOf(
                new ResourceOf("TestWrongName.java")
            ).asBytes()
        );
        Assertions.assertThrows(
            WrongTestName.class,
            () -> new CompositeTestPathRule(temp).complaints()
        );
    }
}
