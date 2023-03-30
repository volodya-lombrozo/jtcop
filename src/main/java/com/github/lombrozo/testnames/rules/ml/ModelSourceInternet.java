/*
 * MIT License
 *
 * Copyright (c) 2022-2023 Volodya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.lombrozo.testnames.rules.ml;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import opennlp.tools.postag.POSModel;

/**
 * Model source from the Internet.
 *
 * @since 0.10
 */
public final class ModelSourceInternet implements ModelSource {

    /**
     * The internet address of the model.
     */
    private final URL url;

    /**
     * Default constructor.
     * Uses default URL.
     */
    ModelSourceInternet() {
        this(ModelSourceInternet.defaultUrl());
    }

    /**
     * The main constructor.
     * @param address The internet address of the model.
     */
    private ModelSourceInternet(final URL address) {
        this.url = address;
    }

    @Override
    public POSModel model() throws IOException {
        return new POSModel(this.url);
    }

    /**
     * Default URL.
     * @return The default URL.
     */
    private static URL defaultUrl() {
        final String url = "https://opennlp.sourceforge.net/models-1.5/en-pos-perceptron.bin";
        try {
            return new URL(url);
        } catch (final MalformedURLException ex) {
            throw new IllegalArgumentException(
                String.format("Default URL: '%s' is invalid", url),
                ex
            );
        }
    }
}
