package org.example.graphingcalculator.expressions;

import java.util.Arrays;

public class SubtractionCompoundExpression implements Expression {
    private final Expression[] expressions;

    public SubtractionCompoundExpression(Expression[] exps) {
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
        double difference = 0;
        for (Expression exp : expressions) {
            difference -= exp.evaluate(x);
        }
        return difference;
    }

    @Override
    public Expression differentiate() {
        return null;
    }
}
