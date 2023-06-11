package com.github.lombrozo.testnames.javaparser;

import com.github.javaparser.ast.body.MethodDeclaration;
import java.util.function.Predicate;

public class ByName implements Predicate<MethodDeclaration> {

    private final String name;

    public ByName(final String method) {
        this.name = method;
    }

    @Override
    public boolean test(final MethodDeclaration t) {
        return this.name.equals(t.getNameAsString());
    }
}
