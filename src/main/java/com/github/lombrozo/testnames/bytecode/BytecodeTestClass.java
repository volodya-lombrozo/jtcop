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

import com.github.lombrozo.testnames.JUnitExtension;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.TestClass;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Stream;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * Bytecode test class.
 *
 * @since 0.1.17
 * @todo #208:90min Implement isJUnitExtension method in BytecodeTestClass.
 *  This method should return true if the test class is a JUnit extension.
 *  For example, if the test class implements the {@link org.junit.jupiter.api.extension.Extension}
 *  interface. When this method is implemented, remove that puzzle.
 */
final class BytecodeTestClass implements TestClass {

    /**
     * Path to the test class.
     */
    private final Path file;

    /**
     * Pared class.
     */
    private final CtClass klass;

    /**
     * Constructor.
     * @param path Path to the test class.
     * @param clazz Pared class.
     */
    BytecodeTestClass(
        final Path path,
        final CtClass clazz
    ) {
        this.file = path;
        this.klass = clazz;
    }

    @Override
    public String name() {
        return this.klass.getSimpleName();
    }

    @Override
    public Collection<TestCase> all() {
        return Collections.emptyList();
    }

    @Override
    public Path path() {
        return this.file;
    }

    @Override
    public Collection<String> suppressed() {
        return Collections.emptyList();
    }

    @Override
    public boolean isJUnitExtension() {
        try {
            return Stream.concat(
                    Arrays.stream(this.klass.getInterfaces()),
                    Stream.of(this.klass.getSuperclass())
                )
                .map(CtClass::getName)
                .map(JUnitExtension::new)
                .anyMatch(JUnitExtension::isJUnitExtension);
        } catch (final NotFoundException ex) {
            throw new IllegalStateException(
                String.format(
                    "Can't get interfaces or parent of class %s",
                    this.klass.getName()
                ),
                ex
            );
        }
    }
}
