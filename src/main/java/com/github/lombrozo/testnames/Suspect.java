package com.github.lombrozo.testnames;

public class Suspect {

    private final Project project;
    private final TestClass test;

    public Suspect(final Project project, final TestClass test) {
        this.project = project;
        this.test = test;
    }

    public Project getProject() {
        return this.project;
    }

    public TestClass getTest() {
        return this.test;
    }
}
