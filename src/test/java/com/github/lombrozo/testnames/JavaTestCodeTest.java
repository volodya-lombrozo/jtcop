package com.github.lombrozo.testnames;

import java.nio.file.Paths;
import java.util.Collection;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JavaTestCodeTest {

    @Test
    void names() {
        final Collection<String> names = new JavaTestCode(
            Paths.get(
                "/Users/lombrozo/Workspace/OpenSource/test-naming-conventions/src/test/java/com/github/lombrozo/testnames/PatternTest.java")
        ).names();
        System.out.println(names);
        assertNotNull(names);
        assertFalse(names.isEmpty());
    }
}