package org.example.graphingcalculator.expressions;

import java.util.Arrays;

public class DivisionCompoundExpression implements Expression {
    private final Expression numerator;
    private final Expression denominator;

    public DivisionCompoundExpression(Expression numerator, Expression denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    @Override
    public Expression deepCopy() {
        return new DivisionCompoundExpression(numerator.deepCopy(), denominator.deepCopy());
    }

    @Override
    public String convertToString(int indentLevel) {
        String leftString = numerator.convertToString(indentLevel + 1);
        String rightString = denominator.convertToString(indentLevel + 1);
        String re = "";

        for (int i = 0; i < indentLevel; i++) {
            re += "\t";
        }

        re += String.format("/\n%s\n%s", leftString, rightString);

        if (indentLevel == 0) {
            re += "\n";
        }

        return re;
    }

    @Override
    public double evaluate(double x) {
        return numerator.evaluate(x) / denominator.evaluate(x);
    }

    @Override
    public Expression differentiate() {
        return new DivisionCompoundExpression(
                new SubtractionCompoundExpression(
                        new MultiplicationCompoundExpression(numerator.differentiate(), denominator),
                        new MultiplicationCompoundExpression(numerator, denominator.differentiate())),
                new ExponentialCompoundExpression(denominator, new ConstantExpression("2")));
    }
}
