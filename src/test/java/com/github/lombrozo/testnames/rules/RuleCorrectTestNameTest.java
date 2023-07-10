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

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * Unit test for {@link RuleCorrectTestName}.
 *
 * @since 0.1.17
 * @todo #195:30min Continue implementing RuleIntegrationTestName.
 *  The rule should successfully check that the name of an integration test class
 *  ends with "IT" or "ITCase". When the rule is ready, remove the
 *  puzzle and enable all the tests inside {@link RuleCorrectTestNameTest}.
 */
class RuleCorrectTestNameTest {

    @Test
    void createsCorrectly() {
        MatcherAssert.assertThat(
            "We expect that RuleCorrectTestName creates correctly, without any exceptions",
            new RuleCorrectTestName(),
            Matchers.notNullValue()
        );
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(strings = {"RulesIT", "RulesITCase", "ITCaseRules", "ITRules"})
    void checksCorrectITNames(final String name) {
        MatcherAssert.assertThat(
            String.format(
                "We expect that %s is a correct name for an integration test class",
                name
            ),
            new RuleCorrectTestName().complaints(),
            Matchers.empty()
        );
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(
        strings = {
            "RulesTest",
            "RulesTestCase",
            "TestCaseRules",
            "TestRules",
            "TestsRules",
            "RuleTests"
        }
    )
    void checksCorrectUnitNames(final String name) {
        MatcherAssert.assertThat(
            String.format(
                "We expect that %s is a correct name for a unit test class",
                name
            ),
            new RuleCorrectTestName().complaints(),
            Matchers.empty()
        );
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(
        strings = {
            "Rules",
            "RulesCase",
            "CaseRules",
            "StrangeITName",
            "StrangeITCaseName"
        }
    )
    void checksIncorrectITNames(final String name) {
        MatcherAssert.assertThat(
            String.format(
                "We expect that %s is a incorrect name for an integration test class",
                name
            ),
            new RuleCorrectTestName().complaints(),
            Matchers.not(Matchers.empty())
        );
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(
        strings = {
            "Complaints",
            "ComplaintsCase",
            "CaseComplaints",
            "StrangeTestName",
            "StrangeTestCaseName",
            "StrangeTestsCaseName",
            "StrangeCaseTestsName"
        }
    )
    void checksIncorrectUnitNames(final String name) {
        MatcherAssert.assertThat(
            String.format(
                "We expect that %s is a incorrect name for a unit test class",
                name
            ),
            new RuleCorrectTestName().complaints(),
            Matchers.not(Matchers.empty())
        );
    }
}
