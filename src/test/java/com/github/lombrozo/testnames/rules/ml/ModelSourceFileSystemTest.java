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

import java.nio.file.Path;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for {@link ModelSourceFileSystem}.
 * @since 0.10
 */
final class ModelSourceFileSystemTest {

//    private static final AtomicReference<POSModel> CACHE = new AtomicReference<>();
//
//    @BeforeAll
//    static void setUp() throws IOException {
//        final ModelSource model = new CachedModelSource(
//            new ModelSourceInternet(),
//            ModelSourceFileSystemTest.CACHE,
//            "src/test/resources/ml/cached.bin"
//        );
//        if (ModelSourceFileSystemTest.CACHE.get() == null) {
//            model.model();
//        }
//    }

    @Test
    void loadsFromFileSystem(@TempDir final Path temp) throws Exception {
        final Path path = temp.resolve("model.bin");
        new CachedModelSource(
            new ModelSourceInternet(),
            "src/test/resources/ml/cached.bin"
        ).model().serialize(path);
        MatcherAssert.assertThat(
            String.format("Model from %s is null", path),
            new ModelSourceFileSystem(path).model(),
            Matchers.notNullValue()
        );
    }
}
