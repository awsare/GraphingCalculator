package org.example.graphingcalculator.expressions;

import java.util.Arrays;

public class AdditionCompoundExpression implements Expression {
    private final Expression[] expressions;

    public AdditionCompoundExpression(Expression[] exps) {
        expressions = Arrays.copyOf(exps, exps.length);
    }

    @Override
    public Expression deepCopy() {
        return null;
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
        double sum = 0;
        for (Expression exp : expressions) {
            sum += exp.evaluate(x);
        }
        return sum;
    }

    @Override
    public Expression differentiate() {
        return null;
    }
}
