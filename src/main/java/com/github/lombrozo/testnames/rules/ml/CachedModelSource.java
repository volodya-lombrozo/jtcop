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
import java.nio.file.Files;
import java.nio.file.Paths;
import opennlp.tools.postag.POSModel;
import org.cactoos.scalar.Sticky;

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
     * Cached file.
     */
    private final File cached;

    /**
     * Constructor.
     * @param orgn Origin
     */
    public CachedModelSource(
        final ModelSource orgn
    ) {
        this(orgn, "src/test/resources/ml/cached.bin");
    }

    /**
     * Constructor.
     * @param orgn Origin
     * @param location Location of cached file
     */
    public CachedModelSource(
        final ModelSource orgn,
        final String location
    ) {
        this(orgn, Paths.get(location).toFile());
    }

    /**
     * Primary constructor.
     * @param orgn Origin
     * @param ccd Cached file
     */
    public CachedModelSource(
        final ModelSource orgn,
        final File ccd
    ) {
        this.origin = orgn;
        this.cached = ccd;
    }

    @Override
    public POSModel model() {
        try {
            return new Sticky<>(
                () -> {
                    final POSModel model;
                    if (this.cached.exists()) {
                        model = new POSModel(this.cached);
                    } else {
                        if (
                            !this.cached.toPath().isAbsolute()
                            && !this.cached.exists()
                        ) {
                            Files.createDirectory(
                                Paths.get(
                                    this.cached.getParent()
                                )
                            );
                        }
                        model = this.origin.model();
                        model.serialize(this.cached);
                    }
                    return model;
                }
            ).value();
        } catch (final Exception exception) {
            throw new IllegalStateException(
                "Cannot cache a model",
                exception
            );
        }
    }
}
