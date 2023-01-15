package com.github.lombrozo.testnames;

import java.util.Arrays;
import java.util.Collection;

@FunctionalInterface
public interface Cases {

    Collection<TestCase> all();

    static Cases correct() {
        return () -> Arrays.asList(
            new TestCase.FakeCase("removes"),
            new TestCase.FakeCase("creates")
        );
    }

    static Cases wrong(){
        return () -> Arrays.asList(
            new TestCase.FakeCase("remove"),
            new TestCase.FakeCase("test")
        );
    }
}
