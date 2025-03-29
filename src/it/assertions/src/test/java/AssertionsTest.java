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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import java.util.function.Consumer;

/**
 * Test class for assertions.
 *
 * @since 0.1
 */
class AssertionsTest {

    /**
     * Default explanation for assertions.
     */
    @SuppressWarnings("JTCOP.RuleProhibitStaticFields")
    private final static String CONSTANT = "Message";

    /**
     * Default message for assertions.
     */
    @SuppressWarnings("JTCOP.RuleProhibitStaticFields")
    private static final String MSG = "MESSAGE";

    @Test
    void checksTheCaseFrom357issueLambda() {
        // This test were added to check the issue #357
        // You can read more about it here:
        // https://github.com/volodya-lombrozo/jtcop/issues/347
        Runnable r = () -> {
            MatcherAssert.assertThat(
                "TO ADD ASSERTION MESSAGE",
                "1",
                Matchers.equalTo("1")
            );
        };
    }

    @Test
    void checksTheCaseFrom357issueLabbdaAsParam() {
        // This test were added to check the issue #357
        // You can read more about it here:
        // https://github.com/volodya-lombrozo/jtcop/issues/357
        Stream.generate(() -> (Runnable) () -> {
            MatcherAssert.assertThat(
                "Lambda as a parameter assertion",
                1,
                Matchers.equalTo(1)
            );
        }).limit(1).forEach(Runnable::run);
    }

    @Test
    void checksTheCaseFrom357issueLabbdaAsParamAfterAConstructor() {
        // This test were added to check the issue #357
        // You can read more about it here:
        // https://github.com/volodya-lombrozo/jtcop/issues/357
        new AssertionsTest.Same(this).together(f -> {
            MatcherAssert.assertThat(
                "The class should be the same as the one that was passed to the constructor",
                f,
                Matchers.equalTo(this)
            );
        });
    }

    @Test
    void checksTheCaseFrom357issueIfStatement() throws InterruptedException {
        // This test were added to check the issue #357
        // You can read more about it here:
        // https://github.com/volodya-lombrozo/jtcop/issues/357
        final int threads = 3;
        final ExecutorService service = Executors.newFixedThreadPool(threads);
        final Set<Integer> basket = Collections.synchronizedSet(new HashSet<>(threads));
        final CountDownLatch latch = new CountDownLatch(1);
        Stream.generate(
            () -> (Runnable) () -> {
                try {
                    latch.await();
                    basket.add(Integer.valueOf(1));
                } catch (final InterruptedException exception) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException(
                        "The testing thread was interrupted, current basket size %d",
                        exception
                    );
                }
            }
        ).limit(threads).forEach(service::submit);
        latch.countDown();
        service.shutdown();
        if (service.awaitTermination(1, TimeUnit.SECONDS)) {
            MatcherAssert.assertThat(
                "TO ADD ASSERTION MESSAGE",
                basket.size(),
                Matchers.equalTo(1)
            );
        } else {
            throw new IllegalStateException(
                String.format(
                    "Failed to wait for threads to finish. Current basket size %d, but expected %d",
                    basket.size(),
                    threads
                )
            );
        }
    }

    @Test
    void checksTheCaseFrom353issueWithAssertionInLoop() {
        // This test were added to check the issue #353
        // You can read more about it here:
        // https://github.com/volodya-lombrozo/jtcop/issues/347
        for (int i = 0; i < 10; ++i) {
            MatcherAssert.assertThat(
                AssertionsTest.MSG,
                "1",
                Matchers.equalTo("1")
            );
        }
    }

    @Test
    void checksTheCaseFrom347issue() {
        // This test were added to check the issue #347
        // You can read more about it here:
        // https://github.com/volodya-lombrozo/jtcop/issues/347
        MatcherAssert.assertThat(
            AssertionsTest.MSG,
            "1",
            Matchers.equalTo("1")
        );
    }


    @Test
    @SuppressWarnings("JTCOP.LineHitterRule")
    void checksJUnitAssertions() {
        Assertions.assertEquals("1", "1", message());
        Assertions.assertEquals("1", "1", "Message");
        Assertions.assertTrue(true, "Message");
        Assertions.assertFalse(false, message());
        Assertions.assertNotNull(new Object(), CONSTANT);
        Assertions.assertNotNull(new Object(), message());
    }

    @Test
    void checksHamcrestAssertions() {
        MatcherAssert.assertThat(
            CONSTANT,
            "1",
            Matchers.equalTo("1")
        );
        MatcherAssert.assertThat(
            message(),
            "1",
            Matchers.equalTo("1")
        );
        MatcherAssert.assertThat(
            "Message",
            true,
            Matchers.is(true)
        );
    }

    @Test
    void checksCombinedAssertions() {
        Assertions.assertSame(1, 1, "Message");
        Assertions.assertSame(1, 1, CONSTANT);
        Assertions.assertNull(null, "Message");
        Assertions.assertNull(null, CONSTANT);
        MatcherAssert.assertThat(
            CONSTANT,
            "1",
            Matchers.equalTo("1")
        );
        MatcherAssert.assertThat(
            message(),
            "1",
            Matchers.equalTo("1")
        );
        MatcherAssert.assertThat(
            "Message",
            true,
            Matchers.is(true)
        );
    }

    @Test
    @SuppressWarnings("JTCOP.RuleAssertionMessage")
    void ignoresJUnitAssertions() {
        Assertions.assertSame(1, 1);
        Assertions.assertSame(1, 1);
    }

    @Test
    @SuppressWarnings("JTCOP.RuleAssertionMessage")
    void ignoresHamcrestAssertions() {
        MatcherAssert.assertThat(
            "1",
            Matchers.equalTo("1")
        );
        MatcherAssert.assertThat(
            "1",
            Matchers.equalTo("1")
        );
        MatcherAssert.assertThat(
            true,
            Matchers.is(true)
        );
    }

    @Test
    void findsAssertionsInBlockOfTryStatement() {
        try {
            MatcherAssert.assertThat(
                "Message",
                "1",
                Matchers.equalTo("1")
            );
        } catch (RuntimeException e) {
        }
    }

    @Test
    void findsAssertionsInCatchOfTryStatement() {
        try {
            System.out.println("Hello");
        } catch (RuntimeException e) {
            MatcherAssert.assertThat(
                message(),
                "1",
                Matchers.equalTo("1")
            );
        }
    }

    private String message() {
        return "Message";
    }

    private static class Same {
        private final AssertionsTest test;

        public Same(final AssertionsTest test) {
            this.test = test;
        }

        void together(Consumer<AssertionsTest> supplier) {
            supplier.accept(this.test);
        }
    }
}