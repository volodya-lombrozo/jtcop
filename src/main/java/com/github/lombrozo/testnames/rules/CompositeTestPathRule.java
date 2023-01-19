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

package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.javaparser.JavaTestCode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * The rule for composite test.
 *
 * @since 0.1.7
 */
public final class CompositeTestPathRule implements Rule {

    /**
     * The start.
     */
    private final Path start;

    /**
     * Ctor.
     *
     * @param path The start
     */
    public CompositeTestPathRule(final Path path) {
        this.start = path;
    }

    @Override
    public Collection<Complaint> complaints() {
        final Collection<Complaint> result;
        if (Files.exists(this.start)) {
            try {
                result = Files.walk(this.start)
                    .filter(Files::exists)
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .map(JavaTestCode::new)
                    .map(AllTestsInPresentSimple::new)
                    .map(AllTestsInPresentSimple::complaints)
                    .flatMap(Collection::stream)
                    .collect(Collectors.toList());
            } catch (final IOException exception) {
                throw new IllegalStateException(exception);
            }
        } else {
            result = Collections.emptyList();
        }
        return result;
    }

}
