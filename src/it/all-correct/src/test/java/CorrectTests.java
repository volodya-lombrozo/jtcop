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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.nio.file.Path;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Arrays;
import org.junit.jupiter.params.ParameterizedTest;
import org.hamcrest.MatcherAssert;

/**
 * Test class.
 *
 * @since 1.4
 */
@SuppressWarnings("JTCOP.RuleAllTestsHaveProductionClass")
public class CorrectTests {

    /**
     * This is the test for the issue #471.
     * You can read more about the issue right here:
     * https://github.com/volodya-lombrozo/jtcop/issues/471
     */
    @Test
    void routesNotWhenNotMatchAndNoSpareCmd() {
        MatcherAssert.assertThat(
            "Routes to command that not matched",
            true
        );
    }

    /**
     * This is the test for the issue #482.
     * You can read more about the issue right here:
     * https://github.com/volodya-lombrozo/jtcop/issues/482
     */
    @Test
    void determinesTypesCorrectly() {
        MatcherAssert.assertThat(
            "Routes to command that not matched",
            new BooleanScalar().value()
        );
    }

    /**
     * This is test for the issue #453.
     * You can read more about the issue right here:
     * https://github.com/volodya-lombrozo/jtcop/issues/453
     */
    @ParameterizedTest(name = "Generates programs for {0} grammar with top rule {1}")
    @MethodSource("syntax")
    void generatesSyntaxForGrammar(
        final List<String> definitions,
        final String top,
        @TempDir final Path tmp
    ) {
        String[] programs = definitions.stream().toArray(String[]::new);
        String message = "We expect that the randomly generated code will be verified without errors";
        try {
            Assertions.assertDoesNotThrow(
                () -> Stream.generate(() -> top)
                    .limit(50)
                    .peek(System.out::println)
                    .count(),
                message
            );
        } catch (Exception exception) {
            Assertions.fail(message, exception);
        }
    }

    static Stream<Arguments> syntax() {
        return Stream.of(
            Arguments.of(Arrays.asList("rule", "rule", "rule"), "rule"),
            Arguments.of(Arrays.asList("rule", "rule", "rule"), "rule"),
            Arguments.of(Arrays.asList("rule", "rule", "rule"), "rule")
        );
    }
}
