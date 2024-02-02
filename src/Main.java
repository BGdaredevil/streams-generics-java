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
//        listNestedFolders(DIR_PATH);
//        System.out.println(recursivelyListNestedFolders(DIR_PATH));
//        System.out.println(recordedCount);
        serializeDeserializeObject();

    }

    private static void serializeDeserializeObject() {
        Set<SerializableObject> playground = new HashSet<>();
        SerializableObject pesho = new SerializableObject("Pesho", 22, new HashSet<>(Set.of("mocho", "sharo", "the rest")), true, 'y');
        SerializableObject gosho = new SerializableObject("gosho", 32, new HashSet<>(Set.of("car", "bike")), true, 'y');
        SerializableObject misho = new SerializableObject("misho", 26, new HashSet<>(), true, 'y');
        SerializableObject simo = new SerializableObject("simo", 30, new HashSet<>(Set.of("mavro")), true, 'y');

        System.out.printf("one  %d, %s\n", pesho.getID(), pesho.getName());

        playground.add(pesho);
        playground.add(gosho);
        playground.add(misho);
        playground.add(simo);

        try (FileOutputStream out = new FileOutputStream("./serialized-single-item");
             ObjectOutputStream objStream = new ObjectOutputStream(out)
        ) {
            objStream.writeObject(pesho);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileOutputStream out = new FileOutputStream("./serialized-many-items");
             ObjectOutputStream objStream = new ObjectOutputStream(out)
        ) {
            objStream.writeObject(playground);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (FileInputStream in = new FileInputStream("./serialized-single-item");
             ObjectInputStream objStream = new ObjectInputStream(in)
        ) {
            SerializableObject asan = (SerializableObject) objStream.readObject();
           System.out.printf("two  %d, %s\n", asan.getID(), asan.getName());

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (FileInputStream in = new FileInputStream("./serialized-many-items");
             ObjectInputStream objStream = new ObjectInputStream(in)
        ) {
            Set<SerializableObject> asan = (HashSet<SerializableObject>) objStream.readObject();

            asan.forEach(el -> System.out.printf("three %d, %s\n", el.getID(), el.getName()));

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    static int recordedCount = 0;

    private static String recursivelyListNestedFolders(String dirPath) {
        ArrayDeque<String> result = new ArrayDeque<>();

        try {
            String absPath = parseRelativePath(dirPath);
            File root = new File(absPath);

            if (root.isDirectory()) {
                result.add(String.format("%s -> %s", root.getPath(), root.getName()));
                recordedCount++;
            }

            File[] nested = root.listFiles(File::isDirectory);

            if (nested != null) {
                Arrays.stream(nested).map(e -> recursivelyListNestedFolders(e.getPath())).forEach(result::add);
            }

            return String.join("\n", result.stream().map(e -> e.replace(root.getParent(), ".")).toArray(String[]::new));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
            System.out.printf(".%s -> %s\n", rootItem.getPath().replace(absPath, "").replace(rootItem.getName(), ""), rootItem.getName());

            dirsToTraverse.forEach((path, dir) -> {
                System.out.printf(".%s -> %s\n", path.replace(absPath, "").replace(dir.getName(), ""), dir.getName());
            });

            System.out.println(dirsToTraverse.size() + 1);

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