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
import java.util.Optional;
import lombok.ToString;

/**
 * The assertion of the test method.
 *
 * @since 0.1.15
 * @todo #160:90min Add unit tests for AssertionOfJavaParser.
 *  For now we don't have tests for AssertionOfJava parser and don't check how different
 *  implementations like {@link AssertionOfHamcrest} and {@link AssertionOfJUnit} work together.
 *  So, it's better to test that interaction and then remove that puzzle.
 */
@ToString
public final class AssertionOfJavaParser implements ParsedAssertion {

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
        final ParsedAssertion junit = new AssertionOfJUnit(this.call);
        final ParsedAssertion hamcrest = new AssertionOfHamcrest(this.call);
        if (junit.isAssertion()) {
            result = junit.explanation();
        } else if (hamcrest.isAssertion()) {
            result = hamcrest.explanation();
        } else {
            result = Optional.empty();
        }
        return result;
    }

    @Override
    public boolean isAssertion() {
        return new AssertionOfJUnit(this.call).isAssertion()
            || new AssertionOfHamcrest(this.call).isAssertion();
    }
}
