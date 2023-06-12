package com.github.lombrozo.testnames.javaparser;

import com.github.javaparser.ast.expr.MethodCallExpr;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class AssertionOfJavaParserTest {

    private static final String EXACTLY_FORM = "Expected exactly %d assertions, but was %d";

    @Test
    void parsesJunitAssertionOnly() {
        final List<AssertionOfJavaParser> all = AssertionOfJavaParserTest.method("junit")
            .map(AssertionOfJavaParser::new)
            .filter(AssertionOfJavaParser::isAssertion)
            .collect(Collectors.toList());
        final int expected = 1;
        MatcherAssert.assertThat(
            String.format(AssertionOfJavaParserTest.EXACTLY_FORM, expected, all.size()),
            all,
            Matchers.hasSize(expected)
        );
    }

    @Test
    void parsesHamcrestAssertionOnly() {
        final List<AssertionOfJavaParser> all = AssertionOfJavaParserTest
            .method("hamcrestAssertion")
            .map(AssertionOfJavaParser::new)
            .filter(AssertionOfJavaParser::isAssertion)
            .collect(Collectors.toList());
        final int expected = 2;
        MatcherAssert.assertThat(
            String.format(AssertionOfJavaParserTest.EXACTLY_FORM, expected, all.size()),
            all,
            Matchers.hasSize(expected)
        );
    }

    @Test
    void parsesSeveralAssertionsFromDifferentLibraries() {
        final List<AssertionOfJavaParser> all = AssertionOfJavaParserTest
            .method("severalFrameworks")
            .map(AssertionOfJavaParser::new)
            .filter(AssertionOfJavaParser::isAssertion)
            .collect(Collectors.toList());
        final int expected = 4;
        MatcherAssert.assertThat(
            String.format(AssertionOfJavaParserTest.EXACTLY_FORM, expected, all.size()),
            all,
            Matchers.hasSize(expected)
        );
    }

    @Test
    void extractMessagesFromAllAssertions() {
        final List<AssertionOfJavaParser> all = AssertionOfJavaParserTest
            .method("severalFrameworks")
            .map(AssertionOfJavaParser::new)
            .filter(AssertionOfJavaParser::isAssertion)
            .collect(Collectors.toList());
        MatcherAssert.assertThat(
            String.format(
                "All assertions should have messages, but here are the assertions without messages: %s",
                all.stream()
                    .filter(assertion -> !assertion.explanation().isPresent())
                    .collect(Collectors.toList())
            ),
            all.stream()
                .map(AssertionOfJavaParser::explanation)
                .allMatch(Optional::isPresent),
            Matchers.is(true)
        );
    }

    @Test
    void parsesAssertionsWithoutMessage() {
        final List<AssertionOfJavaParser> all = AssertionOfJavaParserTest.method(
                "assertionsWithoutMesssages")
            .map(AssertionOfJavaParser::new)
            .filter(AssertionOfJavaParser::isAssertion)
            .collect(Collectors.toList());
        MatcherAssert.assertThat(
            String.format(
                "We expect, that all assertions won't have messages, but was %s",
                all.stream()
                    .filter(assertion -> assertion.explanation().isPresent())
                    .collect(Collectors.toList())
            ),
            all.stream()
                .map(AssertionOfJavaParser::explanation)
                .noneMatch(Optional::isPresent),
            Matchers.is(true)
        );
    }

    private static Stream<MethodCallExpr> method(final String name) {
        return JavaTestClasses.TEST_WITH_ASSERTIONS.toJavaParserClass()
            .methods(new ByName(name))
            .findFirst()
            .orElseThrow(() -> new MethodNotFound(name))
            .statements();
    }

}