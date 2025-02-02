/*
 * MIT License
 *
 * Copyright (c) 2022-2025 Volodya Lombrozo
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
import com.github.lombrozo.testnames.TestClassCharacteristics;
import java.util.Arrays;
import java.util.stream.Stream;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

/**
 * Test class characteristics from bytecode.
 *
 * @since 0.1.19
 */
final class BytecodeTestClassCharacteristics implements TestClassCharacteristics {

    /**
     * Parsed class.
     */
    private final CtClass klass;

    /**
     * Constructor.
     * @param klass Parsed class.
     */
    BytecodeTestClassCharacteristics(final CtClass klass) {
        this.klass = klass;
    }

    @Override
    public boolean isJUnitExtension() {
        try {
            return Stream
                .concat(
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

    @Override
    public boolean isIntegrationTest() {
        final String pckg = this.klass.getPackageName();
        return pckg.endsWith(".it") || "it".equals(pckg);
    }

    @Override
    public int numberOfTests() {
        return (int) Arrays.stream(this.klass.getMethods())
            .filter(BytecodeTestClassCharacteristics::isTest)
            .count();
    }

    @Override
    public int numberOfMethods() {
        return this.klass.getDeclaredMethods().length;
    }

    @Override
    public String parent() {
        try {
            return this.klass.getSuperclass().getName();
        } catch (final NotFoundException exception) {
            throw new IllegalStateException(
                String.format(
                    "Can't get parent of class %s",
                    this.klass.getName()
                ),
                exception
            );
        }
    }

    /**
     * Checks whether a method is test-method.
     * @param method To check.
     * @return True if the method is test-method.
     */
    private static boolean isTest(final CtMethod method) {
        return method.hasAnnotation(Test.class)
            || method.hasAnnotation(ParameterizedTest.class);
    }
}

