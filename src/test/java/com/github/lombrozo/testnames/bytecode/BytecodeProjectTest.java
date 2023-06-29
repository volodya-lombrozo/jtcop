package com.github.lombrozo.testnames.bytecode;

import com.github.lombrozo.testnames.ProductionClass;
import com.github.lombrozo.testnames.TestClass;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test cases for {@link BytecodeProject}.
 *
 * @since 0.1.17
 */
class BytecodeProjectTest {

    @Test
    void parsesGeneratedTestClasses(@TempDir final Path temp) throws Exception {
        final ResourceOf resource = new ResourceOf("generated/RuleTest.class");
        Files.write(temp.resolve("RuleTest.class"), new BytesOf(resource).asBytes());
        final Collection<TestClass> classes = new BytecodeProject(
            temp,
            temp
        ).testClasses();
        MatcherAssert.assertThat(
            String.format("We expect to parse only one  test class, but got: %s", classes),
            classes,
            Matchers.hasSize(1)
        );
        final String name = classes.iterator().next().name();
        final String expected = "RuleTest";
        MatcherAssert.assertThat(
            String.format("Expected name of the test class is %s, but was %s", expected, name),
            name,
            Matchers.equalTo(expected)
        );
    }

    @Test
    void parsesGeneratedProductionClasses(@TempDir final Path temp) throws Exception {
        final ResourceOf resource = new ResourceOf("generated/RuleName.class");
        Files.write(temp.resolve("RuleName.class"), new BytesOf(resource).asBytes());
        final Collection<ProductionClass> classes = new BytecodeProject(
            temp,
            temp
        ).productionClasses();
        MatcherAssert.assertThat(
            String.format("We expect to parse only one production class, but got: %s", classes),
            classes,
            Matchers.hasSize(1)
        );
        final String name = classes.iterator().next().name();
        final String expected = "RuleName";
        MatcherAssert.assertThat(
            String.format(
                "Expected name of the production class is %s, but was %s",
                expected,
                name
            ),
            name,
            Matchers.equalTo(expected)
        );
    }
}