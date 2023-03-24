package com.github.lombrozo.testnames.rules.ml;

import com.github.lombrozo.testnames.Case;
import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.complaints.WrongTestName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import opennlp.tools.postag.POSTaggerME;

public class OpenNlpRule implements Rule {

    private static final Pattern CAMEL = Pattern.compile("(?=\\p{Lu})");
    private final POSTaggerME model;
    private final Case test;

    OpenNlpRule(final POSTaggerME tagger, final Case tst) {
        this.model = tagger;
        this.test = tst;
    }

    @Override
    public Collection<Complaint> complaints() {
        final String[] words = Stream.concat(
                Stream.of("He"),
                Arrays.stream(OpenNlpRule.CAMEL.split(this.test.name()))
            )
            .map(s -> s.toLowerCase(Locale.ROOT))
            .toArray(String[]::new);
        System.out.println(Arrays.deepToString(words));
        final String[] tags = this.model.tag(words);
        System.out.println(Arrays.deepToString(tags));
        final Tag[] parse = Tag.parse(tags);
        System.out.println(Arrays.deepToString(parse));
        final ArrayList<Complaint> res = new ArrayList<>();
        if (!parse[1].isVerb()) {
            res.add(new WrongTestName(this.test, "Test name should start with verb"));
        }
        return res;
    }
}
