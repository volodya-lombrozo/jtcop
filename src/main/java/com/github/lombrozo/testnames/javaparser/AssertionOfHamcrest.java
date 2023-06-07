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

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

/**
 * Assertion of Hamcrest.
 *
 * @since 0.1.15
 */
public final class AssertionOfHamcrest implements ParsedAssertion {

    /**
     * The explanation of the assertion.
     * The message that can't be parsed. It could be either a constant, or a method call.
     * @todo #160:30min UNKNOWN_MESSAGE constant duplication.
     *  The UNKNOWN_MESSAGE constant is duplicated in AssertionOfJUnit and AssertionOfHamcrest.
     *  It should be moved to the ParsedAssertion interface or to the separate class.
     *  When it's done, remove this puzzle.
     */
    private static final String UNKNOWN_MESSAGE =
        "Unknown message. The message will be known only in runtime";

    /**
     * The method call.
     */
    private final MethodCallExpr method;

    /**
     * The allowed method names.
     */
    private final Set<String> allowed;

    /**
     * Ctor.
     * @param call The method call.
     */
    AssertionOfHamcrest(final MethodCallExpr call) {
        this.method = call;
        this.allowed = Collections.singleton("assertThat");
    }

    @Override
    public boolean isAssertion() {
        return this.allowed.contains(this.method.getName().toString());
    }

    @Override
    public Optional<String> explanation() {
        final Optional<String> result;
        final NodeList<Expression> arguments = this.method.getArguments();
        final Optional<Expression> first = arguments.getFirst();
        if (arguments.size() > 2 && first.isPresent()) {
            result = AssertionOfHamcrest.message(first.get());
        } else {
            result = Optional.empty();
        }
        return result;
    }

    /**
     * Retrieves message from Hamcrest expression.
     * @param expression Hamcrest expression.
     * @return Optional message.
     */
    private static Optional<String> message(final Expression expression) {
        final Optional<String> result;
        if (expression.isStringLiteralExpr()) {
            result = Optional.of(expression.asStringLiteralExpr().getValue());
        } else if (expression.isNameExpr() || expression.isMethodCallExpr()) {
            result = Optional.of(AssertionOfHamcrest.UNKNOWN_MESSAGE);
        } else {
            result = Optional.empty();
        }
        return result;
    }
}
