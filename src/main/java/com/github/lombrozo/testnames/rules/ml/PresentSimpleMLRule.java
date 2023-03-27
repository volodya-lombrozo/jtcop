package com.github.lombrozo.testnames.rules.ml;

import com.github.lombrozo.testnames.Case;
import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.complaints.WrongTestName;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import opennlp.tools.postag.POSTaggerME;

public class PresentSimpleMLRule implements Rule {

    private static final Pattern CAMEL = Pattern.compile("(?=\\p{Lu})");
    private final POSTaggerME model;
    private final Case test;

    PresentSimpleMLRule(final POSTaggerME tagger, final Case tst) {
        this.model = tagger;
        this.test = tst;
    }

    @Override
    public Collection<Complaint> complaints() {
        final Tag[] parse = Tag.parse(
            this.model.tag(
                Stream.concat(
                        Stream.of("It"),
                        Arrays.stream(PresentSimpleMLRule.CAMEL.split(this.test.name()))
                    ).map(s -> s.toLowerCase(Locale.ROOT))
                    .toArray(String[]::new)
            )
        );
        final Collection<Complaint> res;
        if (parse[1].isVerb()) {
            res = Collections.emptyList();
        } else {
            res = Collections.singleton(
                new WrongTestName(
                    this.test,
                    "Test name should start with a verb"
                )
            );
        }
        return res;
    }
}
