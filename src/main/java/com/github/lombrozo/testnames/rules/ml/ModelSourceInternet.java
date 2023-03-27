package com.github.lombrozo.testnames.rules.ml;

import java.io.IOException;
import java.net.URL;
import opennlp.tools.postag.POSModel;

public class ModelSourceInternet implements ModelSource {
    @Override
    public POSModel model() throws IOException {
        return new POSModel(
            new URL(
                "https://opennlp.sourceforge.net/models-1.5/en-pos-perceptron.bin"
            )
        );
    }
}
