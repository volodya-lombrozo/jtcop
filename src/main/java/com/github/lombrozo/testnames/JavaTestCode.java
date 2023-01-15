package com.github.lombrozo.testnames;

import com.github.javaparser.ParseProblemException;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class JavaTestCode implements Cases {
    private final Path path;

    public JavaTestCode(final Path path) {
        this.path = path;
    }

    @Override
    public Collection<TestCase> all() {
        try {
            final CompilationUnit parse = StaticJavaParser.parse(this.path);
            final List<Node> childNodes = parse.getChildNodes();
            final List<TestCase> names = new ArrayList<>();
            for (final Node childNode : childNodes) {
                if (childNode instanceof ClassOrInterfaceDeclaration) {
                    final ClassOrInterfaceDeclaration clazz = (ClassOrInterfaceDeclaration) childNode;
                    clazz.getMethods().forEach(m -> {
                        if (!m.isPrivate() && (
                            m.isAnnotationPresent("Test")
                                || m.isAnnotationPresent("ParameterizedTest"))) {

                            final String name = m.getNameAsString();
                            names.add(new JavaParserCase(clazz.getNameAsString(), name, this.path));
                        }
                    });
                }
            }
            return names;
        } catch (IOException | ParseProblemException ex) {
            throw new IllegalStateException(
                String.format("Failed to parse Java class by path %s", this.path),
                ex
            );
        }
    }
}
