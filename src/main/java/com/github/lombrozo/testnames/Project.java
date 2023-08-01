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

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Project.
 *
 * @since 0.2
 */
public interface Project {

    /**
     * All production classes.
     * @return All production classes.
     */
    Collection<ProductionClass> productionClasses();

    /**
     * All test classes.
     * @return All test classes.
     */
    Collection<TestClass> testClasses();

    /**
     * The fake project.
     *
     * @since 0.2
     */
    final class Fake implements Project {

        /**
         * All production classes.
         */
        private final Collection<? extends ProductionClass> classes;

        /**
         * All test classes.
         */
        private final Collection<? extends TestClass> tests;

        /**
         * Ctor.
         */
        public Fake() {
            this(new ProductionClass.Fake());
        }

        /**
         * Ctor.
         * @param classes All production classes.
         */
        public Fake(final ProductionClass... classes) {
            this(Arrays.asList(classes), Collections.emptyList());
        }

        /**
         * Ctor.
         * @param tests All test classes.
         */
        public Fake(final TestClass... tests) {
            this(Collections.emptyList(), Arrays.asList(tests));
        }

        /**
         * Ctor.
         * @param clazz Production class.
         * @param test Test class.
         */
        public Fake(final ProductionClass clazz, final TestClass test) {
            this(Collections.singleton(clazz), Collections.singleton(test));
        }

        /**
         * Primary ctor.
         * @param classes All production classes.
         * @param tests All test classes.
         */
        public Fake(
            final Collection<? extends ProductionClass> classes,
            final Collection<? extends TestClass> tests
        ) {
            this.classes = classes;
            this.tests = tests;
        }

        @Override
        public Collection<ProductionClass> productionClasses() {
            return Collections.unmodifiableCollection(this.classes);
        }

        @Override
        public Collection<TestClass> testClasses() {
            return Collections.unmodifiableCollection(this.tests);
        }
    }

    /**
     * Combined project.
     *
     * @since 0.1.17
     */
    class Combined implements Project {

        /**
         * All projects.
         */
        private final Collection<? extends Project> projects;

        /**
         * Constructor.
         * @param projects All projects.
         */
        public Combined(final Project... projects) {
            this(Arrays.asList(projects));
        }

        /**
         * Constructor.
         * @param projects All projects.
         */
        Combined(final Collection<? extends Project> projects) {
            this.projects = projects;
        }

        @Override
        public Collection<ProductionClass> productionClasses() {
            return this.projects.stream()
                .map(Project::productionClasses)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        }

        @Override
        public Collection<TestClass> testClasses() {
            return this.projects.stream()
                .map(Project::testClasses)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        }
    }

    /**
     * Project without tests.
     *
     * @since 1.0.1
     */
    class WithoutTests implements Project {

        /**
         * Original project.
         */
        private final Project original;

        /**
         * Constructor.
         * @param original Original project.
         */
        public WithoutTests(final Project original) {
            this.original = original;
        }

        @Override
        public Collection<ProductionClass> productionClasses() {
            return this.original.productionClasses();
        }

        @SuppressWarnings("PMD.JUnit4TestShouldUseTestAnnotation")
        @Override
        public Collection<TestClass> testClasses() {
            return Collections.emptyList();
        }
    }
}
