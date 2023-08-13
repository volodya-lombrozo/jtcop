/*
 * MIT License
 *
 * Copyright (c) 2022-2023 Volodya
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
 * The suspect that is checked by the cop.
 * @since 1.2.0
 * @todo #245:90min The Suspect class is a plain DTO.
 *  We should refactor this class to make it more object-oriented.
 *  Why it is bed, you can read in that
 *  <a href="https://www.yegor256.com/2016/07/06/data-transfer-object.html">blog post.</a>
 *  The main idea to make this class alive.
 *  Moreover, we have to add more tests for this class.
 */
public class Suspect {

    /**
     * The project to check.
     */
    private final Project proj;

    /**
     * The test class to check.
     */
    private final TestClass klass;

    /**
     * Ctor.
     * @param project The project to check.
     * @param test The test class to check.
     */
    Suspect(final Project project, final TestClass test) {
        this.proj = project;
        this.klass = test;
    }

    /**
     * The project to check.
     * @return The project.
     */
    public Project project() {
        return this.proj;
    }

    /**
     * The test class to check.
     * @return The test class.
     */
    public TestClass test() {
        return this.klass;
    }
}
