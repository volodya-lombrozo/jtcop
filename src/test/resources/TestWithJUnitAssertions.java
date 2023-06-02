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

import java.time.Duration;
import java.util.Collections;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestWithJUnitAssertions {

    /**
     * Default explanation for assertions.
     */
    private final static String DEFAULT_EXPLANATION = "JUnit explanation";

    /**
     * Default supplier for assertions.
     */
    private final static String DEFAULT_SUPPLIER = () -> TestWithJUnitAssertions.DEFAULT_EXPLANATION;

    @Test
    void withMessages() {
        Assertions.assertEquals("1", "1", DEFAULT_EXPLANATION);
        Assertions.assertTrue(true, TestWithJUnitAssertions.DEFAULT_EXPLANATION);
    }

    @Test
    void withoutMessages() {
        Assertions.assertEquals("1", "1");
        Assertions.assertTrue(true);
        Assertions.assertFalse(false);
        Assertions.assertNotEquals("1", "2");
        Assertions.assertArrayEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        Assertions.assertLinesMatch(Collections.EMPTY_LIST, Collections.EMPTY_LIST);
        Assertions.assertInstanceOf(Integer.class, 1);
        Assertions.assertNotSame(new Object(), new Object());
        Assertions.assertSame(1, 1);
        Assertions.assertNull(null);
        Assertions.assertNotNull(new Object());
        Assertions.assertTimeout(
            Duration.ZERO,
            () -> {
            }
        );
        Assertions.assertTimeoutPreemptively(
            Duration.ZERO,
            () -> {
            }
        );
        Assertions.assertThrowsExactly(
            IllegalStateException.class,
            () -> {
                throw new IllegalStateException();
            }
        );
        Assertions.assertIterableEquals(
            new int[]{1, 2, 3},
            new int[]{1, 2, 3}
        );
        Assertions.assertDoesNotThrow(
            () -> {
            }
        );
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> {
                throw new IllegalStateException();
            }
        );
    }

    @Test
    void ingoresSomeAssertionMessages() {
        Assertions.fail("JUnit explanation");
        Assertions.fail(new IllegalStateException());
        Assertions.fail();
        Assertions.fail("JUnit explanation", new IllegalStateException());
        Assertions.fail(() -> "JUnit explanation");
        Assertions.assertAll(
            () -> {
            }
        );
    }
}