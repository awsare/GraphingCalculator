package org.example.graphingcalculator.expressions;

public class ConstantExpression implements Expression {
    private final String value;

    public ConstantExpression(String value) {
        // adds decimal place
        this.value = String.valueOf(Double.parseDouble(value));
    }

    @Override
    public Expression deepCopy() {
        return new ConstantExpression(value);
    }

    @Override
    public String convertToString(int indentLevel) {
        String re = "";

        for (int i = 0; i < indentLevel; i++) {
            re += "\t";
        }

        return re + value;
    }

    @Override
    public double evaluate(double x) {
        return Double.parseDouble(value);
    }

    @Override
    public Expression differentiate() {
        return new ConstantExpression("0");
    }
}
