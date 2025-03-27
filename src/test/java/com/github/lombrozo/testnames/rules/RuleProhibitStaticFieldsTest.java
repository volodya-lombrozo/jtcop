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
import com.github.lombrozo.testnames.TestClass;
import com.github.lombrozo.testnames.complaints.ComplaintLinked;
import java.util.Collection;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test cases for {@link RuleProhibitStaticFields}.
 * @since 1.4
 */
final class RuleProhibitStaticFieldsTest {

    @Test
    void retrievesAliases() {
        MatcherAssert.assertThat(
            "RuleProhibitStaticLiterals should have only one alias with the same name",
            new RuleProhibitStaticFields(new TestClass.Fake()).aliases(),
            Matchers.contains("RuleProhibitStaticFields")
        );
    }

    @Test
    void complaintsOnStaticFields() {
        MatcherAssert.assertThat(
            "RuleProhibitStaticLiterals should complain on static fields",
            new RuleProhibitStaticFields(
                new TestClass.WithFields(new Field.Named("constant", new Field.Static()))
            ).complaints(),
            Matchers.not(Matchers.empty())
        );
    }

    @Test
    void generatesMeaningfulComplaint() {
        final Collection<Complaint> all = new RuleProhibitStaticFields(
            new TestClass.WithFields(new Field.Named("meaningful", new Field.Static()))
        ).complaints();
        MatcherAssert.assertThat(
            String.format(
                "RuleProhibitStaticLiterals should generate meaningful complaint. All complaints: %s",
                all
            ),
            all.stream()
                .findFirst()
                .orElseThrow(() -> new AssertionError("No complaints found")),
            Matchers.equalTo(
                new ComplaintLinked(
                    "The static field 'meaningful' was found in the class 'FakeClassTest'",
                    "Please don't use static fields in the test class; it's better to use constants directly within the test methods.",
                    RuleProhibitStaticFields.class,
                    "prohibit-static-fields.md"
                )
            )
        );
    }

    @Test
    void doesNotComplainOnNonStaticFields() {
        MatcherAssert.assertThat(
            "RuleProhibitStaticLiterals should not complain on non-static fields",
            new RuleProhibitStaticFields(
                new TestClass.WithFields(new Field.Named("local"))
            ).complaints(),
            Matchers.empty()
        );
    }
}
