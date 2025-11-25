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

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.resolution.UnsolvedSymbolException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Assertion of Hamcrest.
 *
 * @since 0.1.15
 */
public final class AssertionOfHamcrest implements ParsedAssertion {

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
     *
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
            result = new StringExpression(first.get()).asString();
        } else if (arguments.size() == 2 && first.isPresent()) {
            final Optional<Expression> last = arguments.getLast();
            if (last.isPresent() && "boolean".equals(this.type(last.get()))) {
                result = new StringExpression(first.get()).asString();
            } else {
                result = Optional.empty();
            }
        } else {
            result = Optional.empty();
        }
        return result;
    }

    @Override
    public boolean isLineHitter() {
        final List<String> args = this.method.getArguments()
            .stream()
            .map(Expression::toString)
            .collect(Collectors.toList());
        return AssertionOfHamcrest.containsLineHitter(args);
    }

    /**
     * Checks if contains line hitter.
     *
     * @param args Assertion arguments
     * @return True if contains assertTrue(true) assertion
     */
    private static boolean containsLineHitter(final Collection<String> args) {
        return AssertionOfHamcrest.containsHitter(args, "true")
            || AssertionOfHamcrest.containsHitter(args, "false");
    }

    /**
     * Checks for the existence of line hitter by type.
     *
     * @param args All args
     * @param type Type of hitter
     * @return True if contains hitter
     */
    private static boolean containsHitter(final Collection<String> args, final String type) {
        final String shortened = String.format("equalTo(%s)", type);
        final String full = String.format("Matchers.equalTo(%s)", type);
        return (args.contains(shortened) || args.contains(full)) && args.contains(type);
    }

    /**
     * Gets type of expression.
     *
     * @param expr Expression
     * @return Type description
     */
    private String type(final Expression expr) {
        String result;
        try {
            result = expr.calculateResolvedType().describe();
        } catch (final UnsolvedSymbolException ex) {
            Logger.getLogger(this.getClass().getName())
                .warning(String.format("can't resolve type for '%s'", expr));
            result = "unresolved";
        }
        return result;
    }
}
