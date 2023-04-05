package com.github.lombrozo.testnames.javaparser;

import com.github.lombrozo.testnames.ProductionClass;
import java.nio.file.Path;

public class JavaParserProductionClass implements ProductionClass {

    public JavaParserProductionClass(final Path path) {
        this.path = path;
    }

    private final Path path;

    @Override
    public String name() {
        return this.path.getFileName().toString();
    }
}
