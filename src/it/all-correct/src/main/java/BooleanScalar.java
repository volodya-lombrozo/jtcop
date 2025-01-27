public class BooleanScalar {

    private final boolean val;

    public BooleanScalar() {
        this(true);
    }

    public BooleanScalar(final boolean val) {
        this.val = val;
    }

    public boolean value() {
        return val;
    }

}