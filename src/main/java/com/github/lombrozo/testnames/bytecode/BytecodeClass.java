package com.github.lombrozo.testnames.bytecode;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javassist.ClassPool;
import javassist.bytecode.ClassFile;

final class BytecodeClass {

    private final Path path;

    public BytecodeClass(final Path path) {
        this.path = path;
    }

    boolean isClass() {
        return this.path.getFileName().toString().endsWith(".class");
    }

    BytecodeTest toTest() {
        try {
            return new BytecodeTest(
                this.path,
                ClassPool.getDefault()
                    .makeClass(
                        new ClassFile(
                            new DataInputStream(
                                Files.newInputStream(this.path.toFile().toPath()
                                )
                            )
                        )
                    )
            );
        } catch (final IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
