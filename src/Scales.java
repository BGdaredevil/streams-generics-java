public class Scales<T extends Comparable<T>> {
    private final T left;
    private final T right;

    public Scales(T left, T right) {
        this.left = left;
        this.right = right;
    }

    public T getHeavier() {
        if (this.left.compareTo(this.right) == 0) {
            return null;
        }

        if (this.left.compareTo(this.right) < 0) {
            return this.right;
        }

        return left;
    }

}
