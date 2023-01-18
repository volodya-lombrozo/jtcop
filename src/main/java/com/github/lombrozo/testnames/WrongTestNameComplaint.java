package com.github.lombrozo.testnames;

public class WrongTestNameComplaint implements Complaint {
    private final TestCase test;
    private final String explanation;

    public WrongTestNameComplaint(final TestCase test, final String explanation) {
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
