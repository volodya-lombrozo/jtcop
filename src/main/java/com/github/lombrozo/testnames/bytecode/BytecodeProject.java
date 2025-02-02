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

import com.github.lombrozo.testnames.ProductionClass;
import com.github.lombrozo.testnames.Project;
import com.github.lombrozo.testnames.TestClass;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Bytecode project.
 * Implementation of {@link Project} interface.
 *
 * @since 0.1.17
 */
public final class BytecodeProject implements Project {

    /**
     * Production classes path.
     */
    private final Path classes;

    /**
     * Test classes path.
     */
    private final Path tests;

    /**
     * Constructor.
     * @param classes Production classes path.
     * @param tests Test classes path.
     */
    public BytecodeProject(final File classes, final File tests) {
        this(classes.toPath(), tests.toPath());
    }

    /**
     * Constructor.
     * @param classes Production classes path.
     * @param tests Test classes path.
     */
    public BytecodeProject(
        final Path classes,
        final Path tests
    ) {
        this.classes = classes;
        this.tests = tests;
    }

    @Override
    public Collection<ProductionClass> productionClasses() {
        final Collection<ProductionClass> res;
        if (Files.exists(this.classes)) {
            try (Stream<Path> stream = Files.walk(this.classes)) {
                res = stream.map(BytecodeClass::new)
                    .filter(BytecodeClass::isClass)
                    .map(BytecodeClass::toProductionClass)
                    .collect(Collectors.toList());
            } catch (final IOException ex) {
                throw new IllegalStateException(
                    String.format("Can't read production classes from %s", this.classes),
                    ex
                );
            }
        } else {
            res = Collections.emptyList();
        }
        return res;
    }

    @Override
    public Collection<TestClass> testClasses() {
        final Collection<TestClass> result;
        if (Files.exists(this.tests)) {
            try (Stream<Path> stream = Files.walk(this.tests)) {
                result = stream.map(BytecodeClass::new)
                    .filter(BytecodeClass::isClass)
                    .map(BytecodeClass::toTest)
                    .collect(Collectors.toList());
            } catch (final IOException ex) {
                throw new IllegalStateException(
                    String.format("Can't read test classes from %s", this.tests),
                    ex
                );
            }
        } else {
            result = Collections.emptyList();
        }
        return result;
    }
}
