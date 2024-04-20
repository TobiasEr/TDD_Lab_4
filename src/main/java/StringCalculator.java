import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringCalculator {

    private final Logger logger;

    public StringCalculator(Logger logger) {
        this.logger = logger;
    }

    public static void main(String[] args) {
        SimpleLogger logger = new SimpleLogger();
        Scanner scanner = new Scanner(System.in);
        StringCalculator calculator = new StringCalculator(logger);
        System.out.println("Welcome!");
        System.out.println("Type scalc '{YOUR_NUMBERS}'");

        // Loop until the user inputs ""
        while (true) {
            String input = scanner.nextLine(); // Read the next line of input

            // Check if the user wants to exit
            if (input.isEmpty()) {
                break; // Exit the loop
            }

            if (input.startsWith("scalc '//") && !input.endsWith("'")) {
                input += "\n";
                input += scanner.nextLine();
            }

            if (!input.startsWith("scalc '") || !input.endsWith("'")) {
                System.out.println("Invalid input");
                continue;
            }

            // Process the input
            input = input.substring(7, input.length()-1);
            int result = calculator.add(input);
            if (result == -1) {
                System.out.println("Invalid input");
            } else {
                System.out.println("The result is " + result);
            }
        }

        scanner.close();
        System.out.println("Exiting");
    }

    public int add(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        String regex = "";

        if (input.startsWith("//")) {
            Pattern pattern = Pattern.compile("//(.*?)\n");
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                String separatorsString = matcher.group(1);
                regex = generateRegex(separatorsString);

                input = input.substring(matcher.end()); // Get the text after the separators
            }
        }

        if (regex.isEmpty()) {
            regex = ",|\n";
        }

        // If the input string starts with \n it gets removed.
        // This is to avoid splitting at the first \n if it starts with it.
        if (input.startsWith("\n")) {
            input = input.substring(1);
        }

        return calculateResult(input, regex);
    }

    private String generateRegex (String separatorsString) {
        List<String> separators = new ArrayList<>();

        // Check if separators are enclosed in square brackets
        if (separatorsString.startsWith("[")) {
            Pattern separatorPattern = Pattern.compile("\\[(.*?)\\]");
            Matcher separatorMatcher = separatorPattern.matcher(separatorsString);

            while (separatorMatcher.find()) {
                String separator = separatorMatcher.group(1);
                separators.add(Pattern.quote(separator));
            }
        } else {
            // If not enclosed in square brackets, add the whole string as a separator
            separators.add(Pattern.quote(separatorsString));
        }

        // Join the separators with '|'
        return String.join("|", separators);
    }

    private int calculateResult (String input, String regex) {
        List<String> numbers = List.of(input.split(regex));
        int parsedNum;
        int result = 0;
        for (String number : numbers) {
            try {
                parsedNum = Integer.parseInt(number);
            } catch (NumberFormatException e) {
                return -1;
            }
            if (parsedNum < 0) {
                throw new IllegalArgumentException("Negatives not allowed. Invalid input = " + parsedNum);
            }
            if (parsedNum >= 1000) {
                logger.log(parsedNum);
            }
            result += parsedNum;
        }
        return result;
    }
}
