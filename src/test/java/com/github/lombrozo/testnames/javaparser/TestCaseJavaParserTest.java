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

import java.nio.file.Path;
import java.nio.file.Paths;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TestCaseJavaParser}.
 *
 * @since 0.2
 */
class TestCaseJavaParserTest {

    @Test
    void convertsToString() {
        MatcherAssert.assertThat(
            new TestCaseJavaParser("name", Paths.get(".")).toString(),
            Matchers.equalTo("JavaParserTestCase(title=name, file=.)")
        );
    }

    @Test
    void hasTheSameHashCode() {
        final String name = "nm";
        final Path path = Paths.get("./.");
        MatcherAssert.assertThat(
            new TestCaseJavaParser(name, path).hashCode(),
            Matchers.is(new TestCaseJavaParser(name, path).hashCode())
        );
    }

    @Test
    void equalsIfBothTheSame() {
        final String name = "nme";
        final Path path = Paths.get("././.");
        MatcherAssert.assertThat(
            new TestCaseJavaParser(name, path),
            Matchers.equalTo(new TestCaseJavaParser(name, path))
        );
    }
}