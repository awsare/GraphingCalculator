package org.example.graphingcalculator.expressions;

import org.example.graphingcalculator.ExpressionParseException;

public class VariableExpression implements Expression {

    @Override
    public Expression deepCopy() {
        return new VariableExpression();
    }

    @Override
    public String convertToString(int indentLevel) {
        String re = "";

        for (int i = 0; i < indentLevel; i++) {
            re += "\t";
        }

        return re + "x";
    }

    @Override
    public double evaluate(double x) {
        return x;
    }

    @Override
    public Expression differentiate() throws UnsupportedOperationException {
        return new ConstantExpression("1");
    }
}
