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
import java.nio.file.Paths;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests for {@link CachedModelSource}.
 *
 * @since 1.3.2
 */
final class CachedModelSourceTest {

    @Test
    void cachesModelInFile() throws Exception {
        final String location = "src/test/resources/ml/cached.bin";
        new CachedModelSource(
            new ModelSourceInternet(),
            location
        ).model();
        MatcherAssert.assertThat(
            String.format(
            "Model from %s is NULL, but it shouldn't",
                location
            ),
            new ModelSourceFileSystem(Paths.get(location)).model(),
            Matchers.notNullValue()
        );
    }

    @Test
    @Tag("slow")
    void loadsSecondTimeFaster(@TempDir final Path tmp) throws Exception {
        final Path path = tmp.resolve("testing.bin");
        final long before = System.currentTimeMillis();
        new CachedModelSource(
            new ModelSourceInternet(),
            path.toString()
        ).model();
        final long origin = System.currentTimeMillis() - before;
        final long start = System.currentTimeMillis();
        new CachedModelSource(
            new ModelSourceInternet(),
            path.toString()
        ).model();
        final long cached = System.currentTimeMillis() - start;
        final long expected = 250L;
        MatcherAssert.assertThat(
            String.format(
            "Cached time: (%s ms) should be less than %s ms",
                cached,
                expected
            ),
            cached < expected,
            new IsEqual<>(true)
        );
        MatcherAssert.assertThat(
            String.format(
                "Cached time: (%s ms) should be less than origin load time: (%s ms)",
                cached,
                origin
            ),
            cached < origin,
            new IsEqual<>(true)
        );
    }
}
