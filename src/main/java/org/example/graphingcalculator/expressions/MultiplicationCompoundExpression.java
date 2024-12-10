package org.example.graphingcalculator.expressions;

import java.util.Arrays;

public class MultiplicationCompoundExpression implements Expression {
    private final Expression[] expressions;

    public MultiplicationCompoundExpression(Expression[] exps) {
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
        double product = 1;
        for (Expression exp : expressions) {
            product *= exp.evaluate(x);
        }
        return product;
    }

    @Override
    public Expression differentiate() {
        return null;
    }
}
