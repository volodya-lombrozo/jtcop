package com.github.lombrozo.testnames.javaparser;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link CharacteristicsJavaParser}.
 *
 * @since 0.1.19
 * @todo #194:30min Add tests for all the methods from CharacteristicsJavaParser class.
 *  The tests should be added to CharacteristicsJavaParserTest class for all
 *  methods from {@link com.github.lombrozo.testnames.javaparser.CharacteristicsJavaParser}.
 *  When we implement the tests, we should remove the that puzzle from this class.
 */
final class CharacteristicsJavaParserTest {

    @Test
    void createsCharacteristics() {
        MatcherAssert.assertThat(
            "Characteristics should be created successfully",
            JavaTestClasses.SIMPLE.toTestClass().characteristics(),
            Matchers.notNullValue()
        );
    }

}