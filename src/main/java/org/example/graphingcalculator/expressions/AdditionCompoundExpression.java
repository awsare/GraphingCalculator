package org.example.graphingcalculator.expressions;

public class AdditionCompoundExpression implements Expression {
    private final Expression left;
    private final Expression right;

    public AdditionCompoundExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public Expression deepCopy() {
        return new AdditionCompoundExpression(left.deepCopy(), right.deepCopy());
    }

    @Override
    public String convertToString(int indentLevel) {
        String leftString = left.convertToString(indentLevel + 1);
        String rightString = right.convertToString(indentLevel + 1);
        String re = "";
        for (int i = 0; i < indentLevel; i++) {
            re += "\t";
        }
        System.out.println("printing an addition");
        if (indentLevel == 0) {
            return re + String.format("+\n%s\n%s\n", leftString, rightString);
        }
        else {
            return re + String.format("+\n%s\n%s", leftString, rightString);
        }
    }

    @Override
    public double evaluate(double x) {
        return left.evaluate(x) + right.evaluate(x);
    }

    @Override
    public Expression differentiate() {
        return new AdditionCompoundExpression(left.differentiate(), right.differentiate());
    }
}
