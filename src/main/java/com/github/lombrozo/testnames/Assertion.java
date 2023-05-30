package com.github.lombrozo.testnames;

import java.util.Optional;
import java.util.UUID;

/**
 * The assertion of the test method.
 */
public interface Assertion {

    /**
     * The assertion message explanation.
     * @return The assertion message explanation.
     */
    Optional<String> explanation();


    /**
     * Fake assertion.
     *
     * @since 0.1.15
     */
    class Fake implements Assertion {

        private final String message;

        public Fake() {
            this(UUID.randomUUID().toString());
        }

        public Fake(final String msg) {
            this.message = msg;
        }

        @Override
        public Optional<String> explanation() {
            return Optional.ofNullable(this.message);
        }
    }

    /**
     * Empty assertion.
     *
     * @since 0.1.15
     */
    class Empty implements Assertion {

        @Override
        public Optional<String> explanation() {
            return Optional.empty();
        }
    }

}
