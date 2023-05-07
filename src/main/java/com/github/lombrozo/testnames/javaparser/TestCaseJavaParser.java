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

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.javaparser.ast.nodeTypes.NodeWithMembers;
import com.github.lombrozo.testnames.TestCase;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Data;

/**
 * Parser for test case.
 *
 * @since 0.1.0
 * @todo #110:30min Implement suppressed method in TestCaseJavaParser.
 *  The method should return a list of suppressed rules. Similar to the method in TestClass.
 *  Also we have to add tests for the method.
 */
@Data
final class TestCaseJavaParser implements TestCase {

    /**
     * The name of test case.
     */
    private final String title;

    /**
     * Path to java class.
     */
    private final Path file;

    /**
     * Ctor.
     *
     * @param name The test case name
     * @param path Path to java class
     * @checkstyle ParameterNameCheck (6 lines)
     */
    TestCaseJavaParser(final String name, final Path path) {
        this.title = name;
        this.file = path;
    }

    @Override
    public String name() {
        return this.title;
    }

    @Override
    public Collection<String> suppressed() {
        try {
            List<String> res = new ArrayList<>();
            final CompilationUnit unit = StaticJavaParser.parse(this.file);
            unit.getChildNodes().stream().filter(
                node -> node instanceof TypeDeclaration<?>
            ).forEach(
                m -> {
                    final NodeWithMembers<TypeDeclaration<?>> node = (NodeWithMembers<TypeDeclaration<?>>) m;
                    final List<MethodDeclaration> methods = node.getMethods();
                    for (final MethodDeclaration method : methods) {
                        if (method.getNameAsString().equals(this.title)) {
                            res.addAll(
                                TestCaseJavaParser.annotations(method)
                                    .collect(Collectors.toSet()));
                        }
                    }
                });
            return res;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Retrieves annotation values from the node.
     * @param node JavaParser node.
     * @return Stream of annotation values.
     */
    private static Stream<String> annotations(final Node node) {
        final Stream<String> result;
        if (node instanceof NodeWithAnnotations<?>) {
            result = ((NodeWithAnnotations<?>) node)
                .getAnnotations().stream()
                .filter(Expression::isSingleMemberAnnotationExpr)
                .map(Expression::asSingleMemberAnnotationExpr)
                .flatMap(TestCaseJavaParser::annotationValue)
                .filter(TestCaseJavaParser::isJtcopAnnotation)
                .map(ann -> ann.substring(6))
                .collect(Collectors.toList()).stream();
        } else {
            result = Stream.empty();
        }
        return result;
    }

    /**
     * Retrieves annotation value from single member expression.
     * @param expr Single member expression.
     * @return Annotation value.
     */
    private static Stream<String> annotationValue(final SingleMemberAnnotationExpr expr) {
        final Stream<String> result;
        final Expression value = expr.getMemberValue();
        if (value.isStringLiteralExpr()) {
            result = Stream.of(value.asStringLiteralExpr().asString());
        } else if (value.isArrayInitializerExpr()) {
            result = value.asArrayInitializerExpr().getValues()
                .stream()
                .filter(Expression::isStringLiteralExpr)
                .map(Expression::asStringLiteralExpr)
                .map(StringLiteralExpr::asString);
        } else {
            result = Stream.empty();
        }
        return result;
    }

    /**
     * Checks whether annotation is related to Jtcop.
     * @param value Annotation value.
     * @return True if Jtcop annotation.
     */
    private static boolean isJtcopAnnotation(final String value) {
        return value.toUpperCase(Locale.ROOT).startsWith("JTCOP.");
    }

}
