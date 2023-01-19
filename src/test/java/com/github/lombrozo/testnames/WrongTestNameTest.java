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

import java.util.UUID;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * The test case for {@link WrongTestName}.
 *
 * @since 0.1.0
 */
final class WrongTestNameTest {

    @Test
    void createsUsingSingleParamConstructor() {
        final String name = UUID.randomUUID().toString();
        MatcherAssert.assertThat(
            new WrongTestName(new Case.FakeCase(name)).getMessage(),
            Matchers.equalTo(
                String.format(
                    "Test name '%s#%s' doesn't follow naming rules, test path: %s",
                    "FakeClass",
                    name,
                    "."
                )
            )
        );
    }

    @Test
    void createsUsingNameAndReason() {
        final String name = UUID.randomUUID().toString();
        final String reason = UUID.randomUUID().toString();
        MatcherAssert.assertThat(
            new WrongTestName(new Case.FakeCase(name), reason).getMessage(),
            Matchers.equalTo(
                String.format(
                    "Test name '%s#%s' doesn't follow naming rules, because %s, test path: %s",
                    "FakeClass",
                    name,
                    reason,
                    "."
                )
            )
        );
    }

    @Test
    void createsUsingSeveralExceptions() {
        MatcherAssert.assertThat(
            new WrongTestName(
                new WrongTestName(new Case.FakeCase("test1")),
                new WrongTestName(new Case.FakeCase("test2"))
            ).getMessage(),
            Matchers.allOf(
                Matchers.containsString("test1"),
                Matchers.containsString("test2")
            )
        );
    }

}
