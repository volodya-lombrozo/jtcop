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
package com.github.lombrozo.testnames.complaints;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.TestClass;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import lombok.ToString;

/**
 * Compound complaint for entire class.
 * @since 0.2
 */
@ToString
public final class ComplaintClass implements Complaint {

    /**
     * Class.
     */
    private final TestClass clazz;

    /**
     * All class complaints.
     */
    private final Collection<? extends Complaint> complaints;

    /**
     * Constructor.
     * @param clazz Class.
     * @param complaints Complaints.
     */
    ComplaintClass(
        final TestClass clazz,
        final Complaint... complaints
    ) {
        this(clazz, Arrays.asList(complaints));
    }

    /**
     * The main constructor.
     * @param clazz Class.
     * @param complaints All complaints.
     */
    public ComplaintClass(
        final TestClass clazz,
        final Collection<? extends Complaint> complaints
    ) {
        this.clazz = clazz;
        this.complaints = complaints;
    }

    @Override
    public String message() {
        final AtomicInteger counter = new AtomicInteger(1);
        return String.format(
            "The test class %s (%s:) has encountered some problems. Please review the results for more information.%s",
            this.clazz.name(),
            this.clazz.path(),
            this.complaints.stream()
                .map(Complaint::message)
                .map(message -> String.format("\n\t%d) %s", counter.getAndIncrement(), message))
                .collect(Collectors.joining())
        );
    }
}
