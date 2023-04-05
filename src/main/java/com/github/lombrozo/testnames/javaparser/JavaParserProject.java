package com.github.lombrozo.testnames.javaparser;

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

public class JavaParserProject implements Project {

    private final Path main;
    private final Path test;

    public JavaParserProject(final Path main, final Path test) {
        this.main = main;
        this.test = test;
    }

    @Override
    public Collection<ProductionClass> productionClasses() {
        final Collection<ProductionClass> res;
        if (Files.exists(this.main)) {
            try (Stream<Path> files = Files.walk(this.main)) {
                res = files
                    .filter(Files::exists)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(JavaParserProductionClass::new)
                    .collect(Collectors.toList());
            } catch (final IOException exception) {
                throw new IllegalStateException(exception);
            }
        } else {
            res = Collections.emptyList();
        }
        return res;
    }

    @Override
    public Collection<TestClass> testClasses() {
        final Collection<TestClass> res;
        if (Files.exists(this.test)) {
            try (Stream<Path> files = Files.walk(this.test)) {
                res = files
                    .filter(Files::exists)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(JavaParserTestClass::new)
                    .collect(Collectors.toList());
            } catch (final IOException exception) {
                throw new IllegalStateException(exception);
            }
        } else {
            res = Collections.emptyList();
        }
        return res;
    }
}
