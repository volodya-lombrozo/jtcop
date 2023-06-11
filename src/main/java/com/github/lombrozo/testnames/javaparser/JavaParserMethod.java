package com.github.lombrozo.testnames.javaparser;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import java.util.stream.Stream;

final class JavaParserMethod {

    private final MethodDeclaration method;

    JavaParserMethod(final MethodDeclaration declaration) {
        this.method = declaration;
    }


    String name() {
        return this.method.getNameAsString();
    }

    MethodDeclaration asMethodDeclaration() {
        return this.method;
    }

    Stream<MethodCallExpr> statements() {
        return this.body().getStatements().stream()
            .filter(Statement::isExpressionStmt)
            .map(JavaParserMethod::toExpression)
            .filter(Expression::isMethodCallExpr)
            .map(MethodCallExpr.class::cast);
    }

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
        return statement.toExpressionStmt()
            .orElseThrow(
                () -> {
                    throw new IllegalStateException("Statement is not expression");
                }
            ).getExpression();
    }
}
