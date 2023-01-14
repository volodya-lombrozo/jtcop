package com.github.lombrozo.testnames;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class CompositeTestPathRuleTest {

    @Test
    void checksSeveralFilesInDirectorySuccessfully(@TempDir final Path temp) throws Exception {
        Files.write(temp.resolve("Test1.java"), new BytesOf(
            new ResourceOf("TestParameterized.java")).asBytes());
        Files.write(temp.resolve("Test2.java"), new BytesOf(
            new ResourceOf("TestSimple.java")).asBytes());
        try {
            new CompositeTestPathRule(temp).validate();
        } catch (final WrongTestName ex) {
            Assertions.fail(ex);
        }
    }

    @Test
    void checksBrokenClass(@TempDir final Path temp) throws Exception {
        Files.write(
            temp.resolve("Broken.java"),
            "class BrokenClass {".getBytes(StandardCharsets.UTF_8)
        );
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new CompositeTestPathRule(temp).validate()
        );
    }

    @Test
    void ignoresDirectoriesAndNonJavaFiles(@TempDir final Path temp) throws Exception {
        Files.write(
            temp.resolve("some.txt"),
            "Simple text".getBytes(StandardCharsets.UTF_8)
        );
        Files.createDirectories(temp.resolve("subdir").resolve("deep"));
        try {
            new CompositeTestPathRule(temp).validate();
        } catch (final WrongTestName ex) {
            Assertions.fail(ex);
        }
    }

    @Test
    void failsIfTestNameIsWrong(@TempDir final Path temp) throws Exception {
        Files.write(temp.resolve("Test3.java"), new BytesOf(
            new ResourceOf("TestWrongName.java")).asBytes());
        Assertions.assertThrows(
            WrongTestName.class,
            () -> new CompositeTestPathRule(temp).validate()
        );
    }
}
