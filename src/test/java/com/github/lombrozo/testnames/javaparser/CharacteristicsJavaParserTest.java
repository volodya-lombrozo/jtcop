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
package com.github.lombrozo.testnames.javaparser;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link CharacteristicsJavaParser}.
 *
 * @since 0.1.19
 * @todo #194:30min Add tests for all the methods from CharacteristicsJavaParser class.
 *  The tests should be added to CharacteristicsJavaParserTest class for all
 *  methods from {@link com.github.lombrozo.testnames.javaparser.CharacteristicsJavaParser}.
 *  When we implement the tests, we should remove the that puzzle from this class.
 */
final class CharacteristicsJavaParserTest {

    @Test
    void createsCharacteristics() {
        MatcherAssert.assertThat(
            "Characteristics should be created successfully",
            JavaTestClasses.SIMPLE.toTestClass().characteristics(),
            Matchers.notNullValue()
        );
    }
}
