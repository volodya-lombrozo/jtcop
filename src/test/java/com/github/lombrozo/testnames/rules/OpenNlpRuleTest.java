package com.github.lombrozo.testnames.rules;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.regex.Pattern;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import org.cactoos.io.InputOf;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class OpenNlpRuleTest {


    private static final Pattern CAMEL = Pattern.compile("(?=\\p{Lu})");
    private static InputStream stream;
    private static POSTaggerME model;

    @BeforeAll
    static void setUp() throws Exception {
        stream = new InputOf(Paths.get(
            "/Users/lombrozo/Workspace/OpenSource/learning/opennlp/en-pos-perceptron.bin"
        )).stream();
        model = new POSTaggerME(new POSModel(stream));
    }

    @AfterAll
    static void tearDown() throws IOException {
        stream.close();
    }

    @CsvSource({
        "buildsRequest",
        "checks",
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
        "answerWhenMentioned",
        "denyOutdatedTag",
        "rendersAbsentPages",
    })
    @ParameterizedTest
    void checksCorrectNames(final CharSequence name) {
        System.out.println(
            Arrays.toString(OpenNlpRuleTest.model.tag(OpenNlpRuleTest.CAMEL.split(name)))
        );
    }

}