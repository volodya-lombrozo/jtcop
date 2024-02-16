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

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;

/**
 * Assertion of JUnit.
 *
 * @since 0.1.15
 */
final class AssertionOfJUnit implements ParsedAssertion {

    /**
     * Special assertions that we consider as assertions with messages.
     */
    private static final String[] SPECIAL = {"assertAll", "fail"};

    /**
     * The `assert` regex.
     */
    private static final Pattern ASSERT = Pattern.compile("assert");

    /**
     * The method call.
     */
    private final MethodCallExpr call;

    /**
     * The allowed methods.
     * The key is the method name, the value is the minimum number of arguments.
     */
    private final Map<String, Integer> allowed;

    /**
     * Constructor.
     *
     * @param method The method call.
     */
    AssertionOfJUnit(final MethodCallExpr method) {
        this(method, AssertionOfJUnit.allowedJUnitNames());
    }

    /**
     * Constructor.
     *
     * @param method The method call.
     * @param methods The allowed methods.
     */
    private AssertionOfJUnit(final MethodCallExpr method, final Map<String, Integer> methods) {
        this.call = method;
        this.allowed = methods;
    }

    @Override
    public boolean isAssertion() {
        return this.allowed.containsKey(this.call.getName().toString());
    }

    @Override
    public Optional<String> explanation() {
        final Optional<String> result;
        final NodeList<Expression> args = this.call.getArguments();
        final Optional<Expression> last = args.getLast();
        final Integer min = this.allowed.get(this.call.getName().toString());
        if (Arrays.asList(AssertionOfJUnit.SPECIAL).contains(this.call.getName().toString())) {
            result = new UnknownMessage().message();
        } else if (min < args.size() && last.isPresent()) {
            result = new StingExpression(last.get()).asString();
        } else {
            result = Optional.empty();
        }
        return result;
    }

    @Override
    public boolean isLineHitter() {
        final String cut = AssertionOfJUnit.ASSERT
            .matcher(this.call.getNameAsString())
            .replaceAll("")
            .toLowerCase(Locale.ROOT);
        return this.containsLineHitter("true", cut)
            || this.containsLineHitter("false", cut);
    }

    /**
     * Check on simple line hitter.
     *
     * @param type Type of hitter
     * @param cut Rest of assertion
     * @return True if rest of assertion equals to type
     */
    private boolean containsLineHitter(final String type, final String cut) {
        return type.equalsIgnoreCase(cut) && this.call.getArguments()
            .stream()
            .anyMatch(arg -> cut.equals(arg.toString()));
    }

    /**
     * The allowed methods.
     *
     * @return The allowed JUnit methods.
     */
    private static Map<String, Integer> allowedJUnitNames() {
        return Arrays.stream(Assertions.class.getMethods())
            .filter(AssertionOfJUnit::isAssertion)
            .collect(Collectors.toMap(Method::getName, Method::getParameterCount, Math::min));
    }

    /**
     * Is JUnit assertion.
     *
     * @param method The method.
     * @return True if JUnit assertion.
     */
    private static boolean isAssertion(final Method method) {
        final int modifiers = method.getModifiers();
        return Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers);
    }

}
