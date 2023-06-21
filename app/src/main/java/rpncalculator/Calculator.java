package rpncalculator;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.StringTokenizer;
import java.util.function.Consumer;

public class Calculator {

    private static final String DELIMITER = " ";

    private final CalculatorContext context;
    private final Consumer<String> logConsumer;

    public Calculator(final Consumer<String> logConsumer) {
        context = new CalculatorContext()
                        .withPrecision(16)
                        .withMaximumDisplayDecimalPlaces(10);

        this.logConsumer = logConsumer;
    }

    public void evaluate(final String input) {
        final StringTokenizer tokenizer = new StringTokenizer(input, DELIMITER);

        while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken();
            final Optional<Operator> operatorOptional = Operator.ofLabel(token);
            if (operatorOptional.isPresent()) {
                operatorOptional.get().getExecutor().execute(context, logConsumer);
            } else {
                context.add(new BigDecimal(token, context.getMathContext()));
            }
        }
    }

    public void printCurrentState() {
        logConsumer.accept(context.toString());
    }
}
