package com.github.lombrozo.testnames.rules;

import com.github.lombrozo.testnames.Assertion;
import com.github.lombrozo.testnames.Complaint;
import com.github.lombrozo.testnames.TestCase;
import java.util.Collection;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

/**
 * The test case for {@link RuleAssertionMessage}.
 *
 * @since 0.1.15
 */
class RuleAssertionMessageTest {

    @Test
    void checksAssertionMessageWithoutComplaints() {
        MatcherAssert.assertThat(
            new RuleAssertionMessage(new TestCase.Fake(new Assertion.Fake())).complaints(),
            Matchers.empty()
        );
    }

    @Test
    void checksAssertionMessageOfTestCaseWithoutAssertions() {
        final Collection<Complaint> complaints = new RuleAssertionMessage(
            new TestCase.Fake()
        ).complaints();
        MatcherAssert.assertThat(
            complaints,
            Matchers.hasSize(1)
        );
        final Complaint next = complaints.iterator().next();
        MatcherAssert.assertThat(
            next.message(),
            Matchers.containsString("doesn't have assertion statements")
        );

    }

    @Test
    void checksAssertionMessageOfTestCaseWithoutAssertionMessage() {
        final Collection<Complaint> complaints = new RuleAssertionMessage(
            new TestCase.Fake(new Assertion.Empty())
        ).complaints();
        MatcherAssert.assertThat(
            complaints,
            Matchers.hasSize(1)
        );
        MatcherAssert.assertThat(
            complaints.iterator().next().message(),
            Matchers.containsString("has assertion without message")
        );
    }

}