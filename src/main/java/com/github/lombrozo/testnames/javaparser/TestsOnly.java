package com.github.lombrozo.testnames.javaparser;

import com.github.javaparser.ast.body.MethodDeclaration;
import java.util.function.Predicate;

class TestsOnly implements Predicate<MethodDeclaration> {

    @Override
    public boolean test(final MethodDeclaration t) {
        return !t.isPrivate() && (t.isAnnotationPresent("Test")
            || t.isAnnotationPresent("ParameterizedTest"));
    }
}
