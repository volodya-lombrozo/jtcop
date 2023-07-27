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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

class AssertionsTest {

    /**
     * Default explanation for assertions.
     */
    private final static String CONSTANT = "Message";

    @Test
    @SuppressWarnings("JTCOP.RuleAssertTrueHitter")
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