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
        if (base instanceof ConstantExpression) {
            return new MultiplicationCompoundExpression(new NaturalLogarithmicExpression(base), new MultiplicationCompoundExpression(new ExponentialCompoundExpression(base, exponent), exponent.differentiate()));
        }
        return null;
    }
}
