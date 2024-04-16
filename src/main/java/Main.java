import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        var calculator = new StringCalculator();

        // Loop until the user inputs "exit"
        while (true) {
            System.out.print("? ");
            String input = scanner.nextLine(); // Read the next line of input

            // Check if the user wants to exit
            if ("exit".equalsIgnoreCase(input)) {
                break; // Exit the loop
            }

            // Process the input
            var result = calculator.add(input);
            System.out.println(result);
        }

        scanner.close();
        System.out.println("Exiting...");
    }
}
