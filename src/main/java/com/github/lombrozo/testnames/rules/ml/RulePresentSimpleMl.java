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

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.Rule;
import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.complaints.ComplaintWrongTestName;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import opennlp.tools.postag.POSTaggerME;

/**
 * Present simple tense rule with using of ML.
 *
 * @since 0.10
 */
public final class RulePresentSimpleMl implements Rule {

    /**
     * The pattern to split camel case.
     */
    private static final Pattern CAMEL = Pattern.compile("(?=\\p{Lu})");

    /**
     * The Open NLP tagger.
     */
    private final POSTaggerME model;

    /**
     * The test to check.
     */
    private final TestCase test;

    /**
     * The main constructor.
     * @param tagger The Open NLP tagger
     * @param tst The test to check
     */
    RulePresentSimpleMl(final POSTaggerME tagger, final TestCase tst) {
        this.model = tagger;
        this.test = tst;
    }

    @Override
    public Collection<Complaint> complaints() {
        final Tag[] parse = Tag.parse(
            this.model.tag(
                Stream
                    .concat(
                        Stream.of("It"),
                        Arrays.stream(RulePresentSimpleMl.CAMEL.split(this.test.name()))
                    ).map(s -> s.toLowerCase(Locale.ROOT))
                    .toArray(String[]::new)
            )
        );
        final Collection<Complaint> res;
        if (parse[1].isVerb()) {
            res = Collections.emptyList();
        } else {
            res = Collections.singleton(
                new ComplaintWrongTestName(
                    this.test,
                    "Test name should start with a verb"
                )
            );
        }
        return res;
    }
}
