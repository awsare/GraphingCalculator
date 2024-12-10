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
        return new ExponentialCompoundExpression(base, exponent);
    }

    @Override
    public String convertToString(int indentLevel) {
//        return "";
        return null;
    }

    @Override
    public double evaluate(double x) {
        return Math.pow(base.evaluate(x), exponent.evaluate(x));
    }

    @Override
    public Expression differentiate() {
        // If function f(x)=C * h(x) (where C is a positive constant), then its derivative is f'(x)=(log C) * C^h(x) * h'(x).
        if (base instanceof ConstantExpression) {
            return new MultiplicationCompoundExpression(new Expression[]{
                    new NaturalLogarithmicExpression(base),
                    new ExponentialCompoundExpression(base, exponent),
                    exponent.differentiate()});
        }

        // If function f(x)=g(x)^C (where C is a constant), then its derivative is f'(x)=C * g(x)^(C-1) * g'(x).
        return new MultiplicationCompoundExpression(new Expression[]{
                exponent,
                new ExponentialCompoundExpression(
                        base,
                        new SubtractionCompoundExpression(new Expression[]{exponent, new ConstantExpression("1")})),
                base.differentiate()
        });
    }
}
