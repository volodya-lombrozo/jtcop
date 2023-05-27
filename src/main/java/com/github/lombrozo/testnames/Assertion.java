package com.github.lombrozo.testnames;

import java.util.Optional;

/**
 * The assertion of the test method.
 */
public interface Assertion {

    /**
     * The assertion message explanation.
     * @return The assertion message explanation.
     */
    Optional<String> explanation();

}
