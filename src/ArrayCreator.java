import java.lang.reflect.Array;
import java.util.Arrays;

public class ArrayCreator {

    public static <T> T[] create(int length, T item) {
        T[] temp = (T[]) Array.newInstance(item.getClass(), length);
        Arrays.fill(temp, item);
        return temp;
    }

    public static <T> T[] create(Class<T> tClass, int length, T item) {
        T[] temp = (T[]) Array.newInstance(tClass, length);
        Arrays.fill(temp, item);
        return temp;
    }
}
