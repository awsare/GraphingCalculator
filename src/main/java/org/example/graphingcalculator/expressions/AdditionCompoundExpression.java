package org.example.graphingcalculator.expressions;

import org.example.graphingcalculator.ExpressionParseException;

public class AdditionCompoundExpression implements Expression {
    private final Expression left;
    private final Expression right;

    /**
     * @param left is the left side of the expression
     * @param right is the right side of the expression
     */
    public AdditionCompoundExpression(Expression left, Expression right) {
        this.left = left;
        this.right = right;
    }

    /**
     * @return a deep copy of this class
     */
    @Override
    public Expression deepCopy() {
        return new AdditionCompoundExpression(left.deepCopy(), right.deepCopy());
    }

    /**
     * @param indentLevel how many tab characters should appear at the beginning of each line.
     * @return this addition expression in string format
     */
    @Override
    public String convertToString(int indentLevel) {
        String leftString = left.convertToString(indentLevel + 1);
        String rightString = right.convertToString(indentLevel + 1);
        String re = "";

        for (int i = 0; i < indentLevel; i++) {
            re += "\t";
        }

        re += String.format("+\n%s\n%s", leftString, rightString);

        if (indentLevel == 0) {
            re += "\n";
        }

        return re;
    }

    /**
     * @param x the value of the independent variable x
     * @return the evaluated value with x of this addition expression
     */
    @Override
    public double evaluate(double x) {
        return left.evaluate(x) + right.evaluate(x);
    }

    /**
     * @return the derivative expression of this addition expression
     */
    @Override
    public Expression differentiate() throws UnsupportedOperationException {
        return new AdditionCompoundExpression(left.deepCopy().differentiate(), right.deepCopy().differentiate());
    }
}
