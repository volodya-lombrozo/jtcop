import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

class HamcrestAssertTrueHitter {

    @Test
    void checksHitter() {
        MatcherAssert.assertThat("msg", true, Matchers.equalTo(true));
        MatcherAssert.assertThat("msg", true || false, Matchers.equalTo(true));
    }
}
