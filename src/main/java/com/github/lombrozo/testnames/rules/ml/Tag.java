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
package com.github.lombrozo.testnames.rules.ml;

import java.util.Arrays;
import lombok.ToString;

/**
 * Penn Treebank tag set.
 * You can read about it
 * <a href="https://www.eecis.udel.edu/~vijay/cis889/ie/pos-set.pdf">here</a>
 *
 * @since 0.2
 */
@ToString
public enum Tag {

    /**
     * Coordinating conjunction.
     * Example: and, or, but.
     */
    CC("CC"),

    /**
     * Cardinal number.
     * Example: one, two, 2, 1.5.
     */
    CD("CD"),

    /**
     * Determiner.
     * Example: the, a, some, most.
     */
    DT("DT"),

    /**
     * Existential there.
     * Example: there is.
     */
    EX("EX"),

    /**
     * Foreign word.
     * Example: d’hoevre.
     */
    FW("FW"),

    /**
     * Preposition or subordinating conjunction.
     * Example: of, on, in, by, that.
     */
    IN("IN"),

    /**
     * Adjective.
     * Example: big, red, high.
     */
    JJ("JJ"),

    /**
     * Adjective, comparative.
     * Example: bigger, redder, higher.
     */
    JJR("JJR"),

    /**
     * Adjective, superlative.
     * Example: biggest, reddest, highest.
     */
    JJS("JJS"),

    /**
     * List item marker.
     * Example: 1), 2), 3).
     */
    LS("LS"),

    /**
     * Modal.
     * Example: can, could, may, might, must, will.
     */
    MD("MD"),

    /**
     * Noun, singular or mass.
     * Example: dog, cat, tree, air, beauty.
     */
    NN("NN"),

    /**
     * Noun, plural.
     * Example: dogs, cats, trees, berries, beauties.
     */
    NNS("NNS"),

    /**
     * Proper noun, singular.
     * Example: Germany, God, Alice, Africa, Asia.
     */
    NNP("NNP"),

    /**
     * Proper noun, plural.
     * Example: Vikings, Gods, Alices, Monkeys.
     */
    NNPS("NNPS"),

    /**
     * Predeterminer.
     * Example: all, both, half.
     */
    PDT("PDT"),

    /**
     * Possessive ending.
     * Example: ’s, ’.
     */
    POS("POS"),

    /**
     * Personal pronoun.
     * Example: I, you, he, she, it, we, they.
     */
    PP("PP"),

    /**
     * Possessive pronoun.
     * Example: my, your, his, her, its, our, their.
     */
    PPZ("PPZ"),

    /**
     * Personal pronoun.
     * Example: me, you, him, her, it, us, them.
     */
    PRP("PRP"),

    /**
     * Adverb.
     * Example: very, tomorrow, down, where, there.
     */
    RB("RB"),

    /**
     * Adverb, comparative.
     * Example: better, faster, harder.
     */
    RBR("RBR"),

    /**
     * Adverb, superlative.
     * Example: best, fastest, hardest.
     */
    RBS("RBS"),

    /**
     * Particle.
     * Example: about, off, up, over, with.
     */
    RP("RP"),

    /**
     * Sentence terminator.
     * Example: ., !, ?.
     */
    SENT("SENT"),

    /**
     * Symbol.
     * Example: %, $, #, +, =, :.
     */
    SYM("TO"),

    /**
     * To.
     * Example: to.
     */
    TO("TO"),

    /**
     * Interjection.
     * Example: oh, oops, gosh, wow, ouch.
     */
    UH("UH"),

    /**
     * Verb be, base form.
     * Example: be.
     */
    VB("VB"),

    /**
     * Verb be, past tense.
     * Example: was, were.
     */
    VBD("VBD"),

    /**
     * Verb be, gerund or present participle.
     * Example: being.
     */
    VBG("VBG"),

    /**
     * Verb be, past participle.
     * Example: been.
     */
    VBN("VBN"),

    /**
     * Verb be, sing. present, non-3d
     * Example: am.
     */
    VBP("VBP"),

    /**
     * Verb be, 3rd person sing. present.
     * Example: is.
     */
    VBZ("VBZ"),

    /**
     * Verb have, base form.
     * Example: have.
     */
    VH("VH"),

    /**
     * Verb have, past tense.
     * Example: had.
     */
    VHD("VHD"),

    /**
     * Verb have, gerund or present participle.
     * Example: having.
     */
    VHG("VHG"),

    /**
     * Verb have, past participle.
     * Example: had.
     */
    VHN("VHN"),

    /**
     * Verb have, sing. present, non-3d.
     * Example: have.
     */
    VHP("VHP"),

    /**
     * Verb have, 3rd person sing. present.
     * Example: has.
     */
    VHZ("VHZ"),

    /**
     * Verb, base form.
     * Example: take.
     */
    VV("VV"),

    /**
     * Verb, past tense.
     * Example: took.
     */
    VVD("VVD"),

    /**
     * Verb, gerund or present participle.
     * Example: taking.
     */
    VVG("VVG"),

    /**
     * Verb, past participle.
     * Example: taken.
     */
    VVN("VVN"),

    /**
     * Verb, sing. present, non-3d.
     * Example: take.
     */
    VVP("VVP"),

    /**
     * Verb, 3rd person sing. present.
     * Example: takes.
     */
    VVZ("VVZ"),

    /**
     * Wh-determiner.
     * Example: which, what, whatever, whichever.
     */
    WWD("WWD"),

    /**
     * Wh-pronoun.
     * Example: who, whom, whose, which.
     */
    WP("WP"),

    /**
     * Possessive wh-pronoun.
     * Example: whose.
     */
    WP_DOLLAR("WP$"),

    /**
     * Wh-adverb.
     * Example: how, where, when.
     */
    WRB("WRB"),

    /**
     * Hash #.
     * Example: #.
     */
    HASH("#"),

    /**
     * Dollar $.
     * Example: $.
     */
    DOLLAR("$"),

    /**
     * Quotation mark.
     * Example: ".
     */
    QM("\""),

    /**
     * Opening quotation mark.
     * Example: ``.
     */
    OQM("``"),

    /**
     * Opening bracket.
     * Example: (.
     */
    OB("("),

    /**
     * Closing bracket.
     * Example: ).
     */
    CB(")"),

    /**
     * Comma.
     * Example: ,.
     */
    COMMA(","),

    /**
     * Punctuation.
     * Example: – ; : — ….
     */
    PUNCTUATION(":"),

    /**
     * Unknown.
     */
    UNKNOWN("UNKNOWN");

    /**
     * Tag.
     */
    private final String tag;

    /**
     * Ctor.
     * @param tag Tag
     */
    Tag(final String tag) {
        this.tag = tag;
    }

    /**
     * Is this tag a verb?
     * @return True if this tag is a verb
     */
    boolean isVerb() {
        return Arrays.asList(Tag.VB, Tag.VBP, Tag.VBZ).contains(this);
    }

    /**
     * Parse raw tags.
     * @param tags Raw tags
     * @return Tags
     */
    static Tag[] parse(final String... tags) {
        return Arrays.stream(tags).map(Tag::parse).toArray(Tag[]::new);
    }

    /**
     * Parse raw tag.
     * @param tag Raw tag
     * @return Tag
     */
    private static Tag parse(final String tag) {
        return Arrays.stream(Tag.values())
            .filter(t -> t.tag.equals(tag))
            .findFirst().orElse(Tag.UNKNOWN);
    }
}
