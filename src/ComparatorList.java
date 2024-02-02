import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class ComparatorList<T extends Comparable<T>> {
    private ArrayList<T> container;

    public ComparatorList() {
        this.container = new ArrayList<T>();
    }

    public void add(T element) {
        this.container.add(element);
    }

    public T remove(int index) {
        return this.container.remove(index);
    }

    public boolean contains(T element) {
        return this.container.contains(element);
    }

    public void swap(int indexOne, int indexTwo) {
        var tempArr = this.container.toArray((T[]) Array.newInstance(this.container.getLast().getClass(), this.container.size()));
        var temp = tempArr[indexOne];
        tempArr[indexOne] = tempArr[indexTwo];
        tempArr[indexTwo] = temp;
        this.container = new ArrayList<>(Arrays.asList(tempArr));
    }

    public int countGreaterThan(T element) {
        return compareValues(element);
    }

    public T getMax() {
        T element = null;
        var item = this.container.stream().max(Comparable::compareTo);

        if (item.isPresent()) {
            element = item.get();
        }

        return element;
    }

    public T getMin() {
        T element = null;
        var item = this.container.stream().min(Comparable::compareTo);

        if (item.isPresent()) {
            element = item.get();
        }

        return element;
    }

    public String print() {
        return String.join("\n", this.container.stream().map(Object::toString).toArray(String[]::new));
    }

    private int compareValues(T toCompareTo) {
        int count = 0;

        for (T element : this.container) {
            if (element.compareTo(toCompareTo) > 0) {
                count++;
            }
        }

        return count;
    }
}
