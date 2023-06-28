package rpncalculator;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.function.Consumer;

public class Calculator {

    private final CalculatorContext context;
    private final Consumer<String> logConsumer;

    public Calculator(final Consumer<String> logConsumer) {
        context = new CalculatorContext()
                .withPrecision(16)
                .withMaximumDisplayDecimalPlaces(10);

        this.logConsumer = logConsumer;
    }

    public void evaluate(final String input) {
        final StringTokenizer tokenizer = new StringTokenizer(input, CalculatorContext.DELIMITER);
        int position = 1;

        while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken();
            final Optional<Operator> operatorOptional = Operator.ofLabel(token);

            if (operatorOptional.isPresent()) {

                try {
                    operatorOptional.get().getExecutor().execute(context);
                } catch (InsufficientParametersException e) {
                    logConsumer.accept(
                        "operator " + operatorOptional.get().getLabel() + 
                        " (position: " + position + "): " + 
                        e.getMessage());
                    break;
                }

            } else {
                context.add(new BigDecimal(token, context.getMathContext()));
            }

            position += CalculatorContext.DELIMITER.length() + token.length();
        }
    }

    public void printCurrentState() {
        logConsumer.accept(context.toString());
    }
}
