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

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithBody;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * JavaParser method.
 * Utility class for working with JavaParser library.
 *
 * @since 0.1.15
 */
@ToString
@EqualsAndHashCode
final class JavaParserMethod {

    /**
     * The method declaration.
     */
    private final MethodDeclaration method;

    /**
     * Ctor.
     * @param declaration Method declaration.
     */
    JavaParserMethod(final MethodDeclaration declaration) {
        this.method = declaration;
    }

    /**
     * Get method name.
     * @return Method name.
     */
    String name() {
        return this.method.getNameAsString();
    }

    /**
     * Get method declaration.
     * @return Method declaration.
     */
    MethodDeclaration asMethodDeclaration() {
        return this.method;
    }

    /**
     * Get all method statements.
     * @return Method statements.
     */
    Stream<MethodCallExpr> statements() {
        return JavaParserMethod.flatStatements(this.body().getStatements().stream())
            .filter(Statement::isExpressionStmt)
            .map(JavaParserMethod::toExpression)
            .filter(Expression::isMethodCallExpr)
            .map(MethodCallExpr.class::cast);
    }

    /**
     * This method unrolls inner method statements.
     * In other words, if a statement is a type of block that contains inner statements,
     * we simply unroll all these statements and return them as a flat stream.
     * @param stmts Statements to unroll.
     * @return Stream of statements.
     */
    @SuppressWarnings("PMD.CognitiveComplexity")
    private static Stream<Statement> flatStatements(final Stream<? extends Statement> stmts) {
        return stmts.map(JavaParserMethod::statements).flatMap(Collection::stream);
    }

    /**
     * Extract statements from node.
     * @param node Node to extract statements from.
     * @return List of statements.
     */
    private static List<Statement> statements(final Node node) {
        final List<Statement> res = new ArrayList<>(0);
        if (node instanceof Statement) {
            res.add((Statement) node);
        }
        node.getChildNodes()
            .stream()
            .map(JavaParserMethod::statements)
            .forEach(res::addAll);
        return res;
    }

    /**
     * Get method body.
     * @return Method body.
     */
    private BlockStmt body() {
        return this.method.getBody().orElseThrow(
            () -> new IllegalStateException(
                String.format(
                    "The method %s has to have body, otherwise it is not a method",
                    this.method.getNameAsString()
                )
            )
        );
    }

    /**
     * Convert statement to expression.
     * @param statement Statement
     * @return Expression
     */
    private static Expression toExpression(final Statement statement) {
        return statement.toExpressionStmt().orElseThrow(
            () -> new IllegalStateException("Statement is not expression")
        ).getExpression();
    }
}
