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

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithMembers;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.TestClass;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.cactoos.scalar.Sticky;
import org.cactoos.scalar.Unchecked;

/**
 * Test cases code.
 *
 * @since 0.1.0
 */
public final class JavaParserTestClass implements TestClass {

    /**
     * Path to java class.
     */
    private final Path path;

    /**
     * Parsed Java class.
     */
    private final Unchecked<? extends CompilationUnit> unit;

    /**
     * Ctor.
     *
     * @param file Path to the class
     */
    public JavaParserTestClass(final Path file) {
        this(file, new Sticky<>(() -> StaticJavaParser.parse(file)));
    }

    /**
     * Ctor.
     *
     * @param file Path to the class
     * @param stream Parsed Java class
     */
    JavaParserTestClass(final Path file, final InputStream stream) {
        this(file, new Sticky<>(() -> StaticJavaParser.parse(stream)));
    }

    /**
     * Ctor.
     *
     * @param path Path to the class
     * @param unit Parsed class.
     */
    private JavaParserTestClass(final Path path, final Sticky<? extends CompilationUnit> unit) {
        this.path = path;
        this.unit = new Unchecked<>(unit);
    }

    @Override
    public String name() {
        return this.path.getFileName().toString();
    }

    @Override
    public Collection<TestCase> all() {
        try {
            return this.unit.value()
                .getChildNodes()
                .stream()
                .filter(JavaParserTestClass::isTestClass)
                .flatMap(this::testCases)
                .collect(Collectors.toList());
        } catch (final UncheckedIOException | ParseProblemException ex) {
            throw new IllegalStateException(
                String.format("Failed to parse Java class by path %s", this.path),
                ex
            );
        }
    }

    @Override
    public Path path() {
        return this.path;
    }

    /**
     * Checks methods in class.
     *
     * @param node The child node.
     * @return Stream of test cases.
     */
    private Stream<JavaParserTestCase> testCases(final Node node) {
        final NodeWithMembers<ClassOrInterfaceDeclaration> clazz = (NodeWithMembers) node;
        return clazz.getMethods()
            .stream()
            .filter(JavaParserTestClass::isTest)
            .map(
                method -> new JavaParserTestCase(
                    method.getNameAsString(),
                    this.path
                )
            );
    }

    /**
     * Check whether node is class or not.
     * @param node Any node.
     * @return True if test class.
     */
    private static boolean isTestClass(final Node node) {
        return node instanceof ClassOrInterfaceDeclaration;
    }

    /**
     * Check if method test or parameterized test.
     *
     * @param method To check
     * @return Result as boolean
     */
    private static boolean isTest(final MethodDeclaration method) {
        return !method.isPrivate() && (method.isAnnotationPresent("Test")
            || method.isAnnotationPresent("ParameterizedTest"));
    }
}
