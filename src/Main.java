import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

//        readFile("./input.txt");
        writeToFile("./input.txt");

    }

    private static void writeToFile(String path) {
        List<Character> sybols = new ArrayList<>(Arrays.asList(',', '.', '!', '?', ';'));

        try (FileInputStream in = new FileInputStream(path);
             FileOutputStream out = new FileOutputStream("./result.txt")
        ) {
            int symbol = in.read();

            while (symbol != -1) {
                if (sybols.contains((char) symbol)) {
                    symbol = in.read();

                    continue;
                }

                out.write(symbol);
                symbol = in.read();
            }

        } catch (IOException err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }

    }

    private static void readFile(String path) {
        StringBuilder result = new StringBuilder();
        try (FileInputStream in = new FileInputStream(path)) {
            int theByte = in.read();

            while (theByte != -1) {
                result.append(theByte).append(", ");
                theByte = in.read();
            }

            System.out.println(result);

        } catch (IOException err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }
    }
}