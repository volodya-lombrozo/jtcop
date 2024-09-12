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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicReference;
import opennlp.tools.postag.POSModel;

/**
 * Cached model source.
 *
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
     * Cached file.
     */
    private final File cached;

    /**
     * Constructor.
     * @param orgn Origin
     * @param cache Memory
     */
    public CachedModelSource(
        final ModelSource orgn,
        final AtomicReference<POSModel> cache
    ) {
        this(orgn, cache, "src/test/resources/ml/cached.bin");
    }

    /**
     * Constructor.
     * @param orgn Origin
     * @param cache Memory
     * @param location Location of cached file
     */
    public CachedModelSource(
        final ModelSource orgn,
        final AtomicReference<POSModel> cache,
        final String location
    ) {
        this(orgn, cache, Paths.get(location).toFile());
    }

    /**
     * Primary constructor.
     * @param orgn Origin
     * @param cache Memory
     * @param ccd Cached file
     */
    public CachedModelSource(
        final ModelSource orgn,
        final AtomicReference<POSModel> cache,
        final File ccd
    ) {
        this.origin = orgn;
        this.memory = cache;
        this.cached = ccd;
    }

    @Override
    public POSModel model() throws IOException {
        final POSModel model;
        final POSModel memorized = this.memory.get();
        if (memorized != null) {
            model = memorized;
        } else if (this.cached.exists()) {
            model = new POSModel(this.cached);
            this.memory.set(model);
        } else {
            Files.createDirectory(Paths.get(this.cached.getParent()));
            model = this.origin.model();
            model.serialize(this.cached);
            this.memory.set(model);
        }
        return model;
    }
}
