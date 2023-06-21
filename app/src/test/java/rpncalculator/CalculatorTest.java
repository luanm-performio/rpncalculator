package rpncalculator;

import java.util.function.Consumer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;
    private final StringBuffer output = new StringBuffer();
    private Consumer<String> logConsumer = (message) ->  output.append(message);

    @BeforeEach
    public void setup() {
        calculator = new Calculator(logConsumer);
    }

    @Test
    public void shouldEvaluateSimpleInputString() {
        calculator.evaluate("5 2");
        calculator.printCurrentState();

        assertThat(output).hasToString("stack: 5 2");
    }

    @Test
    public void shouldEvaluateMoreThanOneInputStringsWithOperatorCase1() {
        calculator.evaluate("2 sqrt");
        calculator.printCurrentState();
        assertThat(output).hasToString("stack: 1.4142135623");
        output.setLength(0);

        calculator.evaluate("clear 9 sqrt");
        calculator.printCurrentState();
        assertThat(output).hasToString("stack: 3");
    }

    @Test
    public void shouldEvaluateMoreThanOneInputStringsWithOperatorCase2() {
        calculator.evaluate("5 2 -");
        calculator.printCurrentState();
        assertThat(output).hasToString("stack: 3");
        output.setLength(0);

        calculator.evaluate("3 -");
        calculator.printCurrentState();
        assertThat(output).hasToString("stack: 0");

        output.setLength(0);
        calculator.evaluate("clear");
        calculator.printCurrentState();
        assertThat(output).hasToString("stack: ");
    }

    @Test
    public void shouldEvaluateMoreThanOneInputStringsWithOperatorCase3() {
        calculator.evaluate("5 4 3 2");
        calculator.printCurrentState();
        assertThat(output).hasToString("stack: 5 4 3 2");
        output.setLength(0);

        calculator.evaluate("undo undo *");
        calculator.printCurrentState();
        assertThat(output).hasToString("stack: 20");

        output.setLength(0);
        calculator.evaluate("5 *");
        calculator.printCurrentState();
        assertThat(output).hasToString("stack: 100");

        output.setLength(0);
        calculator.evaluate("undo");
        calculator.printCurrentState();
        assertThat(output).hasToString("stack: 20 5");
    }

}
