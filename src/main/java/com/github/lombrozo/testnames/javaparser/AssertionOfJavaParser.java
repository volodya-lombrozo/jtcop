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

import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.lombrozo.testnames.Assertion;
import java.util.Optional;

/**
 * The assertion of the test method.
 *
 * @since 0.1.15
 */
public final class AssertionOfJavaParser implements Assertion {

    /**
     * The method call.
     */
    private final MethodCallExpr call;

    /**
     * Ctor.
     * @param method The method call.
     */
    AssertionOfJavaParser(final MethodCallExpr method) {
        this.call = method;
    }

    @Override
    public Optional<String> explanation() {
        final Optional<String> result;
        final ParsedAssertion assertion = new AssertionOfJUnit(this.call);
        if (assertion.isAssertion()) {
            result = assertion.explanation();
        } else {
            result = Optional.empty();
        }
        return result;
    }
}
