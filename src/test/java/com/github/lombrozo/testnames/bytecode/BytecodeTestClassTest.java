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

import com.github.lombrozo.testnames.TestClass;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test case for {@link BytecodeTestClass}.
 *
 * @since 0.1.17
 */
class BytecodeTestClassTest {

    @Test
    void checksIfBytecodeIsJUnitExtension(@TempDir final Path temp) throws Exception {
        final ResourceOf resource = new ResourceOf("generated/OnlineCondition.class");
        Files.write(temp.resolve("OnlineCondition.class"), new BytesOf(resource).asBytes());
        final TestClass test = new BytecodeProject(temp, temp)
            .testClasses()
            .iterator()
            .next();
        MatcherAssert.assertThat(
            String.format(
                "We expected that test class will be JUnit extension, but wasn't: %s",
                test
            ),
            test.isJUnitExtension(),
            Matchers.is(true)
        );
    }

    @Test
    void checksIfBytecodeIsNotJUnitExtension(@TempDir final Path temp) throws Exception {
        final ResourceOf resource = new ResourceOf("generated/RuleTest.class");
        Files.write(temp.resolve("RuleTest.class"), new BytesOf(resource).asBytes());
        final TestClass test = new BytecodeProject(temp, temp)
            .testClasses()
            .iterator()
            .next();
        MatcherAssert.assertThat(
            String.format(
                "We expected that test class will not be JUnit extension, but was %s",
                test
            ),
            test.isJUnitExtension(),
            Matchers.is(false)
        );
    }

}