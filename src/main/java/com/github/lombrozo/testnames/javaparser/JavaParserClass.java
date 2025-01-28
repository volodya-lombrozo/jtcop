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

import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.nodeTypes.NodeWithImplements;
import com.github.javaparser.ast.nodeTypes.NodeWithMembers;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.resolution.SymbolResolver;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JavaParser class.
 * The utility class for working with JavaParser library.
 *
 * @since 0.1.15
 */
@SuppressWarnings("PMD.TooManyMethods")
final class JavaParserClass {

    /**
     * Parsed Java class.
     */
    private final Node klass;

    /**
     * Ctor.
     *
     * @param path Input stream with java class.
     * @param resolver Symbol resolver.
     */
    JavaParserClass(final Path path, final SymbolResolver resolver) {
        this(JavaParserClass.parse(path, resolver));
    }

    /**
     * Ctor.
     *
     * @param stream Input stream with java class.
     * @param resolver Symbol resolver.
     */
    JavaParserClass(final InputStream stream, final SymbolResolver resolver) {
        this(JavaParserClass.parse(stream, resolver));
    }

    /**
     * Ctor.
     *
     * @param unit Compilation unit.
     */
    JavaParserClass(final CompilationUnit unit) {
        this(JavaParserClass.fromCompilation(unit));
    }

    /**
     * Ctor.
     *
     * @param unit Compilation unit.
     */
    private JavaParserClass(final Node unit) {
        this.klass = unit;
    }

    /**
     * Annotations of class.
     *
     * @return Annotations of class.
     */
    SuppressedAnnotations annotations() {
        return new SuppressedAnnotations((Node) this.klass);
    }

    /**
     * Methods of class.
     *
     * @param filters Filters for methods.
     * @return Methods of class.
     */
    @SafeVarargs
    final Stream<JavaParserMethod> methods(final Predicate<MethodDeclaration>... filters) {
        return ((NodeWithMembers<TypeDeclaration<?>>) this.klass).getMethods()
            .stream()
            .filter(method -> Stream.of(filters).allMatch(filter -> filter.test(method)))
            .map(JavaParserMethod::new);
    }

    /**
     * Whether the class is a test.
     *
     * @return True if the class is a test.
     */
    boolean isTest() {
        return !this.isAnnotation()
            && !this.isInterface()
            && !this.isPackageInfo()
            && this.hasTests();
    }

    /**
     * Retrieves the name of the superclass.
     *
     * @return The name of the superclass.
     */
    String superclass() {
        final String res;
        final String def = "java.lang.Object";
        if (this.klass instanceof ClassOrInterfaceDeclaration) {
            final ClassOrInterfaceDeclaration definition = (ClassOrInterfaceDeclaration) this.klass;
            if (definition.isInterface()) {
                res = def;
            } else {
                res = definition.getExtendedTypes()
                    .stream()
                    .findFirst()
                    .map(ClassOrInterfaceType::getNameAsString)
                    .orElse(def);
            }
        } else {
            res = def;
        }
        return res;
    }

    /**
     * Returns all parents of the class.
     *
     * @return All parents of the class.
     */
    Collection<Class<?>> parents() {
        final Collection<String> all = this.imports();
        return this.implement().getImplementedTypes().stream()
            .filter(ClassOrInterfaceType::isClassOrInterfaceType)
            .map(ClassOrInterfaceType::getNameWithScope)
            .map(name -> all.stream().filter(s -> s.contains(name)).findFirst().orElse(name))
            .map(this::load)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    /**
     * Returns package of the class.
     *
     * @return Package of the class.
     */
    Optional<String> pckg() {
        final CompilationUnit unit = (CompilationUnit) this.klass.getParentNode()
            .orElseThrow(
                () -> new IllegalStateException(
                    String.format(
                        "Can't find parent node in the klass %s",
                        this.klass
                    )
                )
            );
        return unit.getPackageDeclaration()
            .map(NodeWithName::getNameAsString);
    }

    /**
     * Whether the class has tests.
     *
     * @return True if the class has tests.
     */
    private boolean hasTests() {
        return this.methods(new TestsOnly()).findAny().isPresent();
    }

    /**
     * Checks if the class is an annotation.
     *
     * @return True if an annotation
     */
    private boolean isAnnotation() {
        return this.klass instanceof AnnotationDeclaration;
    }

    /**
     * Checks if the class is an interface.
     *
     * @return True if an interface
     */
    private boolean isInterface() {
        return this.klass instanceof ClassOrInterfaceDeclaration
            && this.cast().isInterface();
    }

    /**
     * Checks if the class is a package-info.java.
     *
     * @return True if a package-info.java
     */
    private boolean isPackageInfo() {
        return this.klass instanceof ClassOrInterfaceDeclaration
            && "empty".equals(this.cast().getNameAsString());
    }

    /**
     * Loads class from the classpath.
     *
     * @param name Name of the class.
     * @return Loaded class.
     */
    private Optional<Class<?>> load(final String name) {
        Optional<Class<?>> res;
        try {
            res = Optional.ofNullable(
                Thread.currentThread().getContextClassLoader().loadClass(name)
            );
        } catch (final ClassNotFoundException ex) {
            Logger.getLogger(this.getClass().getName())
                .warning(
                    String.format("Can't find class %s in classpath", name)
                );
            res = Optional.empty();
        }
        return res;
    }

    /**
     * All the imports of the current class.
     *
     * @return All the imports of the current class.
     */
    private Collection<String> imports() {
        return this.klass
            .getParentNode()
            .map(
                node -> ((CompilationUnit) node)
                    .getImports()
                    .stream()
                    .map(ImportDeclaration::getNameAsString)
                    .collect(Collectors.toList())
            )
            .orElse(Collections.emptyList());
    }

    /**
     * Cast the class to NodeWithImplements.
     *
     * @return NodeWithImplements
     */
    private NodeWithImplements<?> implement() {
        if (this.klass instanceof NodeWithImplements<?>) {
            return (NodeWithImplements<?>) this.klass;
        } else {
            throw new IllegalStateException(
                String.format("Can't cast %s to NodeWithImplements", this.klass)
            );
        }
    }

    /**
     * Cast the class to ClassOrInterfaceDeclaration.
     *
     * @return ClassOrInterfaceDeclaration
     */
    private ClassOrInterfaceDeclaration cast() {
        if (this.klass instanceof ClassOrInterfaceDeclaration) {
            return (ClassOrInterfaceDeclaration) this.klass;
        } else {
            throw new IllegalStateException(
                String.format("Can't cast %s to ClassOrInterfaceDeclaration", this.klass)
            );
        }
    }

    /**
     * Prestructor of class.
     *
     * @param unit Compilation unit.
     * @return Node with class.
     * @todo #187:90min Provide refactoring for JavaParserClass and TestClassJavaParser.
     *  The JavaParserClass and TestClassJavaParser classes are very similar. They share some logic
     *  and have similar methods. The refactoring should be provided to make the code more
     *  readable and maintainable. Also we have to count the different cases like records and
     *  package-info classes, inner classes and so on. For each case we must have a test.
     */
    private static Node fromCompilation(final CompilationUnit unit) {
        final Queue<Node> all = unit.getChildNodes()
            .stream()
            .filter(node -> node instanceof TypeDeclaration<?>)
            .collect(Collectors.toCollection(LinkedList::new));
        if (all.isEmpty()) {
            all.add(new ClassOrInterfaceDeclaration());
        }
        return all.element();
    }

    /**
     * Parse java by path.
     *
     * @param path Path to java file
     * @param resolver Symbol resolver.
     * @return Compilation unit.
     */
    private static CompilationUnit parse(final Path path, final SymbolResolver resolver) {
        try {
            StaticJavaParser.getParserConfiguration()
                .setSymbolResolver(resolver)
                .setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17);
            return JavaParserClass.parse(Files.newInputStream(path), resolver);
        } catch (final IOException ex) {
            throw new IllegalStateException(
                String.format("Can't parse java file: %s", path.toAbsolutePath()),
                ex
            );
        }
    }

    /**
     * Parse java by input stream.
     *
     * @param stream Input stream.
     * @param resolver Symbol resolver.
     * @return Compilation unit.
     */
    private static CompilationUnit parse(final InputStream stream, final SymbolResolver resolver) {
        StaticJavaParser.getParserConfiguration()
            .setSymbolResolver(resolver)
            .setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_17);
        return StaticJavaParser.parse(stream);
    }
}
