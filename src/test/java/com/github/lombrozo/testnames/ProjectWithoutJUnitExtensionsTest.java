package com.github.lombrozo.testnames;

import org.junit.jupiter.api.Test;

class ProjectWithoutJUnitExtensionsTest {

    @Test
    void returnsProductionClassesFromOrigin() {

    }

    @Test
    void filtersJUnitExtensions() {
        new ProjectWithoutJUnitExtensions(
            new Project.Fake()
        );
    }

    @Test
    void skipsNotJUnitExtensions() {
        new ProjectWithoutJUnitExtensions(
            new Project.Fake()
        );
    }

}