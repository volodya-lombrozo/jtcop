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
// @checkstyle OuterTypeNumberCheck (1 line)
package com.github.lombrozo.testnames.rules.ml;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import opennlp.tools.postag.POSModel;
import org.cactoos.Scalar;
import org.cactoos.scalar.Sticky;

/**
 * Cached model source.
 *
 * @since 1.3.2
 */
public final class CachedModelSource implements ModelSource {

    /**
     * Model scalar.
     */
    private final Scalar<POSModel> scalar;

    /**
     * Constructor.
     *
     * @param source Model source
     */
    public CachedModelSource(final ModelSource source) {
        this(new Sticky<>(new CachedModelFs(source)));
    }

    /**
     * Ctor.
     * @param source Model source
     * @param location Cache location
     */
    public CachedModelSource(final ModelSource source, final String location) {
        this(new Sticky<>(new CachedModelFs(source, location)));
    }

    /**
     * Primary constructor.
     *
     * @param sclr Model scalar
     */
    public CachedModelSource(final Scalar<POSModel> sclr) {
        this.scalar = sclr;
    }

    @Override
    public POSModel model() throws Exception {
        return this.scalar.value();
    }
}

/**
 * Model cached in file system.
 * @since 1.3.2
 */
final class CachedModelFs implements Scalar<POSModel> {

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
    CachedModelFs(final ModelSource orgn) {
        this(orgn, "src/test/resources/ml/cached.bin");
    }

    /**
     * Constructor.
     * @param orgn Origin
     * @param location Location of cached file
     */
    CachedModelFs(final ModelSource orgn, final String location) {
        this(orgn, Paths.get(location).toFile());
    }

    /**
     * Primary constructor.
     * @param orgn Origin
     * @param ccd Cached file
     */
    CachedModelFs(final ModelSource orgn, final File ccd) {
        this.origin = orgn;
        this.cached = ccd;
    }

    @Override
    public POSModel value() throws Exception {
        final POSModel model;
        if (this.cached.exists()) {
            model = new POSModel(this.cached);
        } else {
            if (!this.cached.toPath().isAbsolute() && !this.cached.exists()) {
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
}
