package org.example.graphingcalculator.expressions;

public class LiteralExpression implements Expression {
    private final String value;

    public LiteralExpression(String value) {
        this.value = value;
    }

    @Override
    public Expression deepCopy() {
        return new LiteralExpression(value);
    }

    @Override
    public String convertToString(int indentLevel) {
//        String re = "";
//        for (int i = 0; i < indentLevel; i++) {
//            re += "\t";
//        }
//        return re + value;
        return null;
    }

    @Override
    public double evaluate(double x) {
        return Double.parseDouble(value);
    }

    @Override
    public Expression differentiate() {
        return new LiteralExpression("0");
    }
}
