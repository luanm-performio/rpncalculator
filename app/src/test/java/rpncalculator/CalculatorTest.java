package rpncalculator;

import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CalculatorTest {

    private Calculator calculator;
    private final StringBuffer output = new StringBuffer();
    private Consumer<String> logConsumer = (message) ->  output.append(message);

    @BeforeEach
    public void setup() {
        calculator = new Calculator(logConsumer);
    }

    @Test
    public void shouldEvaluateSimpleInputStringCase1() {
        evaluate("5 2");
        assertThatOutputHasToString("stack: 5 2");
    }

    @Test
    public void shouldEvaluateMoreThanOneInputStringsWithOperatorCase2() {
        evaluate("2 sqrt");
        assertThatOutputHasToString("stack: 1.4142135623");

        evaluate("clear 9 sqrt");
        assertThat(output).hasToString("stack: 3");
    }

    @Test
    public void shouldEvaluateMoreThanOneInputStringsWithOperatorCase3() {
        evaluate("5 2 -");
        assertThatOutputHasToString("stack: 3");

        evaluate("3 -");
        assertThatOutputHasToString("stack: 0");

        evaluate("clear");
        assertThat(output).hasToString("stack:");
    }

    @Test
    public void shouldEvaluateMoreThanOneInputStringsWithOperatorCase4() {
        evaluate("5 4 3 2");
        assertThatOutputHasToString("stack: 5 4 3 2");

        evaluate("undo undo *");
        assertThatOutputHasToString("stack: 20");

        evaluate("5 *");
        assertThatOutputHasToString("stack: 100");

        evaluate("undo");
        assertThat(output).hasToString("stack: 20 5");
    }

    @Test
    public void shouldEvaluateMoreThanOneInputStringsWithOperatorCase5() {
        evaluate("7 12 2 /");
        assertThatOutputHasToString("stack: 7 6");

        evaluate("*");
        assertThatOutputHasToString("stack: 42");

        evaluate("4 /");
        assertThatOutputHasToString("stack: 10.5");
    }

    @Test
    public void shouldEvaluateMoreThanOneInputStringsWithOperatorCase6() {
        evaluate("1 2 3 4 5");
        assertThatOutputHasToString("stack: 1 2 3 4 5");

        evaluate("*");
        assertThatOutputHasToString("stack: 1 2 3 20");

        evaluate("clear 3 4 -");
        assertThatOutputHasToString("stack: -1");
    }

    @Test
    public void shouldEvaluateMoreThanOneInputStringsWithOperatorCase7() {
        evaluate("1 2 3 4 5");
        assertThatOutputHasToString("stack: 1 2 3 4 5");

        evaluate("* * * *");
        assertThatOutputHasToString("stack: 120");
    }

    @Test
    @Disabled("Not yet covered")
    public void shouldEvaluateMoreThanOneInputStringsWithOperatorCase8() {
        evaluate("1 2 3 * 5 + * * 6 5");
        assertThatOutputHasToString("operator * (position: 15): insufficient parameters\nstack: 11");
    }

    private void evaluate(String input) {
        calculator.evaluate(input);
        calculator.printCurrentState();
    }

    private void assertThatOutputHasToString(String expectedToString) {
        assertThat(output).hasToString(expectedToString);
        output.setLength(0);
    }
}
