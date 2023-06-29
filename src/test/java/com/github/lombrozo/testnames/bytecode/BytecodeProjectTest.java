package com.github.lombrozo.testnames.bytecode;

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
import static org.junit.jupiter.api.Assertions.*;

class BytecodeProjectTest {

    @Test
    void parsesGeneratedClasses(@TempDir final Path temp) throws Exception {
        final ResourceOf resource = new ResourceOf("generated/RuleTest.class");
        Files.write(temp.resolve("RuleTest.class"), new BytesOf(resource).asBytes());
        final Collection<TestClass> classes = new BytecodeProject(temp, temp).testClasses();
        MatcherAssert.assertThat(
            "",
            classes,
            Matchers.hasSize(1)
        );
    }
}