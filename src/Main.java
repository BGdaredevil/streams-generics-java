import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String PATH = "./input.txt";
    private static final String DIR_PATH = "../test-fs/";

    public static void main(String[] args) {

//        readFile(PATH);
//        writeToFile(PATH);
//        coppyToFile(PATH, "./input-copy.txt");
//        extractInts(PATH, "./extracted-digits.txt");
//        listFiles(DIR_PATH);
        listNestedFolders(DIR_PATH);

    }

    private static void listNestedFolders(String dirPath) {
        Map<String, File> dirsToTraverse = new LinkedHashMap<>();

        try {
            String absPath = parseRelativePath(dirPath);
            File rootItem = new File(absPath);

            File[] dirs = rootItem.listFiles(File::isDirectory);

            while (dirs.length > 0) {
                Map<String, File> tempMap = new LinkedHashMap<>();

                Arrays.stream(dirs).forEach(dir -> {
                    if (!dirsToTraverse.containsKey(dir.getAbsolutePath()) && dir.isDirectory()) {
                        dirsToTraverse.put(dir.getAbsolutePath(), dir);
                        File[] subDirs = dir.listFiles(File::isDirectory);
                        Arrays.stream(subDirs).forEach(subDir -> {
                            if (!tempMap.containsKey(subDir.getAbsolutePath())) {
                                tempMap.put(subDir.getAbsolutePath(), subDir);
                            }
                        });
                    }
                });

                dirs = tempMap.values().toArray(File[]::new);
            }

            dirsToTraverse.forEach((path, dir) -> {
                System.out.printf(".%s -> %s\n", path.replace(absPath, "").replace(dir.getName(), ""), dir.getName());
            });

            System.out.println(dirsToTraverse.size());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void listFiles(String dirPath) {
        try {
            String absPath = parseRelativePath(dirPath);
            File[] fileList = Objects.requireNonNull(new File(absPath).listFiles());
            File[] sortedFiles = Arrays.stream(fileList)
                    .filter(e -> !e.isDirectory())
                    .sorted(Comparator.comparing(File::getName, String::compareTo))
                    .toArray(File[]::new);

            for (File file : sortedFiles) {
                System.out.printf("%s - %s b\n", file.getName(), Files.size(Paths.get(file.getAbsolutePath())));
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static String parseRelativePath(String path) throws IOException {
        File helper = new File(path);
        return helper.getCanonicalPath();
    }

    private static void extractInts(String inputPath, String outputPath) {
        Pattern digitSelector = Pattern.compile("(?<!\\w)\\d+(?!\\w+)");

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