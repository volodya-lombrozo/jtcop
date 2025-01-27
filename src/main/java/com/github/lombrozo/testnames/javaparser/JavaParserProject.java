/*
 * MIT License
 *
 * Copyright (c) 2022-2024 Volodya Lombrozo
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

import com.github.javaparser.resolution.SymbolResolver;
import com.github.javaparser.resolution.TypeSolver;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ClassLoaderTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.CombinedTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.JavaParserTypeSolver;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;
import com.github.lombrozo.testnames.ProductionClass;
import com.github.lombrozo.testnames.Project;
import com.github.lombrozo.testnames.TestClass;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The project that uses JavaParser.
 *
 * @since 0.2
 */
public final class JavaParserProject implements Project {

    /**
     * The main path where production classes are placed.
     */
    private final Path main;

    /**
     * The test path where test classes are placed.
     */
    private final Path test;

    /**
     * The rules that have to be excluded from execution.
     */
    private final Collection<String> exclusions;

    /**
     * Ctor.
     *
     * @param main The main path where production classes are placed.
     * @param test The test path where test classes are placed.
     * @param exclusions The rules that have to be excluded from execution.
     */
    public JavaParserProject(
        final Path main,
        final Path test,
        final Collection<String> exclusions
    ) {
        this.main = main;
        this.test = test;
        this.exclusions = exclusions;
    }

    /**
     * Ctor.
     *
     * @param main The main path where production classes are placed.
     * @param test The test path where test classes are placed.
     */
    JavaParserProject(final Path main, final Path test) {
        this(main, test, Collections.emptyList());
    }

    @Override
    public Collection<ProductionClass> productionClasses() {
        final Collection<ProductionClass> res;
        if (Files.exists(this.main)) {
            try (Stream<Path> files = Files.walk(this.main)) {
                res = files
                    .filter(Files::exists)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(JavaParserProductionClass::new)
                    .collect(Collectors.toList());
            } catch (final IOException exception) {
                throw new IllegalStateException(exception);
            }
        } else {
            res = Collections.emptyList();
        }
        return res;
    }

    @Override
    public Collection<TestClass> testClasses() {
        final Collection<TestClass> res;
        if (Files.exists(this.test)) {
            try (Stream<Path> files = Files.walk(this.test)) {
                res = files
                    .filter(Files::exists)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .filter(
                        path -> {
                            final JavaParserClass parsed = new JavaParserClass(
                                path,
                                this.projectResolver()
                            );
                            return !parsed.isAnnotation()
                                && !parsed.isInterface()
                                && !parsed.isPackageInfo();
                        }
                    )
                    .map(
                        klass -> new JavaParserTestClass(
                            klass,
                            this.projectResolver(),
                            this.exclusions
                        )
                    )
                    .collect(Collectors.toList());
            } catch (final IOException exception) {
                throw new IllegalStateException(exception);
            }
        } else {
            res = Collections.emptyList();
        }
        return res;
    }

    private SymbolResolver projectResolver() {
        final List<TypeSolver> solvers = new ArrayList(0);
        solvers.add(new ReflectionTypeSolver());
        solvers.add(new ClassLoaderTypeSolver(Thread.currentThread().getContextClassLoader()));
        if (Files.exists(this.main)) {
            solvers.add(new JavaParserTypeSolver(this.main));
        }
        if (Files.exists(this.test)) {
            solvers.add(new JavaParserTypeSolver(this.test));
        }

        return new JavaSymbolSolver(new CombinedTypeSolver(solvers.toArray(new TypeSolver[0])));
    }

    /**
     * Resolver for JavaParser.
     *
     * @return Symbol resolver.
     */
    static SymbolResolver resolver() {
        return new JavaSymbolSolver(
            new CombinedTypeSolver(
                new ReflectionTypeSolver(),
                new ClassLoaderTypeSolver(Thread.currentThread().getContextClassLoader())
            )
        );
    }

}
