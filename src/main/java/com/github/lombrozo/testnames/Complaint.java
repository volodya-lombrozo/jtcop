/*
 * MIT License
 *
 * Copyright (c) 2022-2024 Volodya Lombrozo
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

/**
 * Complaint abstraction.
 * You can find all complaints in package {@link com.github.lombrozo.testnames.complaints}.
 *
 * @since 0.2.0
 */
@FunctionalInterface
public interface Complaint {

    /**
     * The complaint message.
     * @return The complaint message
     */
    String message();

    /**
     * Text complaint.
     * @since 0.2
     */
    final class Text implements Complaint {

        /**
         * Complaint message.
         */
        private final String msg;

        /**
         * Main constructor.
         * @param message Complaint message.
         */
        public Text(final String message) {
            this.msg = message;
        }

        @Override
        public String message() {
            return this.msg;
        }
    }
}
