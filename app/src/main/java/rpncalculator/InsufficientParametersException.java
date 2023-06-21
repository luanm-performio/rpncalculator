package rpncalculator;


class InsufficientParametersException extends RuntimeException {
    public InsufficientParametersException() {
        super("insufficient parameters");
    }
}

