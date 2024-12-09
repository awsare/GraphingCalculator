package org.example.graphingcalculator.expressions;

public class MultiplicationCompoundExpression implements Expression {
    private final Expression left;
    private final Expression right;

    public MultiplicationCompoundExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression deepCopy() {
        return new MultiplicationCompoundExpression(left, right);
    }

    @Override
    public String convertToString(int indentLevel) {
//        String leftString = left.convertToString(indentLevel + 1);
//        String rightString = right.convertToString(indentLevel + 1);
//        String re = "";
//        for (int i = 0; i < indentLevel; i++) {
//            re += "\t";
//        }
//        return re + String.format("+\n\t%s\n\t%s\n", leftString, rightString);
        return null;
    }

    @Override
    public double evaluate(double x) {
        return left.evaluate(x) * right.evaluate(x);
    }

    @Override
    public Expression differentiate() {
        return new AdditionCompoundExpression(
                new MultiplicationCompoundExpression(left, right.differentiate()),
                new MultiplicationCompoundExpression(left.differentiate(), right));
    }
}
