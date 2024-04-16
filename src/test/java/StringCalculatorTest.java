import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StringCalculatorTest {

    private StringCalculator calculator;

    @BeforeEach
    public void beforeEach() {
        calculator = new StringCalculator();
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
    public void testWithNegativeNumberThrowsException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> calculator.add("1,2,-3"));
    }
}
