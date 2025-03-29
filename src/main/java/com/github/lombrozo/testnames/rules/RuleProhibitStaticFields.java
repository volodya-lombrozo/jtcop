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
import com.github.lombrozo.testnames.Field;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.TestClass;
import com.github.lombrozo.testnames.complaints.ComplaintLinked;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Check that a test class does not contain static literals.
 * Were suggested <a href="https://github.com/volodya-lombrozo/jtcop/issues/302">here</a>.
 * @since 1.4
 */
public final class RuleProhibitStaticFields implements Rule {

    /**
     * The test class.
     */
    private final TestClass test;

    /**
     * Constructor.
     * @param test The test class.
     */
    public RuleProhibitStaticFields(final TestClass test) {
        this.test = test;
    }

    @Override
    public List<String> aliases() {
        return Collections.singletonList(RuleProhibitStaticFields.class.getSimpleName());
    }

    @Override
    public Collection<Complaint> complaints() {
        final Collection<Complaint> result;
        final List<String> names = this.test.fields().stream()
            .filter(Field::isStatic)
            .filter(field -> !this.suppressed(field))
            .map(Field::name)
            .collect(Collectors.toList());
        if (names.isEmpty()) {
            result = Collections.emptyList();
        } else {
            result = names.stream().map(this::complaint).collect(Collectors.toList());
        }
        return result;
    }

    /**
     * Generate a complaint for a static field.
     * @param name Field name
     * @return Complaint
     */
    private Complaint complaint(final String name) {
        return new ComplaintLinked(
            String.format(
                "The static field '%s' was found in the class '%s'", name, this.test.name()
            ),
            "Please don't use static fields in the test class; it's better to use constants directly within the test methods.",
            this.getClass(),
            "prohibit-static-fields.md"
        );
    }

    /**
     * Is the rule suppressed for the field?
     * @param field Field to check.
     * @return True if the rule is suppressed for the field.
     */
    private boolean suppressed(final Field field) {
        return field.suppressed().stream().anyMatch(name -> this.aliases().contains(name));
    }
}
