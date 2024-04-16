import java.util.List;

public class StringCalculator {

    public int add(String input) {
        if (input.isEmpty()) {
            return 0;
        }

        String separator;
        if (input.startsWith("//")) {
           separator = "[\n" + input.charAt(2) + "]";
           input = input.substring(3);
        } else {
            separator = "[\n,]";
        }

        // If the input string starts with \n it gets removed.
        // This is to avoid splitting at the first \n if it starts with it.
        if (input.startsWith("\n")) {
            input = input.substring(1);
        }

        List<String> numbers = List.of(input.split(separator));
        int parsedNum;
        int result = 0;
        for (String number : numbers) {
            parsedNum = Integer.parseInt(number);
            if (parsedNum < 0) {
                throw new IllegalArgumentException("Negatives not allowed. Invalid input = " + parsedNum);
            }
            result += parsedNum;
        }
        return result;
    }
}
