package com.github.lombrozo;

/**
 * JUnit extension.
 *
 * @since 0.1.17
 */
public class JUnitExtension {

    /**
     * Fully qualified name of the JUnit extension.
     */
    private final String name;

    public JUnitExtension(final Class<?> klass) {
        this(klass.getPackage().getName());
    }

    public JUnitExtension(final String name) {
        this.name = name;
    }

    public boolean isJUnitExtension() {
        return this.name.startsWith("org.junit.jupiter.api.extension");
    }
}
