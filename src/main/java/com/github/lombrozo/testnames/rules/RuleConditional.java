/*
 * MIT License
 *
 * Copyright (c) 2022-2025 Volodya Lombrozo
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
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Utility rule that checks condition and returns complaints if condition is true.
 *
 * @since 0.2
 */
public final class RuleConditional implements Rule {

    /**
     * Condition to check.
     */
    private final Supplier<Boolean> predicate;

    /**
     * Complaints to return if condition is true.
     */
    private final Complaint complaint;

    /**
     * Creates ConditionalRule with given predicate and complaint.
     * @param check Condition to check
     * @param warning Complaint to return if condition is true
     */
    RuleConditional(
        final Supplier<Boolean> check,
        final Complaint warning
    ) {
        this.predicate = check;
        this.complaint = warning;
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList(RuleConditional.class.getSimpleName());
    }

    @Override
    public Collection<Complaint> complaints() {
        final Collection<Complaint> res;
        if (this.predicate.get()) {
            res = Collections.singleton(this.complaint);
        } else {
            res = Collections.emptyList();
        }
        return res;
    }
}
