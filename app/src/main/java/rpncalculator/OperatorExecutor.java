package rpncalculator;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.function.Consumer;

@FunctionalInterface
public interface OperatorExecutor {

    void execute(final CalculatorContext context, final Consumer<String> logConsumer);

    static OperatorExecutor plus() {
        return (context, logConsumer) -> {

            if (context.numberOfOperands() > 1) {
                final BigDecimal rightOperand = context.pop();
                final BigDecimal leftOperand = context.pop();
                final BigDecimal result = leftOperand.add(rightOperand, context.getMathContext());
                context.add(result);
                final String expression = result + " " + leftOperand + " " + rightOperand;
                context.save(expression);
            } else {
                throw new InsufficientParametersException();
            }
        };
    }

    static OperatorExecutor minus() {
        return (context, logConsumer) -> {

            if (context.numberOfOperands() > 1) {
                final BigDecimal rightOperand = context.pop();
                final BigDecimal leftOperand = context.pop();
                final BigDecimal result = leftOperand.subtract(rightOperand, context.getMathContext());
                context.add(result);
                final String expression = result + " " + leftOperand + " " + rightOperand;
                context.save(expression);
            } else {
                throw new InsufficientParametersException();
            }
        };
    }

    static OperatorExecutor multiply() {
        return (context, logConsumer) -> {

            if (context.numberOfOperands() > 1) {
                final BigDecimal rightOperand = context.pop();
                final BigDecimal leftOperand = context.pop();
                final BigDecimal result = leftOperand.multiply(rightOperand, context.getMathContext());
                context.add(result);
                final String expression = result + " " + leftOperand + " " + rightOperand;
                context.save(expression);
            } else {
                throw new InsufficientParametersException();
            }
        };
    }

    static OperatorExecutor divide() {
        return (context, logConsumer) -> {

            if (context.numberOfOperands() > 1) {
                final BigDecimal rightOperand = context.pop();
                final BigDecimal leftOperand = context.pop();
                final BigDecimal result = leftOperand.divide(rightOperand, context.getMathContext());
                context.add(result);
                final String expression = result + " " + leftOperand + " " + rightOperand;
                context.save(expression);
            } else {
                throw new InsufficientParametersException();
            }
        };
    }

    static OperatorExecutor squareRoot() {
        return (context, logConsumer) -> {

            if (context.numberOfOperands() > 0) {
                final BigDecimal operand = context.pop();
                final BigDecimal result = operand.sqrt(context.getMathContext());
                context.add(result);
                final String expression = result + " " + operand;
                context.save(expression);
            } else {
                throw new InsufficientParametersException();
            }
        };
    }

    static OperatorExecutor clear() {
        return (context, logConsumer) -> {
            context.clear();
        };
    }

    static OperatorExecutor undo() {
        return (context, logConsumer) -> {

            if (context.numberOfOperands() > 0) {
                final BigDecimal lastOperand = context.pop();
                final String lastSaveAction = context.popLastSave().orElse("");
                if (lastSaveAction.startsWith(lastOperand + " ")) {
                    Arrays.stream(lastSaveAction.split(" ")).skip(1).map(BigDecimal::new).forEach(context::add);
                }
            } else {
                throw new InsufficientParametersException();
            }
        };
    }
}
