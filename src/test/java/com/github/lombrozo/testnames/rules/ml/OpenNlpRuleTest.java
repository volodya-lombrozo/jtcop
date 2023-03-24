package com.github.lombrozo.testnames.rules.ml;

import com.github.lombrozo.testnames.Case;
import java.io.IOException;
import java.nio.file.Paths;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OpenNlpRuleTest {

    private static POSTaggerME model;

    @BeforeAll
    static void setUp() throws IOException {
        model = new POSTaggerME(new POSModel(Paths.get(
            "/Users/lombrozo/Workspace/OpenSource/learning/opennlp/en-pos-perceptron.bin"
        ).toFile()));
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
        "doesntAddAuthorToEmptyReq",
        "validatesReleaseVersion",
        "getsReferenceVersion",
        "postsGithubComment",
        "preventsSpam",
        "closesPullRequestForRebaseMode",
        "leavesPullRequestOpenWhenNoRebaseMode",
        "createsRelease",
        "rejectGetAssetWithNoFriends",
        "getExistAssets",
        "doesntTouchRequestAndWire",
        "saySomethingBack",
        "answersWhenMentioned",
        "denyOutdatedTag",
        "rendersAbsentPages",
    })
    @ParameterizedTest
    void checksCorrectNames(final String name) {
        MatcherAssert.assertThat(
            new OpenNlpRule(OpenNlpRuleTest.model, new Case.FakeCase(name)).complaints(),
            Matchers.empty()
        );
    }

}