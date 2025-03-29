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
package com.github.lombrozo.testnames.bytecode;

import com.github.lombrozo.testnames.Field;
import java.lang.annotation.RetentionPolicy;
import java.util.Collections;
import java.util.List;
import javassist.CtField;
import javassist.Modifier;

/**
 * Class field in bytecode.
 * <p>
 * Pay attention, that {@link BytecodeField} returns empty list of suppressed rules
 * in {@link #suppressed()} method. This is because it is not possible to
 * determine the suppressed rules in bytecode. Reason is that {@link SuppressWarnings}
 * has {@link RetentionPolicy#SOURCE} retention policy, so it is not available
 * in bytecode.
 * </p>
 * @since 1.4
 */
public final class BytecodeField implements Field {

    /**
     * Field.
     */
    private final CtField field;

    /**
     * Constructor.
     * @param field Field
     */
    BytecodeField(final CtField field) {
        this.field = field;
    }

    @Override
    public String name() {
        return this.field.getName();
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(this.field.getModifiers());
    }

    @Override
    public List<String> suppressed() {
        return Collections.emptyList();
    }
}
