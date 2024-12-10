package org.example.graphingcalculator.expressions;

import java.util.Arrays;

public class DivisionCompoundExpression implements Expression {
    private final Expression[] expressions;

    public DivisionCompoundExpression(Expression[] exps) {
        expressions = Arrays.copyOf(exps, exps.length);
    }

    @Override
    public Expression deepCopy() {
        return null;
    }

    @Override
    public String convertToString(int indentLevel) {
        return null;
    }

    @Override
    public double evaluate(double x) {
        double quotient = 1;
        for (Expression exp : expressions) {
            quotient /= exp.evaluate(x);
        }
        return quotient;
    }

    @Override
    public Expression differentiate() {
        return null;
    }
}
