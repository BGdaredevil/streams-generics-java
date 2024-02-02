import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Swapper<T> {
    private ArrayList<T> buffer;

    public Swapper(int size) {
        this.buffer = new ArrayList<>(size);
    }

    private boolean invalidIndex(int index) {
        return index < 0 || index >= buffer.size();
    }

    public void addItem(T item) {
        buffer.add(item);
    }

    public void swap(int first, int second) {
        if (this.invalidIndex(first) || this.invalidIndex((second))) {
            System.out.println("Invalid index");
            return;
        }

        if (this.buffer.isEmpty()) {
            System.out.println("No items in the collection");
            return;
        }

        var tempArr = buffer.toArray((T[]) Array.newInstance(buffer.getLast().getClass(), buffer.size()));
        var temp = tempArr[first];
        tempArr[first] = tempArr[second];
        tempArr[second] = temp;
        buffer = new ArrayList<>(Arrays.asList(tempArr));
    }

    public String printState() {
        StringBuilder result = new StringBuilder();
        this.buffer.forEach(e -> result.append(String.format("%s: %s\n", e.getClass().getCanonicalName(), e)));

        return result.toString();
    }

}
