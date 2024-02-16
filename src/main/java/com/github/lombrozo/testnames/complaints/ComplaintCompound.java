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
package com.github.lombrozo.testnames.complaints;

import com.github.lombrozo.testnames.Complaint;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Complex complaint that aggregates several complaints.
 *
 * @since 0.2.0
 */
public final class ComplaintCompound implements Complaint {

    /**
     * The complaints.
     */
    private final Collection<? extends Complaint> complaints;

    /**
     * Ctor.
     *
     * @param all The complaints
     */
    public ComplaintCompound(final Collection<? extends Complaint> all) {
        this.complaints = all;
    }

    @Override
    public String message() {
        return this.complaints.stream()
            .map(Complaint::message)
            .collect(Collectors.joining("\n", "\n", ""));
    }
}
