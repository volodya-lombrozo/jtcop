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

import com.github.lombrozo.testnames.JUnitExtension;
import com.github.lombrozo.testnames.TestClassCharacteristics;

/**
 * JavaParser implementation of {@link TestClassCharacteristics}.
 *
 * @since 0.1.19
 */
final class JavaParserCharacteristics implements TestClassCharacteristics {

    /**
     * JavaParser parsed class.
     */
    private final JavaParserClass klass;

    /**
     * Constructor.
     * @param klass JavaParser parsed class.
     */
    JavaParserCharacteristics(final JavaParserClass klass) {
        this.klass = klass;
    }

    @Override
    public boolean isJUnitExtension() {
        return this.klass.parents().stream()
            .map(JUnitExtension::new)
            .anyMatch(JUnitExtension::isJUnitExtension);
    }

    @Override
    public boolean isIntegrationTest() {
        return this.klass.pckg()
            .map(pckg -> pckg.endsWith(".it") || "it".equals(pckg))
            .orElse(false);
    }

    @Override
    public int numberOfTests() {
        return (int) this.klass.methods(new TestsOnly()).count();
    }

    @Override
    public int numberOfMethods() {
        return (int) this.klass.methods().count();
    }

    @Override
    public String parent() {
        return this.klass.parents()
            .stream()
            .findFirst()
            .orElseThrow(
                () -> new IllegalStateException(
                    String.format(
                        "Can't get parent of class %s",
                        this.klass
                    )
                )
            ).getName();
    }
}
