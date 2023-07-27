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

import com.github.lombrozo.testnames.Assertion;
import com.github.lombrozo.testnames.TestCase;
import java.util.List;
import java.util.Optional;
import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link RuleAssertTrueHitter}.
 *
 * @since 1.0.1
 */
class RuleAssertTrueHitterTest {

    @Test
    void checksClassWithoutHitterCorrectly() {
        MatcherAssert.assertThat(
            "Test class without line hitter should not have complaints",
            new RuleAssertTrueHitter(
                new TestCase.Fake(
                    "Fake",
                    new Assertion.Fake("blah blah blah")
                )
            ).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void checksClassWithHitterCorrectly() {
        MatcherAssert.assertThat(
            "Test class without line hitter should not have complaints",
            new RuleAssertTrueHitter(
                new TestCase.Fake(
                    "Fake",
                    new Assertion() {
                        @Override
                        public Optional<String> explanation() {
                            return Optional.empty();
                        }

                        @Override
                        public List<String> arguments() {
                            return new ListOf<>("false", "true");
                        }

                        @Override
                        public String name() {
                            return "assertTrue";
                        }
                    }
                )
            ).complaints(),
            Matchers.hasSize(1)
        );
    }
}
