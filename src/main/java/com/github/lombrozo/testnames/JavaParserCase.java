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

import java.nio.file.Path;
import lombok.Data;

/**
 * Parser for test case.
 *
 * @since 0.1.0
 */
@Data
@SuppressWarnings("PMD.AvoidFieldNameMatchingMethodName")
final class JavaParserCase implements TestCase {

    /**
     * The class name.
     * @checkstyle MemberNameCheck (2 lines)
     */
    private final String className;

    /**
     * The name of test case.
     */
    private final String name;

    /**
     * The path.
     */
    private final Path path;

    /**
     * Ctor.
     *
     * @param className The class name
     * @param name The test case name
     * @param path The path
     * @checkstyle ParameterNameCheck (6 lines)
     */
    JavaParserCase(final String className, final String name, final Path path) {
        this.className = className;
        this.name = name;
        this.path = path;
    }

    @Override
    public String className() {
        return this.className;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Path path() {
        return this.path;
    }
}
