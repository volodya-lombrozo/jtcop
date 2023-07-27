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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.bytes.BytesOf;
import org.cactoos.bytes.UncheckedBytes;
import org.cactoos.io.ResourceOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test case for {@link BytecodeTestClassCharacteristics}.
 *
 * @since 0.1.19
 */
final class BytecodeTestClassCharacteristicsTest {

    @Test
    void createsSuccessfully(@TempDir final Path tmp) throws IOException {
        BytecodeTestClassCharacteristicsTest.saveTestClassBinary(tmp);
        MatcherAssert.assertThat(
            "Characteristics of the test class shouldn't be empty",
            new BytecodeProject(tmp, tmp)
                .testClasses()
                .iterator()
                .next()
                .characteristics(),
            Matchers.notNullValue()
        );
    }

    @Test
    void retrievesNumberOfTests(@TempDir final Path tmp) throws IOException {
        BytecodeTestClassCharacteristicsTest.saveTestClassBinary(tmp);
        MatcherAssert.assertThat(
            "We expect that RuleTest.class will have exactly one test",
            new BytecodeProject(tmp, tmp)
                .testClasses()
                .iterator()
                .next()
                .characteristics()
                .numberOfTests(),
            Matchers.equalTo(1)
        );
    }

    @Test
    void retrievesNumberOfMethods(@TempDir final Path tmp) throws IOException {
        BytecodeTestClassCharacteristicsTest.saveTestClassBinary(tmp);
        MatcherAssert.assertThat(
            "We expect that RuleTest.class will have exactly one method",
            new BytecodeProject(tmp, tmp)
                .testClasses()
                .iterator()
                .next()
                .characteristics()
                .numberOfMethods(),
            Matchers.equalTo(1)
        );
    }

    @Test
    void checksIfBytecodeIsNotJUnitExtension(@TempDir final Path tmp) throws IOException {
        BytecodeTestClassCharacteristicsTest.saveTestClassBinary(tmp);
        MatcherAssert.assertThat(
            "We expect that RuleTest.class is not a JUnit extension",
            new BytecodeProject(tmp, tmp)
                .testClasses()
                .iterator()
                .next()
                .characteristics()
                .isJUnitExtension(),
            Matchers.equalTo(false)
        );
    }

    @Test
    void checksIfBytecodeIsJUnitExtension(@TempDir final Path tmp) throws IOException {
        final ResourceOf resource = new ResourceOf("generated/OnlineCondition.class");
        Files.write(
            tmp.resolve("OnlineCondition.class"),
            new UncheckedBytes(new BytesOf(resource)).asBytes()
        );
        MatcherAssert.assertThat(
            "We expect that OnlineCondition.class a JUnit extension or condition",
            new BytecodeProject(tmp, tmp)
                .testClasses()
                .iterator()
                .next()
                .characteristics()
                .isJUnitExtension(),
            Matchers.equalTo(true)
        );
    }

    @Test
    void checksIfClassIsIntegrationTest(@TempDir final Path temp) throws IOException {
        final ResourceOf resource = new ResourceOf("generated/IntegrationTest.class");
        Files.write(
            temp.resolve("IntegrationTest.class"),
            new UncheckedBytes(new BytesOf(resource)).asBytes()
        );
        MatcherAssert.assertThat(
            "We expect that IntegrationTest.class an integration test",
            new BytecodeProject(temp, temp)
                .testClasses()
                .iterator()
                .next()
                .characteristics()
                .isIntegrationTest(),
            Matchers.equalTo(true)
        );
    }

    @Test
    void checksIfClassIsNotIntegrationTest(@TempDir final Path temp) throws IOException {
        BytecodeTestClassCharacteristicsTest.saveTestClassBinary(temp);
        MatcherAssert.assertThat(
            "We expect that a plain class is not an integration test",
            new BytecodeProject(temp, temp)
                .testClasses()
                .iterator()
                .next()
                .characteristics()
                .isIntegrationTest(),
            Matchers.is(false)
        );
    }

    /**
     * Save test class binary for test purposes.
     * @param tmp Where to save the binary.
     * @throws IOException if something happened.
     */
    private static void saveTestClassBinary(final Path tmp) throws IOException {
        final ResourceOf resource = new ResourceOf("generated/RuleTest.class");
        Files.write(
            tmp.resolve("RuleTest.class"),
            new UncheckedBytes(new BytesOf(resource)).asBytes()
        );
    }
}
