package com.github.lombrozo.testnames;

public interface ProductionClass {

    String name();


    class Fake implements ProductionClass {

        private final String name;

        public Fake(final String name) {
            this.name = name;
        }

        @Override
        public String name() {
            return this.name;
        }
    }
}
