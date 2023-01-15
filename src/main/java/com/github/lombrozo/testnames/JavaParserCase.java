package com.github.lombrozo.testnames;

import java.nio.file.Path;
import lombok.Data;

@Data
class JavaParserCase implements TestCase {

    private final String className;
    private final String name;
    private final Path path;

    JavaParserCase(final String className, final String name, final Path path) {
        this.className = className;
        this.name = name;
        this.path = path;
    }

    @Override
    public String className() {
        return this.className;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public Path path() {
        return this.path;
    }
}
