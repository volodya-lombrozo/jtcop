package com.github.lombrozo.testnames.bytecode;

import com.github.lombrozo.testnames.ProductionClass;
import com.github.lombrozo.testnames.Project;
import com.github.lombrozo.testnames.TestClass;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BytecodeProject implements Project {

    private final Path classes;
    private final Path tests;

    BytecodeProject(
        final Path classes,
        final Path tests
    ) {
        this.classes = classes;
        this.tests = tests;
    }

    @Override
    public Collection<ProductionClass> productionClasses() {
        return Collections.emptyList();
    }

    @Override
    public Collection<TestClass> testClasses() {
        try (final Stream<Path> stream = Files.walk(this.tests)) {
            return stream.map(BytecodeClass::new)
                .filter(BytecodeClass::isClass)
                .map(BytecodeClass::toTest)
                .collect(Collectors.toList());
        } catch (final IOException ex) {
            throw new IllegalStateException(
                String.format("Can't read test classes from %s", this.tests),
                ex
            );
        }
    }
}
