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
package com.github.lombrozo.testnames.javaparser;

import com.github.javaparser.ast.expr.Expression;
import java.util.Optional;

/**
 * Sting expression of Java Parser.
 * Utility class for working with JavaParser library.
 * Extracts string expression from JavaParser expression.
 *
 * @since 0.1.15
 */
class StringExpression {

    /**
     * Java Parser expression.
     */
    private final Expression expr;

    /**
     * Constructor.
     * @param expression Java Parser expression.
     */
    StringExpression(final Expression expression) {
        this.expr = expression;
    }

    /**
     * Get string literal from Expression if possible.
     * @return String expression.
     */
    Optional<String> asString() {
        final Optional<String> result;
        if (this.expr.isStringLiteralExpr()) {
            result = Optional.of(this.expr.asStringLiteralExpr().asString());
        } else if (
            this.expr.isNameExpr()
                || this.expr.isMethodCallExpr()
                || this.expr.isFieldAccessExpr()
        ) {
            result = new UnknownMessage().message();
        } else {
            result = Optional.empty();
        }
        return result;
    }
}
