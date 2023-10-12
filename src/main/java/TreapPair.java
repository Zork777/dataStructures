public class TreapPair<T> {
    private final T lhs;
    private final T rhs;

    public TreapPair() {
        this.rhs = null;
        this.lhs = null;
    }

    public TreapPair (T lhs, T rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public T getLhs() {
        return lhs;
    }

    public T getRhs() {
        return rhs;
    }
}
