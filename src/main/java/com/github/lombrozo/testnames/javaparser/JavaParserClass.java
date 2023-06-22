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

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithMembers;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JavaParser class.
 * The utility class for working with JavaParser library.
 *
 * @since 0.1.15
 */
final class JavaParserClass {

    /**
     * Parsed Java class.
     */
    private final NodeWithMembers<TypeDeclaration<?>> klass;

    /**
     * Ctor.
     * @param stream Input stream with java class.
     */
    JavaParserClass(final InputStream stream) {
        this(StaticJavaParser.parse(stream));
    }

    /**
     * Ctor.
     * @param unit Compilation unit.
     */
    JavaParserClass(final CompilationUnit unit) {
        this(JavaParserClass.fromCompilation(unit));
    }

    /**
     * Ctor.
     * @param node Node with class.
     */
    @SuppressWarnings("unchecked")
    private JavaParserClass(final Node node) {
        this((NodeWithMembers<TypeDeclaration<?>>) node);
    }

    /**
     * Ctor.
     * @param unit Compilation unit.
     */
    private JavaParserClass(final NodeWithMembers<TypeDeclaration<?>> unit) {
        this.klass = unit;
    }

    /**
     * Annotations of class.
     * @return Annotations of class.
     */
    SuppressedAnnotations annotations() {
        return new SuppressedAnnotations((Node) this.klass);
    }

    /**
     * Methods of class.
     * @param filters Filters for methods.
     * @return Methods of class.
     */
    @SafeVarargs
    final Stream<JavaParserMethod> methods(final Predicate<MethodDeclaration>... filters) {
        return this.klass.getMethods()
            .stream()
            .filter(method -> Stream.of(filters).allMatch(filter -> filter.test(method)))
            .map(JavaParserMethod::new);
    }

    /**
     * Prestructor of class.
     * @param unit Compilation unit.
     * @return Node with class.
     */
    private static Node fromCompilation(final CompilationUnit unit) {
        final Queue<Node> all = unit.getChildNodes()
            .stream()
            .filter(node -> node instanceof TypeDeclaration<?>)
            .collect(Collectors.toCollection(LinkedList::new));
        if (all.isEmpty()) {
            throw new IllegalStateException(
                String.format(
                    "Compilation unit '%s' has contain at least one class",
                    unit
                )
            );
        }
        return all.element();
    }

}
