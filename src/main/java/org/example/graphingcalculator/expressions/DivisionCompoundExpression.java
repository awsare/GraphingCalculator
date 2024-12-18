package org.example.graphingcalculator.expressions;

import org.example.graphingcalculator.ExpressionParseException;

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
    public Expression differentiate() throws UnsupportedOperationException {
        return new DivisionCompoundExpression(
                new SubtractionCompoundExpression(
                        new MultiplicationCompoundExpression(numerator.deepCopy().differentiate(), denominator.deepCopy()),
                        new MultiplicationCompoundExpression(numerator.deepCopy(), denominator.deepCopy().differentiate())),
                new ExponentialCompoundExpression(denominator.deepCopy(), new ConstantExpression("2")));
    }
}
