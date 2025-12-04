import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class SearchFile {


    private static final int REQUIRED_ARGUMENT_LENGTH = 3;

    public static void main(String[] args) {

        if (args.length != REQUIRED_ARGUMENT_LENGTH || !args[0].equals("search")) {
            printAndExit("Syntax error. Usage: search <pattern> <file>");
        }
        //This program serches a file for matching arguments!

        String patternText = args[1];
        String filePath = args[2];

        File file = new File(filePath);

        if (!file.exists()) {
            printAndExit("File not found: " + filePath);
        }

        List<String> allLines;

        try {
            allLines = Files.readAllLines(file.toPath());
        } catch (IOException e) {
            printAndExit("Could not read file: " + filePath);
            return; 
        }

        try {
            Pattern pattern = Pattern.compile(patternText);

            allLines.stream()
                    .filter(line -> {
                        Matcher matcher = pattern.matcher(line);
                        return matcher.find();
                    })
                    .forEach(System.out::println);

        } catch (PatternSyntaxException e) {
            printAndExit("Invalid pattern: " + patternText);
        }
    }

    private static void printAndExit(String message) {
        System.out.println(message);
        System.exit(1);
    }
}
