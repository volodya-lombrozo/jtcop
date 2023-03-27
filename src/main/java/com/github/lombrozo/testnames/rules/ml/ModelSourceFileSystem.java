package com.github.lombrozo.testnames.rules.ml;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import opennlp.tools.postag.POSModel;

public class ModelSourceFileSystem implements ModelSource {
    private final Path path;

    public ModelSourceFileSystem() {
        this("/tmp/opennlp/en-pos-perceptron.bin");
    }

    private ModelSourceFileSystem(final String file) {
        this(Paths.get(file));
    }

    private ModelSourceFileSystem(final Path file) {
        this.path = file;
    }

    @Override
    public POSModel model() throws IOException {
        return new POSModel(this.path.toFile());
    }
}
