import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class JunitAssertTrueHitter {
    @Test
    void assertTrueHitter() {
        assertTrue(true);
    }

    @Test
    void assertFalseHitter() {
        assertFalse(false);
    }
}