package rpncalculator;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        final Calculator calculator = new Calculator(System.out::println);

        try (final Scanner scanner = new Scanner(System.in)) {
            while (true) {
                final String expression = scanner.nextLine();
                if ("q".equals(expression)) {
                    System.exit(0);
                }
                calculator.evaluate(expression);
                calculator.printCurrentState();
            }
        }
    }
}

