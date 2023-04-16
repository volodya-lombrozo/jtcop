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
package com.github.lombrozo.testnames;

import com.github.lombrozo.testnames.rules.RuleAllTestsHaveProductionClass;
import com.github.lombrozo.testnames.rules.RuleAllTestsInPresentSimple;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The cop that checks the project.
 *
 * @since 0.2
 */
final class Cop {

    /**
     * The project to check.
     */
    private final Project project;

    /**
     * Ctor.
     * @param proj The project to check.
     */
    Cop(final Project proj) {
        this.project = proj;
    }

    /**
     * Checks the project.
     * @return The complaints.
     * @todo #1:90min Simplify Cop.inspection() method.
     *  We can have no indents here. In order to refactor this we can add more methods or re-design
     *  the current solution.
     */
    Collection<Complaint> inspection() {
        final Collection<Complaint> res = new ArrayList<>(
            new RuleAllTestsHaveProductionClass(this.project).complaints()
        );
        final List<Complaint> list = this.project.testClasses().stream()
            .map(RuleAllTestsInPresentSimple::new)
            .map(RuleAllTestsInPresentSimple::complaints)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());
        res.addAll(list);
        return res;
    }
}
