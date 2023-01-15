package com.github.lombrozo.testnames;

import java.nio.file.Path;
import java.nio.file.Paths;

public interface TestCase {

    String className();

    String name();

    Path path();


    class FakeCase implements TestCase {

        private final String className;
        private final String name;
        private final Path path;

        FakeCase(final String name) {
            this("FakeClass", name, Paths.get("."));
        }

        FakeCase(final String className, final String name, final Path path) {
            this.className = className;
            this.name = name;
            this.path = path;
        }

        @Override
        public String className() {
            return className;
        }

        @Override
        public String name() {
            return name;
        }

        @Override
        public Path path() {
            return path;
        }
    }
}
