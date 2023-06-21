package rpncalculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class CalculatorContext {
    private static final String DELIMITER = " ";
    private final Stack<BigDecimal> stack;
    private final Stack<String> undoOperatorStack;
    private MathContext mathContext;
    private DecimalFormat numberFormatter;

    public CalculatorContext() {
        stack = new Stack<>();
        undoOperatorStack = new Stack<>();
    }

    public CalculatorContext withPrecision(final int precision) {
        this.mathContext = new MathContext(precision);

        return this;
    }

    public CalculatorContext withMaximumDisplayDecimalPlaces(final int displayDecimalPlaces) {
        numberFormatter = new DecimalFormat();
        numberFormatter.setRoundingMode(RoundingMode.FLOOR);
        numberFormatter.setMaximumFractionDigits(displayDecimalPlaces);
        numberFormatter.setMinimumFractionDigits(0);

        return this;
    }

    public void add(final BigDecimal operand) {
        stack.add(operand);
        undoOperatorStack.add(operand + " ");
    }

    public BigDecimal pop() {
        return stack.pop();
    }

    public void clear() {
        stack.clear();
    }

    public int numberOfOperands() {
        return stack.size();
    }

    public String toString() {
        final String stackInformation = stack.stream().map(numberFormatter::format).collect(Collectors.joining(DELIMITER));
        return "stack:" + ("".equals(stackInformation) ? "" : " " + stackInformation);
    }

    public void save(final String expression) {
        undoOperatorStack.add(expression);
    }

    public Optional<String> popLastSave() {
        if (undoOperatorStack.size() > 0) {
            return Optional.of(undoOperatorStack.pop());
        }
        return Optional.empty();
    }

    public MathContext getMathContext() {
        return mathContext;
    }
}
