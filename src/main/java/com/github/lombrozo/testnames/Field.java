/*
 * MIT License
 *
 * Copyright (c) 2022-2025 Volodya Lombrozo
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.github.lombrozo.testnames;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Field abstraction.
 * @since 1.4
 */
public interface Field {

    /**
     * Name of field.
     * @return Name of field.
     */
    String name();

    /**
     * Is field static.
     * @return True if field is static.
     */
    boolean isStatic();

    /**
     * Suppressed rules.
     * @return Suppressed rules.
     */
    List<String> suppressed();

    /**
     * Constant fake field.
     * @since 1.4
     */
    final class Fake implements Field {

        @Override
        public String name() {
            return "fake";
        }

        @Override
        public boolean isStatic() {
            return false;
        }

        @Override
        public List<String> suppressed() {
            return Collections.emptyList();
        }
    }

    /**
     * Named field.
     * @since 1.4
     */
    final class Named implements Field {

        /**
         * Name of field.
         */
        private final String name;

        /**
         * Origin field.
         */
        private final Field origin;

        /**
         * Constructor.
         * @param name Name of field.
         */
        public Named(final String name) {
            this(name, new Field.Fake());
        }

        /**
         * Constructor.
         * @param name Name of field.
         * @param origin Origin field.
         */
        public Named(final String name, final Field origin) {
            this.name = name;
            this.origin = origin;
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public boolean isStatic() {
            return this.origin.isStatic();
        }

        @Override
        public List<String> suppressed() {
            return Collections.emptyList();
        }
    }

    /**
     * Static field.
     * @since 1.4
     */
    final class Static implements Field {

        /**
         * Delegate.
         */
        private final Field origin;

        /**
         * Constructor.
         */
        public Static() {
            this(new Field.Fake());
        }

        /**
         * Constructor.
         * @param origin Origin field.
         */
        public Static(final Field origin) {
            this.origin = origin;
        }

        @Override
        public String name() {
            return this.origin.name();
        }

        @Override
        public boolean isStatic() {
            return true;
        }

        @Override
        public List<String> suppressed() {
            return Collections.emptyList();
        }
    }

    /**
     * Field with suppressed rules.
     * @since 1.4
     */
    final class Suppressed implements Field {

        /**
         * Delegate.
         */
        private final Field origin;

        /**
         * Suppressed rules.
         */
        private final List<String> rules;

        /**
         * Constructor.
         * @param origin Origin field.
         * @param suppressed Suppressed rules.
         */
        public Suppressed(final Field origin, final String... suppressed) {
            this(origin, Arrays.asList(suppressed));
        }

        /**
         * Constructor.
         * @param origin Origin field.
         * @param suppressed Suppressed rules.
         */
        Suppressed(final Field origin, final List<String> suppressed) {
            this.origin = origin;
            this.rules = suppressed;
        }

        @Override
        public String name() {
            return this.origin.name();
        }

        @Override
        public boolean isStatic() {
            return this.origin.isStatic();
        }

        @Override
        public List<String> suppressed() {
            return Collections.unmodifiableList(this.rules);
        }
    }
}
