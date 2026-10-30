package commun;

import java.io.Serializable;

public class RequeteCalcul implements Serializable {
    private static final long serialVersionUID = 1L;

    private final double a;
    private final double b;
    private final char operation;

    public RequeteCalcul(double a, double b, char operation) {
        this.a = a;
        this.b = b;
        this.operation = operation;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public char getOperation() {
        return operation;
    }
}
