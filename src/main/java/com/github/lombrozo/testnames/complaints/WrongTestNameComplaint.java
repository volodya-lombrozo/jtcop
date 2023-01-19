package com.github.lombrozo.testnames.complaints;

import com.github.lombrozo.testnames.Case;
import com.github.lombrozo.testnames.Complaint;

public class WrongTestNameComplaint implements Complaint {
    private final Case test;
    private final String explanation;

    public WrongTestNameComplaint(final Case test, final String explanation) {
        this.test = test;
        this.explanation = explanation;
    }

    @Override
    public String message() {
        return String.format(
            "Test name '%s#%s' doesn't follow naming rules, because %s, test path: %s",
            this.test.className(),
            this.test.name(),
            this.explanation,
            this.test.path()
        );
    }
}
