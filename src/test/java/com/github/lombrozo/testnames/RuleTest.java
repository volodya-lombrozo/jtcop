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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Test case for {@link Rule}.
 *
 * @since 0.1.0
 */
final class RuleTest {

    @ParameterizedTest
    @CsvSource({
        "test, false",
        "canTest, false",
        "execSuccessfully, false",
        "fullCompilingLifecycleSuccessfully, false",
        "_execsCorrectly, false",
        "$execsCorrectly, false",
        "sTestMy, false",
        "sTesTMy, false",
        "sTESTMy, false",
        "$sAnotherCase, false",
        "_sCrack, false",
        "ssssText, false",
        "testContentOfCachedFile, false",
        "testContentOfNoCacheFile, false",
        "testPacks, false",
        "bigSlowTest, false",
        "existsTest, false",
        "existsInDirTest, false",
        "existsInDirDifferentEncryptionTest, false",
        "InvokesLastSuccessfully, false",
        "invokesLastSuccessfully, true",
        "readsHashByNonExistedTag, true",
        "readsCorrectHashByTagFromSimpleString, true",
        "readsCorrectHashByTagFromFile, true",
        "copiesSources, true",
        "findsDirs, true",
        "executesDiscoveryPhaseForCorrectEoPrograms, true",
        "readsFromExistingFile, true",
        "saves, true",
        "returnsRelativePathOfCurrentWorkingDirectory, true"
    })
    void validatesCorrectly(final String name, final boolean expected) {
        try {
            new PresentSimpleRule(new TestCase.FakeCase(name)).validate();
            Assertions.assertTrue(expected);
        } catch (final WrongTestName ex) {
            Assertions.assertFalse(expected);
        }
    }
}
