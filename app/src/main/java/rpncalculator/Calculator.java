package rpncalculator;

import java.math.BigDecimal;
import java.util.StringTokenizer;
import java.util.function.Consumer;

public class Calculator {

    private static final String DELIMITER = " ";

    private final CalculatorContext context;
    private final Consumer<String> logConsumer;

    public Calculator(final Consumer<String> logConsumer) {
        context = new CalculatorContext();
        this.logConsumer = logConsumer;
    }

    public void evaluate(final String input) {
        final StringTokenizer tokenizer = new StringTokenizer(input, DELIMITER);

        while (tokenizer.hasMoreTokens()) {
            final String token = tokenizer.nextToken();
            Operator.ofLabel(token).ifPresentOrElse(
                    operator -> operator.getExecutor().execute(context, logConsumer),
                    () -> context.add(new BigDecimal(token, context.getMathContext())));
        }
    }

    public void printCurrentState() {
        logConsumer.accept(context.toString());
    }
}
