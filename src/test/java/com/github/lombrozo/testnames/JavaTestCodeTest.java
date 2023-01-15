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
        final Cases cases = new JavaTestCode(path);
        MatcherAssert.assertThat(
            cases.all(),
            Matchers.containsInAnyOrder(
                new JavaParserCase(
                    "TestSimple",
                    "creates",
                    path
                ), new JavaParserCase(
                    "TestSimple",
                    "removes",
                    path
                ),
                new JavaParserCase(
                    "TestSimple",
                    "updates",
                    path
                )
            )
        );
    }

    @Test
    void getsNamesFromParameterizedTest(@TempDir final Path temp) throws Exception {
        final String java = "TestParameterized.java";
        final Path path = temp.resolve(java);
        Files.write(path, new BytesOf(new ResourceOf(java)).asBytes());
        final Cases cases = new JavaTestCode(path);
        MatcherAssert.assertThat(
            cases.all(),
            Matchers.containsInAnyOrder(
                new JavaParserCase(
                    "TestParameterized",
                    "checksCases",
                    path
                )
            )
        );
    }

    @Test
    void throwsExceptionIfFileNotFound(@TempDir final Path temp) throws Exception {
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new JavaTestCode(temp.resolve("TestNotFound.java")).all()
        );
    }

    @Test
    void throwsExceptionIfFileIsNotJava(@TempDir final Path temp) throws Exception {
        final Path path = temp.resolve("TestNotJava.txt");
        Files.write(path, "Not Java".getBytes());
        Assertions.assertThrows(
            IllegalStateException.class,
            () -> new JavaTestCode(path).all()
        );
    }


}