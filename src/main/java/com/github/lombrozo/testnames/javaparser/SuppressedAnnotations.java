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

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import com.github.lombrozo.testnames.RuleName;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Parser for test case.
 *
 * @since 0.1.14
 */
final class SuppressedAnnotations {

    /**
     * Class or method declaration.
     */
    private final Node node;

    /**
     * Constructor.
     * @param node Class or method declaration.
     */
    SuppressedAnnotations(final Node node) {
        this.node = node;
    }

    /**
     * Retrieves annotation values from the node.
     * @return Stream of annotation values.
     */
    Stream<String> suppressed() {
        final Stream<String> result;
        if (this.node instanceof NodeWithAnnotations<?>) {
            result = ((NodeWithAnnotations<?>) this.node)
                .getAnnotations().stream()
                .filter(Expression::isSingleMemberAnnotationExpr)
                .map(Expression::asSingleMemberAnnotationExpr)
                .flatMap(SuppressedAnnotations::annotationValue)
                .map(RuleName::new)
                .filter(RuleName::hasPrefix)
                .map(RuleName::withoutPrefix)
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
