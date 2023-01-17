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

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Test cases code.
 *
 * @since 0.1.0
 */
public final class JavaTestCode implements Cases {

    /**
     * Path to java class.
     */
    private final Path path;

    /**
     * Ctor.
     *
     * @param path Path to the class
     */
    public JavaTestCode(final Path path) {
        this.path = path;
    }

    @Override
    public Collection<TestCase> all() {
        try {
            final CompilationUnit parse = StaticJavaParser.parse(this.path);
            final List<Node> nodes = parse.getChildNodes();
            final List<TestCase> names = new ArrayList<>(0);
            for (final Node node : nodes) {
                if (node instanceof ClassOrInterfaceDeclaration) {
                    this.checkTestMethods(
                        names,
                        (ClassOrInterfaceDeclaration) node
                    );
                }
            }
            return names;
        } catch (final IOException | ParseProblemException ex) {
            throw new IllegalStateException(
                String.format("Failed to parse Java class by path %s", this.path),
                ex
            );
        }
    }

    /**
     * Checks methods in class.
     *
     * @param names List of test cases
     * @param node The child node
     */
    private void checkTestMethods(
        final List<TestCase> names,
        final ClassOrInterfaceDeclaration node
    ) {
        for (final MethodDeclaration method : node.getMethods()) {
            if (JavaTestCode.isTest(method)) {
                names.add(
                    new JavaParserCase(
                        node.getNameAsString(),
                        method.getNameAsString(),
                        this.path
                    )
                );
            }
        }
    }

    /**
     * Check if method test or parameterized test.
     *
     * @param method To check
     * @return Result as boolean
     */
    private static boolean isTest(final MethodDeclaration method) {
        return !method.isPrivate()
            && (
            method.isAnnotationPresent("Test")
            || method.isAnnotationPresent("ParameterizedTest")
            );
    }
}
