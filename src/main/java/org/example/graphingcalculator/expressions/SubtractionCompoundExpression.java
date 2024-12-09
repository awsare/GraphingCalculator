package org.example.graphingcalculator.expressions;

public class SubtractionCompoundExpression implements Expression {
    private final Expression left;
    private final Expression right;

    public SubtractionCompoundExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression deepCopy() {
        return new SubtractionCompoundExpression(left.deepCopy(), right.deepCopy());
    }

    @Override
    public String convertToString(int indentLevel) {
        return "";
    }

    @Override
    public double evaluate(double x) {
        return left.evaluate(x) - right.evaluate(x);
    }

    @Override
    public Expression differentiate() {
        return new SubtractionCompoundExpression(left.differentiate(), right.differentiate());
    }
}
