import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String PATH = "./input.txt";

    public static void main(String[] args) {

//        readFile(PATH);
//        writeToFile(PATH);
//        coppyToFile(PATH, "./input-copy.txt");
        extractInts(PATH, "./extracted-digits.txt");

    }

    private static void extractInts(String inputPath, String outputPath) {
        Pattern digitSelector = Pattern.compile("(?<!\\w)\\d+(?![\\w]+)");

        try (BufferedReader in = new BufferedReader(new FileReader(inputPath)); PrintWriter out = new PrintWriter(new FileWriter(outputPath))) {
            String line = in.readLine();

            while (line != null) {
                Matcher matcher = digitSelector.matcher(line);
                while (matcher.find()) {
                    out.println(matcher.group());
                }

                line = in.readLine();
            }

        } catch (IOException err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }

    }

    private static void coppyToFile(String inputPath, String outputPath) {
        List<Character> chars = new ArrayList<>(Arrays.asList(' ', '\n'));
        try (FileInputStream in = new FileInputStream(inputPath); FileOutputStream out = new FileOutputStream(outputPath)) {
            int character = in.read();
            while (character != -1) {
                if (chars.contains((char) character)) {
                    out.write((char) character);
                } else {
                    out.write(String.valueOf(character).getBytes());
                }
                character = in.read();
            }
        } catch (IOException err) {
            System.out.println(err.getMessage());
            err.printStackTrace();
        }

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