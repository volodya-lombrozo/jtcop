package com.github.lombrozo.testnames.rules.ml;

import com.github.lombrozo.testnames.Case;
import java.io.IOException;
import opennlp.tools.postag.POSTaggerME;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class PresentSimpleMLRuleTest {

    private static POSTaggerME model;

    @BeforeAll
    static void setUp() throws IOException {
        PresentSimpleMLRuleTest.model = new POSTaggerME(new ModelSourceInternet().model());
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
        "rendersAbsentPages",
    })
    @ParameterizedTest
    void checksCorrectNames(final String name) {
        MatcherAssert.assertThat(
            new PresentSimpleMLRule(
                PresentSimpleMLRuleTest.model,
                new Case.FakeCase(name)
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
        "___",
    })
    @ParameterizedTest
    void checksWrongNames(final String name) {
        MatcherAssert.assertThat(
            new PresentSimpleMLRule(
                PresentSimpleMLRuleTest.model,
                new Case.FakeCase(name)
            ).complaints(),
            Matchers.not(Matchers.empty())
        );
    }

}