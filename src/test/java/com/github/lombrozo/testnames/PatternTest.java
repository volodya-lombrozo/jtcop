package com.github.lombrozo.testnames;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PatternTest {

    @ParameterizedTest
    @CsvSource({
        "test, false",
        "canTest, false",
        "invokesLastSuccessfully, true"
    })
    public void validatesCorrectly(String name, boolean expected) {
        Assertions.assertEquals(expected, new Pattern(name).valid());
    }
}