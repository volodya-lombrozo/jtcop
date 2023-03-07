package com.github.lombrozo.testnames.rules;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import org.cactoos.io.InputOf;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OpenNlpRuleTest {

    @Test
    void test() {
        final Path path = Paths.get(
            "/Users/lombrozo/Workspace/OpenSource/learning/opennlp/en-pos-perceptron.bin"
        );
        try (final InputStream stream = new InputOf(path).stream()) {
            final POSModel model = new POSModel(stream);
            final String[] res = new POSTaggerME(model)
                .tag("Hello, mister sadman!".split(" "));
            System.out.println(Arrays.toString(res));
        } catch (final Exception ex) {
            throw new IllegalStateException(ex);
        }
    }

}