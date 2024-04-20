import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class StringCalculatorTest {

    private StringCalculator calculator;
    private Logger mockLogger;
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void beforeEach() {
        mockLogger = Mockito.mock(Logger.class);
        calculator = new StringCalculator(mockLogger);
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
        System.setIn(System.in);
    }

    @Test
    public void testEmptyStringReturnsZero() {
        Assertions.assertEquals(0, calculator.add(""));
    }

    @Test
    public void testOneNumberReturnsAddResult() { Assertions.assertEquals(1, calculator.add("1")); }

    @Test
    public void testTwoNumbersReturnsAddResult() { Assertions.assertEquals(3, calculator.add("1,2")); }

    @Test
    public void testTenNumbersReturnsAddResult() { Assertions.assertEquals(55, calculator.add("1,2,3,4,5,6,7,8,9,10")); }

    @Test
    public void testWithNewlineReturnsAddResult() { Assertions.assertEquals(6, calculator.add("1\n2,3")); }

    @Test
    public void testWithOtherSeparatorReturnsAddResult() { Assertions.assertEquals(3, calculator.add("//;\n1;2")); }

    @Test
    public void testWithSeveralSeparatorReturnsAddResult() {
        Assertions.assertEquals(7,
            calculator.add("//[***][%%%]\n" +
                    "1***2%%%4"));
    }

    @Test
    public void testWithNegativeNumberThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.add("1,2,-3"));
    }

    @Test
    public void testThatLoggerRunsWhenNumberAbove1000() {
        calculator.add("1001,1");
        Mockito.verify(mockLogger, Mockito.times(1)).log(1001);
        ;
    }

    @Test
    public void testThatLoggerDontRunWhenNumberUnder1000() {
        calculator.add("999,1");
        Mockito.verify(mockLogger, Mockito.times(0)).log(999);
        ;
    }

    @Test
    public void testPrintOutMain() {
        System.setOut(new PrintStream(outputStream));

        String input = "" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String[] args = {};
        StringCalculator.main(args);
        String expectedOutput = "Welcome!" + System.lineSeparator() +
                "Type scalc '{YOUR_NUMBERS}'" + System.lineSeparator() +
                "Exiting";
        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testPrintOutScalcResult() {
        System.setOut(new PrintStream(outputStream));

        String input = "scalc '1,2,3'" + System.lineSeparator() +
                "" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String[] args = {};
        StringCalculator.main(args);
        String expectedOutput = "Welcome!" + System.lineSeparator() +
                "Type scalc '{YOUR_NUMBERS}'" + System.lineSeparator() +
                "The result is 6" + System.lineSeparator() +
                "Exiting";
        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testPrintOutScalcResultInLoop() {
        System.setOut(new PrintStream(outputStream));

        String input = "scalc '1,2,3'" + System.lineSeparator() +
                "scalc '2,3,4'" + System.lineSeparator() +
                "scalc '3,4,5'" + System.lineSeparator() +
                "scalc '4,5,6'" + System.lineSeparator() +
                "" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String[] args = {};
        StringCalculator.main(args);
        String expectedOutput = "Welcome!" + System.lineSeparator() +
                "Type scalc '{YOUR_NUMBERS}'" + System.lineSeparator() +
                "The result is 6" + System.lineSeparator() +
                "The result is 9" + System.lineSeparator() +
                "The result is 12" + System.lineSeparator() +
                "The result is 15" + System.lineSeparator() +
                "Exiting";
        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testPrintOutScalcResultManyDelimiters() {
        System.setOut(new PrintStream(outputStream));

        String input = "scalc '//[***][%%%]\n1***2%%%4'" + System.lineSeparator() +
                "" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String[] args = {};
        StringCalculator.main(args);
        String expectedOutput = "Welcome!" + System.lineSeparator() +
                "Type scalc '{YOUR_NUMBERS}'" + System.lineSeparator() +
                "The result is 7" + System.lineSeparator() +
                "Exiting";
        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testInvalidInput() {
        System.setOut(new PrintStream(outputStream));

        String input = "hej" + System.lineSeparator() +
                "" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        String[] args = {};
        StringCalculator.main(args);
        String expectedOutput = "Welcome!" + System.lineSeparator() +
                "Type scalc '{YOUR_NUMBERS}'" + System.lineSeparator() +
                "Invalid input" + System.lineSeparator() +
                "Exiting";
        Assertions.assertEquals(expectedOutput, outputStream.toString().trim());
    }




}
