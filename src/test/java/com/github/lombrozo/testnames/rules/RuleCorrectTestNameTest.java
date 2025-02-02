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
package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.TestClass;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit test for {@link RuleCorrectTestName}.
 *
 * @since 0.1.17
 */
final class RuleCorrectTestNameTest {

    @Test
    void createsCorrectly() {
        MatcherAssert.assertThat(
            "We expect that RuleCorrectTestName creates correctly, without any exceptions",
            new RuleCorrectTestName(new TestClass.Fake("CorrectTest")),
            Matchers.notNullValue()
        );
    }

    @ParameterizedTest
    @ValueSource(
        strings = {
            "RulesIT",
            "RulesITCase",
            "ITCaseRules",
            "ITRules",
            "RulesIT.java",
            "RulesITCase.class",
            "ITCaseRules.java",
            "ITRules.class"
        }
    )
    void checksCorrectITNames(final String name) {
        MatcherAssert.assertThat(
            String.format(
                "We expect that %s is a correct name for an integration test class",
                name
            ),
            new RuleCorrectTestName(new TestClass.Fake(name)).complaints(),
            Matchers.empty()
        );
    }

    @ParameterizedTest
    @ValueSource(
        strings = {
            "RulesTest",
            "RulesTestCase",
            "TestCaseRules",
            "TestRules",
            "TestsRules",
            "RuleTests",
            "RulesTest.java",
            "RulesTestCase.class",
            "TestCaseRules.java",
            "TestRules.class",
            "TestsRules.java",
            "RuleTests.class"
        }
    )
    void checksCorrectUnitNames(final String name) {
        MatcherAssert.assertThat(
            String.format(
                "We expect that %s is a correct name for a unit test class",
                name
            ),
            new RuleCorrectTestName(new TestClass.Fake(name)).complaints(),
            Matchers.empty()
        );
    }

    @ParameterizedTest
    @ValueSource(
        strings = {
            "Rules",
            "RulesCase",
            "CaseRules",
            "StrangeITName",
            "StrangeITCaseName",
            "Rules.class",
            "RulesCase.class",
            "CaseRules.class",
            "StrangeITName.java",
            "StrangeITCaseName.java"
        }
    )
    void checksIncorrectITNames(final String name) {
        MatcherAssert.assertThat(
            String.format(
                "We expect that %s is a incorrect name for an integration test class",
                name
            ),
            new RuleCorrectTestName(new TestClass.Fake(name)).complaints(),
            Matchers.not(Matchers.empty())
        );
    }

    @ParameterizedTest
    @ValueSource(
        strings = {
            "Complaints",
            "ComplaintsCase",
            "CaseComplaints",
            "StrangeTestName",
            "StrangeTestCaseName",
            "StrangeTestsCaseName",
            "StrangeCaseTestsName",
            "Complaints.java",
            "ComplaintsCase.java",
            "CaseComplaints.java",
            "StrangeTestName.java",
            "StrangeTestCaseName.class",
            "StrangeTestsCaseName.class",
            "StrangeCaseTestsName.class"
        }
    )
    void checksIncorrectUnitNames(final String name) {
        MatcherAssert.assertThat(
            String.format(
                "We expect that %s is a incorrect name for a unit test class",
                name
            ),
            new RuleCorrectTestName(new TestClass.Fake(name)).complaints(),
            Matchers.not(Matchers.empty())
        );
    }
}
