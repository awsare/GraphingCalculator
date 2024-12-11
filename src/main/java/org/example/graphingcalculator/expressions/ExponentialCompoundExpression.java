package org.example.graphingcalculator.expressions;

public class ExponentialCompoundExpression implements Expression {
    private final Expression base;
    private final Expression exponent;

    public ExponentialCompoundExpression(Expression base, Expression exponent) {
        this.base = base;
        this.exponent = exponent;
    }

    @Override
    public Expression deepCopy() {
        return new ExponentialCompoundExpression(base.deepCopy(), exponent.deepCopy());
    }

    @Override
    public String convertToString(int indentLevel) {
        String leftString = base.convertToString(indentLevel + 1);
        String rightString = exponent.convertToString(indentLevel + 1);
        String re = "";
        for (int i = 0; i < indentLevel; i++) {
            re += "\t";
        }
        System.out.println("printing an exponential");
        return re + String.format("^\n%s\n%s\n", leftString, rightString);
    }

    @Override
    public double evaluate(double x) {
        return Math.pow(base.evaluate(x), exponent.evaluate(x));
    }

    @Override
    public Expression differentiate() {
        // If function f(x)=C^h(x) (where C is a positive constant), then its derivative is f'(x)=(log C) * C^h(x) * h'(x).
        if (base instanceof ConstantExpression) {
            return new MultiplicationCompoundExpression(
                    new NaturalLogarithmicExpression(base),
                    new MultiplicationCompoundExpression(
                            new ExponentialCompoundExpression(
                                    base,
                                    exponent),
                            exponent.differentiate()));
        }

        // If function f(x)=g(x)^C (where C is a constant), then its derivative is f'(x)=C * g(x)^(C-1) * g'(x).
        return new MultiplicationCompoundExpression(
                exponent,
                new MultiplicationCompoundExpression(
                        new ExponentialCompoundExpression(
                                base,
                                new SubtractionCompoundExpression(
                                        exponent,
                                        new ConstantExpression("1"))),
                        base.differentiate()));
    }
}
