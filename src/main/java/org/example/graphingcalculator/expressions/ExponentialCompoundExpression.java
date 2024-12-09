package org.example.graphingcalculator.expressions;

public class ExponentialCompoundExpression implements Expression {
    private final Expression left;
    private final Expression right;

    public ExponentialCompoundExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression deepCopy() {
        return new ExponentialCompoundExpression(left, right);
    }

    @Override
    public String convertToString(int indentLevel) {
//        return "";
        return null;
    }

    @Override
    public double evaluate(double x) {
        return Math.pow(left.evaluate(x), right.evaluate(x));
    }

    @Override
    public Expression differentiate() {
        return null;
    }
}
