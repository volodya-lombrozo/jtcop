/*
 * MIT License
 *
 * Copyright (c) 2022-2024 Volodya Lombrozo
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

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;
import opennlp.tools.postag.POSModel;

/**
 * Cached model source.
 *
 * @see ModelSourceInternet
 * @since 1.3.2
 */
public final class CachedModelSource implements ModelSource {

    /**
     * Origin.
     */
    private final ModelSource origin;
    /**
     * Memory.
     */
    private final AtomicReference<POSModel> memory;

    /**
     * Constructor.
     *
     * @param orgn Origin
     * @param cache Memory
     */
    public CachedModelSource(
        final ModelSource orgn,
        final AtomicReference<POSModel> cache
    ) {
        this.origin = orgn;
        this.memory = cache;
    }

    @Override
    public POSModel model() throws IOException {
        final POSModel model;
        final File cached = Paths.get("src/test/resources/ml/cached.bin").toFile();
        final POSModel memorized = this.memory.get();
        if (memorized != null) {
            model = memorized;
        } else if (cached.exists()) {
            model = new POSModel(cached);
            this.memory.set(model);
        } else {
            model = this.origin.model();
            model.serialize(cached);
            this.memory.set(model);
        }
        return model;
    }
}
