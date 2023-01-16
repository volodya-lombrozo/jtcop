/*
 * MIT License
 *
 * Copyright (c) 2022 Volodya
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
import java.nio.file.Paths;
import lombok.Data;

/**
 * The test case.
 *
 * @since 0.1.0
 */
public interface TestCase {

    /**
     * The name of class.
     *
     * @return The name of class as string
     */
    String className();

    /**
     * The name of test case.
     *
     * @return The name of test case as string.
     */
    String name();

    /**
     * The path.
     *
     * @return Path as string.
     */
    Path path();

    /**
     * The fake test case.
     *
     * @since 0.1.0
     */
    @Data
    final class FakeCase implements TestCase {

        /**
         * The class name.
         * @checkstyle MemberNameCheck (2 lines)
         */
        private final String className;

        /**
         * The name of test.
         */
        private final String name;

        /**
         * The path.
         */
        private final Path path;

        /**
         * Ctor.
         *
         * @param name The name of test case
         */
        FakeCase(final String name) {
            this("FakeClass", name, Paths.get("."));
        }

        /**
         * Primary ctor.
         *
         * @param className The class name
         * @param name The name of test case
         * @param path The path
         * @checkstyle ParameterNameCheck (6 lines)
         */
        FakeCase(final String className, final String name, final Path path) {
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
}
