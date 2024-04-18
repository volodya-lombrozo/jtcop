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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

class AssertionsTest {

    /**
     * Default explanation for assertions.
     */
    private final static String CONSTANT = "Message";

    /**
     * Default message for assertions.
     */
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
    void checksTheCaseFrom357issueIfStatement() {
        // This test were added to check the issue #357
        // You can read more about it here:
        // https://github.com/volodya-lombrozo/jtcop/issues/357
        final int threads = Runtime.getRuntime().availableProcessors() + 10;
        final ExecutorService service = Executors.newFixedThreadPool(threads);
        final PhPackage pckg = new PhPackage(PhPackageTest.DEFAULT_PACKAGE);
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
                Matchers.equalTo(threads)
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

    private String message() {
        return "Message";
    }
}