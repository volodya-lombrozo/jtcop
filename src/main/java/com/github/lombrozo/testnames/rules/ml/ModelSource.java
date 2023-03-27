package com.github.lombrozo.testnames.rules.ml;

import java.io.IOException;
import opennlp.tools.postag.POSModel;

public interface ModelSource {

    POSModel model() throws IOException;

}
