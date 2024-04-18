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
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithBody;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
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
    private static Stream<Statement> flatStatements(final Stream<? extends Statement> stmts) {
        return stmts.flatMap(
            statement -> {
                final Stream<Statement> result;
                if (statement instanceof NodeWithBody) {
                    result = JavaParserMethod.flatStatements(
                        ((NodeWithBody<?>) statement).getBody()
                            .asBlockStmt()
                            .getStatements()
                            .stream()
                    );
                } else if (statement instanceof IfStmt) {
                    final IfStmt ifstmt = (IfStmt) statement;
                    final Collection<Statement> statements = new ArrayList<>(3);
                    if (ifstmt.hasThenBlock()) {
                        statements.add(ifstmt.getThenStmt());
                    }
                    if (ifstmt.hasElseBlock()) {
                        statements.add(
                            ifstmt.getElseStmt()
                                .orElseThrow(
                                    () -> new IllegalStateException(
                                        "Else block is absent. It's impossible")
                                )
                        );
                    }
                    result = JavaParserMethod.flatStatements(statements.stream());
                } else if (statement.isBlockStmt()) {
                    result = JavaParserMethod.flatStatements(
                        statement.asBlockStmt().getStatements().stream()
                    );
                } else if (statement.isExpressionStmt()) {
                    final Expression expression = statement.asExpressionStmt().getExpression();
                    if (expression.isMethodCallExpr()) {
                        result = Stream.of(statement);
                    } else {
                        result = JavaParserMethod.flatStatements(expression);
                    }
                } else {
                    result = Stream.of(statement);
                }
                return result;
            }
        );
    }

    static Stream<Statement> flatStatements(final Expression expression) {
        List<Statement> statements = new ArrayList<>(0);
        List<Expression> expressions = new ArrayList<>(0);
        List<VariableDeclarator> declarators = new ArrayList<>(0);
        if (expression.isLambdaExpr()) {
            return JavaParserMethod.flatStatements(Stream.of(expression.asLambdaExpr().getBody()));
        }
        for (final Node node : expression.getChildNodes()) {
            if (node instanceof Statement) {
                statements.add((Statement) node);
            } else if (node instanceof VariableDeclarator) {
                declarators.add((VariableDeclarator) node);
            }
        }
        final Stream<Statement> a = declarators.stream()
            .map(VariableDeclarator::getInitializer)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .flatMap(JavaParserMethod::flatStatements);
        return Stream.concat(
            Stream.concat(
                a,
                statements.stream()
            ),
            expressions.stream().flatMap(JavaParserMethod::flatStatements)
        );
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
