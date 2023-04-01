package com.github.lombrozo.testnames.complaints;

import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.TestClass;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class CompoundClassComplaintTest {

    @Test
    void returnsSimpleMessageIfDoesNotHaveComplaints() {
        MatcherAssert.assertThat(
            new CompoundClassComplaint(new TestClass.Fake()).message(),
            Matchers.equalTo("haha")
        );
    }

    @Test
    void returnsCompoundMessageIfHasSeveralComplaints() {
        MatcherAssert.assertThat(
            new CompoundClassComplaint(
                new TestClass.Fake(),
                new Complaint.Fake("haha"),
                new Complaint.Fake("haha")
            ).message(),
            Matchers.equalTo(
                "Class FakeClass has some complaints, the path FakeClass:\n\t1) haha\n\t2) haha"
            )
        );
    }

}