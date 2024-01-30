import java.io.FileInputStream;
import java.io.IOException;
import java.nio.Buffer;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

//        readFile("./input.txt");


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