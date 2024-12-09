package org.example.graphingcalculator.expressions;

public class DivisionCompoundExpression implements Expression {
    private final Expression left;
    private final Expression right;

    public DivisionCompoundExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression deepCopy() {
        return new DivisionCompoundExpression(left.deepCopy(), right.deepCopy());
    }

    @Override
    public String convertToString(int indentLevel) {
        return "";
    }

    @Override
    public double evaluate(double x) {
        return left.evaluate(x) / right.evaluate(x);
    }

    @Override
    public Expression differentiate() {

        return new DivisionCompoundExpression(
                new SubtractionCompoundExpression(
                        new MultiplicationCompoundExpression(left.differentiate(), right),
                        new MultiplicationCompoundExpression(left, right.differentiate())),
                new ExponentialCompoundExpression(right, new ConstantExpression("2")));
    }
}
