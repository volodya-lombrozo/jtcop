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

import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.WrongTestName;
import com.github.lombrozo.testnames.javaparser.JavaTestCode;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public void validate() throws WrongTestName {
        if (!Files.exists(this.start)) {
            return;
        }
        final List<Path> tests;
        try (Stream<Path> files = Files.walk(this.start)
            .filter(Files::exists)
            .filter(Files::isRegularFile)
            .filter(path -> path.toString().endsWith(".java"))) {
            tests = files.collect(Collectors.toList());
        } catch (final IOException ex) {
            throw new IllegalStateException(ex);
        }
        final List<WrongTestName> exceptions = new ArrayList<>(0);
        for (final Path test : tests) {
            try {
                new AllTestsInPresentSimple(new JavaTestCode(test)).validate();
            } catch (final WrongTestName ex) {
                exceptions.add(ex);
            }
        }
        if (!exceptions.isEmpty()) {
            throw new WrongTestName(exceptions);
        }
    }

}
