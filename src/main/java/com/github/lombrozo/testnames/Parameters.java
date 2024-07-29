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

import java.util.HashMap;
import java.util.Map;
import java.util.OptionalInt;

/**
 * Parameters that are used in the rules.
 *
 * @since 1.3
 */
public final class Parameters {

    /**
     * Parameters container.
     */
    private final Map<String, ?> params;

    /**
     * Constructor.
     * @param params Parameters pairs, like "name", "value", "name2", "value2".
     */
    public Parameters(final Object... params) {
        this(Parameters.fromArray(params));
    }

    /**
     * Constructor.
     * @param params Parameters map.
     */
    private Parameters(final Map<String, ?> params) {
        this.params = params;
    }

    /**
     * Get integer value.
     * @param name Name of the parameter.
     * @return Integer value.
     */
    OptionalInt intValue(final String name) {
        final OptionalInt result;
        if (!this.params.containsKey(name)) {
            result = OptionalInt.empty();
        } else {
            final Object value = this.params.get(name);
            if (!(value instanceof Integer)) {
                throw new IllegalArgumentException(
                    String.format(
                        "'%s' should be an integer, but was '%s'", value, value.getClass()
                    )
                );
            }
            result = OptionalInt.of(Integer.class.cast(value));
        }
        return result;
    }

    /**
     * Convert an array to a map.
     * @param params Parameters pairs like "name", "value", "name2", "value2".
     * @return Map.
     */
    private static Map<String, ?> fromArray(final Object[] params) {
        if (params.length % 2 != 0) {
            throw new IllegalArgumentException("Parameters list should be even");
        }
        final Map<String, Object> res = new HashMap<>(0);
        for (int i = 0; i < params.length; i += 2) {
            res.put((String) params[i], params[i + 1]);
        }
        return res;
    }

}
