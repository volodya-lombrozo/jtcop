package com.github.lombrozo.testnames;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

final class CompositeTestPathRule implements Rule {

    private final Path start;

    CompositeTestPathRule(final Path path) {
        this.start = path;
    }

    @Override
    public void validate() throws WrongTestName {
        if (!Files.exists(this.start)) {
            return;
        }
        final List<Path> tests;
        try (final Stream<Path> files = Files.walk(this.start)
            .filter(Files::exists)
            .filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".java"))) {
            tests = files.collect(Collectors.toList());
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
        final List<WrongTestName> exceptions = new ArrayList<>();
        for (final Path test : tests) {
            try {
                new RuleForAllTests(new JavaTestCode(test)).validate();
            } catch (final WrongTestName ex) {
                exceptions.add(ex);
            }
        }
        if (!exceptions.isEmpty()) {
            throw new WrongTestName(exceptions);
        }
    }

}
