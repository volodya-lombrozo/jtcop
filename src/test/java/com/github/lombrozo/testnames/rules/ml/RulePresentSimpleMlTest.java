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
package com.github.lombrozo.testnames.rules.ml;

import com.github.lombrozo.testnames.TestCase;
import java.io.IOException;
import opennlp.tools.postag.POSTaggerME;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * Tests for {@link RulePresentSimpleMl}.
 *
 * @since 0.10
 */
@Disabled
class RulePresentSimpleMlTest {

    /**
     * Model for tests.
     */
    private static POSTaggerME model;

    @BeforeAll
    static void setUp() throws IOException {
        RulePresentSimpleMlTest.model = new POSTaggerME(new ModelSourceInternet().model());
    }

    @CsvSource({
        "buildsRequest",
        "checksCorrectName",
        "stopsBecauseCiChecksFailed",
        "locksBranch",
        "recognizesCommaAsDelimiter",
        "buildsReport",
        "unlocksBranch",
        "repliesInGithub",
        "repliesWithLinkToRevision",
        "addsAuthor",
        "doesNotAddAuthorToEmptyReq",
        "validatesReleaseVersion",
        "getsReferenceVersion",
        "makesPostOfGithubComments",
        "preventsSpam",
        "closesPullRequestForRebaseMode",
        "leavesPullRequestOpenWhenNoRebaseMode",
        "createsRelease",
        "rejectGetAssetWithNoFriends",
        "getExistAssets",
        "doesNotTouchRequestAndWire",
        "saySomethingBack",
        "answersWhenMentioned",
        "denyOutdatedTag",
        "rendersAbsentPages"
    })
    @ParameterizedTest
    void checksCorrectNames(final String name) {
        MatcherAssert.assertThat(
            new RulePresentSimpleMl(
                RulePresentSimpleMlTest.model,
                new TestCase.Fake(name)
            ).complaints(),
            Matchers.empty()
        );
    }

    @CsvSource({
        "building",
        "chicken",
        "chickenBuildsBuilding",
        "test",
        "itIsTrue",
        "common",
        "pack",
        "_",
        "_&",
        "...",
        "___"
    })
    @ParameterizedTest
    void checksWrongNames(final String name) {
        MatcherAssert.assertThat(
            new RulePresentSimpleMl(
                RulePresentSimpleMlTest.model,
                new TestCase.Fake(name)
            ).complaints(),
            Matchers.not(Matchers.empty())
        );
    }
}
