package rpncalculator;

import java.math.BigDecimal;
import java.util.Arrays;

@FunctionalInterface
public interface OperatorExecutor {

    void execute(final CalculatorContext context);

    static OperatorExecutor plus() {
        return (context) -> {

            if (context.numberOfOperands() > 1) {
                final BigDecimal rightOperand = context.pop();
                final BigDecimal leftOperand = context.pop();
                final BigDecimal result = leftOperand.add(rightOperand, context.getMathContext());
                context.add(result);
                final String expression = result + CalculatorContext.DELIMITER + leftOperand + CalculatorContext.DELIMITER + rightOperand;
                context.save(expression);
            } else {
                throw new InsufficientParametersException();
            }
        };
    }

    static OperatorExecutor minus() {
        return (context) -> {

            if (context.numberOfOperands() > 1) {
                final BigDecimal rightOperand = context.pop();
                final BigDecimal leftOperand = context.pop();
                final BigDecimal result = leftOperand.subtract(rightOperand, context.getMathContext());
                context.add(result);
                final String expression = result + CalculatorContext.DELIMITER + leftOperand + CalculatorContext.DELIMITER + rightOperand;
                context.save(expression);
            } else {
                throw new InsufficientParametersException();
            }
        };
    }

    static OperatorExecutor multiply() {
        return (context) -> {

            if (context.numberOfOperands() > 1) {
                final BigDecimal rightOperand = context.pop();
                final BigDecimal leftOperand = context.pop();
                final BigDecimal result = leftOperand.multiply(rightOperand, context.getMathContext());
                context.add(result);
                final String expression = result + CalculatorContext.DELIMITER + leftOperand + CalculatorContext.DELIMITER + rightOperand;
                context.save(expression);
            } else {
                throw new InsufficientParametersException();
            }
        };
    }

    static OperatorExecutor divide() {
        return (context) -> {

            if (context.numberOfOperands() > 1) {
                final BigDecimal rightOperand = context.pop();
                final BigDecimal leftOperand = context.pop();
                final BigDecimal result = leftOperand.divide(rightOperand, context.getMathContext());
                context.add(result);
                final String expression = result + CalculatorContext.DELIMITER + leftOperand + CalculatorContext.DELIMITER + rightOperand;
                context.save(expression);
            } else {
                throw new InsufficientParametersException();
            }
        };
    }

    static OperatorExecutor squareRoot() {
        return (context) -> {

            if (context.numberOfOperands() > 0) {
                final BigDecimal operand = context.pop();
                final BigDecimal result = operand.sqrt(context.getMathContext());
                context.add(result);
                final String expression = result + CalculatorContext.DELIMITER + operand;
                context.save(expression);
            } else {
                throw new InsufficientParametersException();
            }
        };
    }

    static OperatorExecutor clear() {
        return (context) -> {
            context.clear();
        };
    }

    static OperatorExecutor undo() {
        return (context) -> {

            if (context.numberOfOperands() > 0) {
                final BigDecimal lastOperand = context.pop();
                final String lastSaveAction = context.popLastSave().orElse("");
                if (lastSaveAction.startsWith(lastOperand + CalculatorContext.DELIMITER)) {
                    Arrays.stream(lastSaveAction.split(CalculatorContext.DELIMITER)).skip(1).map(BigDecimal::new).forEach(context::add);
                }
            } else {
                throw new InsufficientParametersException();
            }
        };
    }
}
