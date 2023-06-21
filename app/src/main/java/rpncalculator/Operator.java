package rpncalculator;

import java.util.Arrays;
import java.util.Optional;

public enum Operator {
    PLUS("+", OperatorExecutor.plus()),
    MINUS("-", OperatorExecutor.minus()),
    MULTIPLY("*", OperatorExecutor.multiply()),
    DIVIDE("/", OperatorExecutor.divide()),
    SQRT("sqrt", OperatorExecutor.squareRoot()),
    CLEAR("clear", OperatorExecutor.clear()),
    UNDO("undo", OperatorExecutor.undo())
    ;

    private String label;
    private OperatorExecutor executor;

    Operator(final String label, final OperatorExecutor executor) {
        this.label = label;
        this.executor = executor;
    }

    public String getLabel() {
        return label;
    }

    public OperatorExecutor getExecutor() {
        return this.executor;
    }

    public static Optional<Operator> ofLabel(final String label) {
        return Arrays.stream(values())
                .filter(operator -> operator.getLabel().equals(label))
                .findFirst();
    }
}
