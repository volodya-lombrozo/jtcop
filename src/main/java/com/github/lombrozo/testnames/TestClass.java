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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * The bulk of test cases.
 *
 * @since 0.1.0
 */
public interface TestClass {

    /**
     * The name of class.
     *
     * @return The name of class as string
     */
    String name();

    /**
     * All cases.
     *
     * @return All cases as collection
     */
    Collection<TestCase> all();

    class Fake implements TestClass {

        private final String name;
        private final Collection<TestCase> all;

        public Fake(TestCase... all) {
            this(Arrays.asList(all));
        }

        Fake(final Collection<TestCase> all) {
            this("FakeClass", all);
        }

        Fake(final String name, final Collection<TestCase> all) {
            this.name = name;
            this.all = all;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public Collection<TestCase> all() {
            return Collections.unmodifiableCollection(this.all);
        }
    }
}
