package org.example.graphingcalculator.expressions;

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
//        return null;
    }

    @Override
    public double evaluate(double x) {
        return 0;
    }

    @Override
    public Expression differentiate() {
        return new ConstantExpression("1");
    }
}
