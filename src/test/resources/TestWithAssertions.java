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

package com.github.lombrozo.testnames;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.stream.Stream;

class TestWithAssertions {
    @Test
    void junit() {
        System.out.println("Hello, world!");
        Assertions.assertEquals("1", "1", "JUnit explanation");
    }

    @Test
    void junitWithSupplier() {
        Assertions.assertEquals("1", "1", "JUnit explanation");
    }

    @Test
    void junitWithoutExplanation() {
        Assertions.assertEquals("1", "1");
    }

    @Test
    void severalFrameworks() {
        System.out.println("Dummy statement");
        Assertions.assertTrue(
            true,
            String.format("We have the reason for that - %s", "yes")
        );
        MatcherAssert.assertThat(
            "We have another reason",
            true,
            Matchers.is(true)
        );
        Assertions.assertNotSame("1", "2", "JUnit explanation");
        MatcherAssert.assertThat("We one more reason", "1", Matchers.is("2"));
        System.out.println("Dummy statement");
    }

    @Test
    void hamcrestAssertion() {
        MatcherAssert.assertThat(
            "We have another reason",
            true,
            Matchers.is(true)
        );
        MatcherAssert.assertThat("We one more reason", "1", Matchers.is("2"));
        System.out.println("Dummy statement");
    }

    @Test
    void assertionsWithoutMesssages() {
        MatcherAssert.assertThat(1, Matchers.is(1));
        Assertions.assertEquals(1, 1);
    }

    @Test
    void checksTheCaseFrom353issueWithAssertionInLoop() {
        // This test were added to check the issue #353
        // You can read more about it here:
        // https://github.com/volodya-lombrozo/jtcop/issues/353
        for (int i = 0; i < 10; ++i) {
            MatcherAssert.assertThat(
                AssertionsTest.MSG,
                "1",
                Matchers.equalTo("1")
            );
        }
    }

    @Test
    void checksTheCaseFrom357issueWithAssertionInIfStatement() {
        // This test were added to check the issue #357
        // You can read more about it here:
        // https://github.com/volodya-lombrozo/jtcop/issues/357
        int x = 11;
        if (x > 10) {
            MatcherAssert.assertThat(
                "Assertion inside if statement",
                "1",
                Matchers.equalTo("1")
            );
        }
    }

    @Test
    void checksTheCaseFrom357issueWithAssertionInClosure() {
        // This test were added to check the issue #357
        // You can read more about it here:
        // https://github.com/volodya-lombrozo/jtcop/issues/357
        Runnable r = () -> {
            MatcherAssert.assertThat(
                "Assertion inside if statement",
                "1",
                Matchers.equalTo("1")
            );
        };
        r.run();
    }

    @Test
    void checksTheCaseFrom357issueWithAssertionAsParam() {
        Stream.generate(() -> (Runnable) () -> {
            MatcherAssert.assertThat(
                "Lambda as a parameter assertion",
                1,
                Matchers.equalTo(1)
            );
        }).limit(1).forEach(Runnable::run);
    }

    @Test
    void checksTheCaseFrom413issueWithAssertionInTryStatement() {
        // https://github.com/volodya-lombrozo/jtcop/issues/413
        try {
            MatcherAssert.assertThat(
                "Assertion inside try statement",
                "1",
                Matchers.equalTo("1")
            );
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @Test
    void checksTheCaseFrom413issueWithAssertionInCatchBlock() {
        // https://github.com/volodya-lombrozo/jtcop/issues/413
        try {
            System.nanoTime();
        } catch (RuntimeException e) {
            MatcherAssert.assertThat(
                "Assertion inside try statement",
                "1",
                Matchers.equalTo("1")
            );
            e.printStackTrace();
        }
    }

}