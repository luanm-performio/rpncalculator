package rpncalculator;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class CalculatorContext {
    private static final String DELIMITER = " ";
    private final Stack<BigDecimal> stack;
    private final Stack<String> undoOperatorStack;
    private final MathContext mathContext;

    public CalculatorContext() {
        stack = new Stack<>();
        undoOperatorStack = new Stack<>();
        mathContext = new MathContext(16);
    }

    public void add(final BigDecimal operand) {

        stack.add(operand);
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
        final DecimalFormat df = new DecimalFormat();
        df.setRoundingMode(RoundingMode.FLOOR);
        df.setMaximumFractionDigits(10);
        df.setMinimumFractionDigits(0);
        return "stack: " + stack.stream().map(df::format).collect(Collectors.joining(DELIMITER));
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
