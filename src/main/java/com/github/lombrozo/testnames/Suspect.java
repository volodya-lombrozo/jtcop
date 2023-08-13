package com.github.lombrozo.testnames;

public class Suspect {

    private final Project project;
    private final TestClass test;

    Suspect(final Project proj, final TestClass klass) {
        this.project = proj;
        this.test = klass;
    }

    public Project project() {
        return this.project;
    }

    public TestClass test() {
        return this.test;
    }
}
