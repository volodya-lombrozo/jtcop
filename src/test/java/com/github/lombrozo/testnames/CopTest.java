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
package com.github.lombrozo.testnames;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test for {@link Cop}.
 *
 * @since 0.2
 */
final class CopTest {

    @Test
    void checksSuccessfully() {
        MatcherAssert.assertThat(
            "Cop should not find any complaints.",
            new Cop(
                new Project.Fake(new ProductionClass.Fake(), new TestClass.Fake())
            ).inspection(),
            Matchers.empty()
        );
    }

    @Test
    void checksWithComplaint() {
        MatcherAssert.assertThat(
            "Cop should find one complaint.",
            new Cop(
                new Project.Fake(
                    new ProductionClass.Fake("CustomClass"),
                    new TestClass.Fake()
                )
            ).inspection(),
            Matchers.hasSize(1)
        );
    }

    @Test
    void checksMessageFormat() {
        MatcherAssert.assertThat(
            "Complaint is not formatted as expected",
            new Cop(
                new Project.Fake(
                    new ProductionClass.Fake("CustomClass"),
                    new TestClass.Fake()
                )
            ).inspection().iterator().next().message(),
            Matchers.allOf(
                Matchers.containsString(
                    "Test class 'FakeClassTest' doesn't have corresponding production class, either rename or move it"
                ),
                Matchers.containsString(
                    "(RuleEveryTestHasProductionClass)"
                )
            )
        );
        // Test FakeClassTest doesn't have corresponding production class.
        //	Either rename or move the test class FakeClassTest.
        //	You can also ignore the rule by adding @SuppressWarnings("JTCOP.RuleEveryTestHasProductionClass") annotation.
        //	Rule: RuleEveryTestHasProductionClass.
        //	You can read more about the rule here: https://github.com/volodya-lombrozo/jtcop/blob/main/docs/rules/all-have-production-class.md
    }
}
