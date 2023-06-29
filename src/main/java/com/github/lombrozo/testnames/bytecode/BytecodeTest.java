package com.github.lombrozo.testnames.bytecode;

import com.github.lombrozo.testnames.TestCase;
import com.github.lombrozo.testnames.TestClass;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import javassist.CtClass;

public class BytecodeTest implements TestClass {

    private final Path path;
    private final CtClass klass;

    BytecodeTest(
        final Path path,
        final CtClass klass
    ) {
        this.path = path;
        this.klass = klass;
    }

    @Override
    public String name() {
        return this.klass.getSimpleName();
    }

    @Override
    public Collection<TestCase> all() {
        return null;
    }

    @Override
    public Path path() {
        return this.path;
    }

    @Override
    public Collection<String> suppressed() {
        return Collections.emptyList();
    }
}
