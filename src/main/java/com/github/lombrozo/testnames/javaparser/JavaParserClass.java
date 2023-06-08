package com.github.lombrozo.testnames.javaparser;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithMembers;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class JavaParserClass {

    private final NodeWithMembers<TypeDeclaration<?>> unit;

    JavaParserClass(final CompilationUnit unit) {
        this(JavaParserClass.fromCompilation(unit));
    }

    @SuppressWarnings("unchecked")
    private JavaParserClass(final Node node) {
        this((NodeWithMembers<TypeDeclaration<?>>) node);
    }

    private JavaParserClass(final NodeWithMembers<TypeDeclaration<?>> unit) {
        this.unit = unit;
    }

    SuppressedAnnotations annotations() {
        return new SuppressedAnnotations((Node) this.unit);
    }

    Stream<MethodDeclaration> methods(Predicate<MethodDeclaration>... filters) {
        return this.unit.getMethods()
            .stream()
            .filter(method -> Stream.of(filters).allMatch(filter -> filter.test(method)));
    }

    private static Node fromCompilation(final CompilationUnit unit) {
        final Queue<Node> all = unit.getChildNodes()
            .stream()
            .filter(node -> node instanceof TypeDeclaration<?>)
            .collect(Collectors.toCollection(LinkedList::new));
        if (all.isEmpty()) {
            throw new IllegalStateException("Compilation unit has contain at least one class");
        }
        return all.element();
    }

}
