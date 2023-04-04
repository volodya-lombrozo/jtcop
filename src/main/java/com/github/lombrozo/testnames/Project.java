package com.github.lombrozo.testnames;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public interface Project {

    Collection<ProductionClass> productionClasses();

    Collection<TestClass> testClasses();


    class Fake implements Project {

        private final Collection<? extends ProductionClass> classes;
        private final Collection<? extends TestClass> tests;


        public Fake(final ProductionClass... classes) {
            this(Arrays.asList(classes), Collections.emptyList());
        }

        public Fake(final TestClass... tests) {
            this(Collections.emptyList(), Arrays.asList(tests));
        }

        public Fake(final ProductionClass clazz, final TestClass test) {
            this(Collections.singleton(clazz), Collections.singleton(test));
        }

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
}
