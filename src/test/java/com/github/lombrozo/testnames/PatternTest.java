package com.github.lombrozo.testnames;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PatternTest {

    @ParameterizedTest
    @CsvSource({
        "test, false",
        "canTest, false",
        "execSuccessfully, false",
        "fullCompilingLifecycleSuccessfully, false",
        "_execsCorrectly, false",
        "$execsCorrectly, false",
        "sTestMy, false",
        "sTesTMy, false",
        "sTESTMy, false",
        "$sAnotherCase, false",
        "_sCrack, false",
        "ssssText, false",
        "testContentOfCachedFile, false",
        "testContentOfNoCacheFile, false",
        "testPacks, false",
        "bigSlowTest, false",
        "existsTest, false",
        "existsInDirTest, false",
        "existsInDirDifferentEncryptionTest, false",
        "InvokesLastSuccessfully, false",
        "invokesLastSuccessfully, true",
        "readsHashByNonExistedTag, true",
        "readsCorrectHashByTagFromSimpleString, true",
        "readsCorrectHashByTagFromFile, true",
        "copiesSources, true",
        "findsDirs, true",
        "executesDiscoveryPhaseForCorrectEoPrograms, true",
        "readsFromExistingFile, true",
        "saves, true",
        "returnsRelativePathOfCurrentWorkingDirectory, true"
    })
    public void validatesCorrectly(String name, boolean expected) {
        Assertions.assertEquals(expected, new Pattern(name).valid());
    }
}