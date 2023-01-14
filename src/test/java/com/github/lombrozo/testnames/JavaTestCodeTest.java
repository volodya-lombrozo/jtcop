package com.github.lombrozo.testnames;

import java.nio.file.Files;
import java.nio.file.Path;
import org.cactoos.bytes.BytesOf;
import org.cactoos.io.ResourceOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class JavaTestCodeTest {

    @Test
    void getsNamesSuccessfully(@TempDir final Path temp) throws Exception {
        final Path path = temp.resolve("TestExample.java");
        Files.write(path, new BytesOf(new ResourceOf("TestExample.java")).asBytes());
        final Names names = new JavaTestCode(path);
        MatcherAssert.assertThat(
            names.names(),
            Matchers.containsInAnyOrder("creates", "removes", "updates")
        );
    }

}