package org.example.graphingcalculator.expressions;

public class NaturalLogarithmicExpression implements Expression {
    private final Expression inside;

    public NaturalLogarithmicExpression(Expression inside) {
        this.inside = inside;
    }

    @Override
    public Expression deepCopy() {
        return new NaturalLogarithmicExpression(inside.deepCopy());
    }

    @Override
    public String convertToString(int indentLevel) {
        String insideString = inside.convertToString(indentLevel + 1);
        String re = "";
        for (int i = 0; i < indentLevel; i++) {
            re += "\t";
        }
        return re + String.format("log\n%s", insideString);
    }

    @Override
    public double evaluate(double x) {
        return Math.log(inside.evaluate(x));
    }

    @Override
    public Expression differentiate() {
        return new DivisionCompoundExpression(inside.differentiate(), inside);
    }
}
