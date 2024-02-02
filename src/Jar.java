import java.util.ArrayDeque;
import java.util.Optional;

public class Jar<T> {
    private final ArrayDeque<T> contents = new ArrayDeque<>();

    public void add(T element) {
        this.contents.push(element);
    }

    public Optional<T> remove() {
        return Optional.ofNullable(this.contents.poll());
    }
}
