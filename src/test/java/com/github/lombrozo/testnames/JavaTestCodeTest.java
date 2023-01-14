package com.github.lombrozo.testnames;

import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JavaTestCodeTest {

    @Test
    void getsNamesFromSimpleTest(@TempDir final Path temp) throws Exception {
        final String java = "TestSimple.java";
        final Path path = temp.resolve(java);
        Files.write(path, new BytesOf(new ResourceOf(java)).asBytes());
        final Names names = new JavaTestCode(path);
        MatcherAssert.assertThat(
            names.names(),
            Matchers.containsInAnyOrder("creates", "removes", "updates")
        );
    }

    @Test
    void getsNamesFromParameterizedTest(@TempDir final Path temp) throws Exception {
        final String java = "TestParameterized.java";
        final Path path = temp.resolve(java);
        Files.write(path, new BytesOf(new ResourceOf(java)).asBytes());
        final Names names = new JavaTestCode(path);
        MatcherAssert.assertThat(
            names.names(),
            Matchers.containsInAnyOrder("checksCases")
        );
    }

    @Test
    void throwsExceptionIfFileNotFound(@TempDir final Path temp) throws Exception {
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new JavaTestCode(temp.resolve("TestNotFound.java")).names()
        );
    }

    @Test
    void throwsExceptionIfFileIsNotJava(@TempDir final Path temp) throws Exception {
        final Path path = temp.resolve("TestNotJava.txt");
        Files.write(path, "Not Java".getBytes());
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new JavaTestCode(path).names()
        );
    }

}