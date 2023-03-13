package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.Case;
import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.Rule;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.regex.Pattern;
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
        final String[] tags = this.model.tag(OpenNlpRule.CAMEL.split(this.test.name()));
        System.out.println(Arrays.toString(tags));
        return Collections.emptyList();
    }
}
