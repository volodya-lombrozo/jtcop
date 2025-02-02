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

import com.github.javaparser.ast.body.MethodDeclaration;
import java.util.function.Predicate;

/**
 * Tests only predicate.
 * The predicate for filtering only test methods.
 *
 * @since 0.1.15
 */
@SuppressWarnings({
    "PMD.JUnit4TestShouldUseTestAnnotation",
    "PMD.JUnitTestsShouldIncludeAssert",
    "PMD.TestClassWithoutTestCases"
})
final class TestsOnly implements Predicate<MethodDeclaration> {

    @Override
    public boolean test(final MethodDeclaration declaration) {
        return !declaration.isPrivate() && TestsOnly.withTestAnnotation(declaration);
    }

    /**
     * Check if the method has a test annotation.
     *
     * @param declaration The method declaration.
     * @return True if the method has a test annotation.
     */
    private static boolean withTestAnnotation(final MethodDeclaration declaration) {
        return declaration.isAnnotationPresent("Test")
            || declaration.isAnnotationPresent("ParameterizedTest");
    }
}
