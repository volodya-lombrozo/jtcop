package com.github.lombrozo.testnames.javaparser;

import java.nio.file.Paths;
import java.util.Arrays;
import org.cactoos.io.ResourceOf;

enum JavaTestClasses {

    ONLY_METHODS_SUPPRESSED("TestOnlyMethodsSuppressed.java"),
    WITHOUT_TESTS("TestWithoutTests.java"),
    PARAMETERIZED("TestParameterized.java"),
    SIMPLE("TestSimple.java"),

    SUPPRESSED_CLASS("TestWithSuppressed.java"),

    SUPPRESSED_ANNOTATION("SuppressedAnnotation.java"),
    SUPPRESSED_INTERFACE("SuppressedInterface.java"),
    MANY_SUPPRESSED("TestWithLotsOfSuppressed.java");


    private final String file;

    JavaTestClasses(final String java) {
        this.file = java;
    }

    TestClassJavaParser javaParserClass(final String... suppressed) {
        try {
            return new TestClassJavaParser(
                Paths.get("."),
                new ResourceOf(this.file).stream(),
                Arrays.asList(suppressed)
            );
        } catch (final Exception exception) {
            throw new IllegalStateException("Can't read class '%s' from filesystem", exception);
        }
    }

}
