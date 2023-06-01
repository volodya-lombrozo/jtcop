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
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;

/**
 * Assertion of JUnit.
 *
 * @since 0.1.15
 */
final class AssertionOfJUnit implements ParsedAssertion {

    /**
     * The method call.
     */
    private final MethodCallExpr call;

    /**
     * The allowed methods.
     */
    private final Set<String> allowed;

    /**
     * Constructor.
     * @param method The method call.
     */
    AssertionOfJUnit(final MethodCallExpr method) {
        this(method, AssertionOfJUnit.allowedJUnitNames());
    }

    /**
     * Constructor.
     * @param method The method call.
     * @param methods The allowed methods.
     */
    private AssertionOfJUnit(final MethodCallExpr method, final Set<String> methods) {
        this.call = method;
        this.allowed = methods;
    }

    @Override
    public boolean isAssertion() {
        return this.call.getArguments().size() > 2
            && this.allowed.contains(this.call.getName().toString());
    }

    @Override
    public Optional<String> explanation() {
        final Optional<String> result;
        final NodeList<Expression> args = this.call.getArguments();
        if (args.isEmpty() || args.size() < 3) {
            result = Optional.empty();
        } else {
            result = Optional.ofNullable(args.get(2).asStringLiteralExpr().asString());
        }
        return result;
    }

    /**
     * The allowed methods.
     * @return The allowed JUnit methods.
     */
    private static Set<String> allowedJUnitNames() {
        return Arrays.stream(Assertions.class.getMethods())
            .filter(AssertionOfJUnit::isAssertion)
            .map(Method::getName)
            .collect(Collectors.toSet());
    }

    /**
     * Is JUnit assertion.
     * @param method The method.
     * @return True if JUnit assertion.
     */
    private static boolean isAssertion(final Method method) {
        final int modifiers = method.getModifiers();
        return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers);
    }
}
