public class Box<T> {
    private final T instance;

    public Box(T instance) {
        this.instance = instance;
    }

    @Override
    public String toString() {
        return String.format("%s: %s", this.instance.getClass().getCanonicalName(), this.instance );
    }
}
