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

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

final class MockeryValidTest {

    /**
     * Test case that uses acceptable number of mocks.
     *  This test case should pass {@link com.github.lombrozo.testnames.rules.RuleTestCaseContainsMockery}
     *  since it contains aacceptable number of mocks (2), the maximum is 2 (default).
     */
    @Test
    void testsSomethingWithAcceptableNumberOfMocks() {
        Mockito.when(Mockito.mock(List.class).get(0)).thenReturn("jeff");
        Mockito.when(Mockito.mock(Map.class).get("test")).thenReturn("jeff");
    }

    /**
     * Test case without mocks at all.
     *  This test case should pass {@link com.github.lombrozo.testnames.rules.RuleTestCaseContainsMockery}
     *  since it does not contain 0 mocks, the maximum is 2 (default).
     */
    @Test
    void testsSomethingWithoutMocks() {
        Mockito.when(new ListOf<>().get(0)).thenReturn("jeff");
        Mockito.when(new MapOf<>().get("test")).thenReturn("jeff");
    }
}
