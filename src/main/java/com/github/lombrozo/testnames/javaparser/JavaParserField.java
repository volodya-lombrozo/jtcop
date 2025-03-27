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

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.lombrozo.testnames.Field;
import java.util.Optional;

/**
 * JavaParser field.
 * @since 1.4
 */
final class JavaParserField implements Field {

    /**
     * Field declaration in the source code.
     */
    private final FieldDeclaration field;

    /**
     * Constructor.
     * @param field Field declaration in the source code.
     */
    JavaParserField(final FieldDeclaration field) {
        this.field = field;
    }

    @Override
    public String name() {
        return this.declarator(this.field)
            .map(VariableDeclarator::getName)
            .map(SimpleName::asString)
            .orElseThrow(() -> new IllegalStateException("Field name is not found"));
    }

    @Override
    public boolean isStatic() {
        return this.field.getModifiers().stream()
            .anyMatch(modifier -> modifier.getKeyword() == Modifier.Keyword.STATIC);
    }

    /**
     * Find variable declarator.
     * @param node Node to search in.
     * @return Variable declarator.
     */
    private Optional<VariableDeclarator> declarator(final Node node) {
        final Optional<VariableDeclarator> result;
        if (node instanceof VariableDeclarator) {
            result = Optional.of((VariableDeclarator) node);
        } else {
            result = node.getChildNodes()
                .stream()
                .map(this::declarator)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
        }
        return result;
    }
}
