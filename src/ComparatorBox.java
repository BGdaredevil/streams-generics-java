import java.util.List;

public class ComparatorBox<T extends Comparable<T>> {
    private final List<T> tList;

    public ComparatorBox(List<T> list) {
        this.tList = list;
    }

    public int compareValues(T toCompareTo) {
        int count = 0;

        for (T element : this.tList) {
            if (element.compareTo(toCompareTo) > 0) {
                count++;
            }
        }

        return count;
    }
}
