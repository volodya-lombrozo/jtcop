package com.github.lombrozo.testnames;

import java.util.Locale;

public class RuleName {

    private static final String PREFIX = "JTCOP";

    private final String name;

    public RuleName(String name) {
        this.name = name;
    }

    public boolean hasPrefix() {
        return this.name.toUpperCase(Locale.ROOT).startsWith(RuleName.PREFIX);
    }

    public String withoutPrefix() {
        if (this.hasPrefix()) {
            return this.name.substring(RuleName.PREFIX.length() + 1);
        } else {
            return this.name;
        }
    }
}
