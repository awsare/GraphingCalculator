package org.example.graphingcalculator.expressions;

public class ParenthesisExpression implements Expression {
    private final Expression inside;

    public ParenthesisExpression(Expression inside) {
        this.inside = inside;
    }

    @Override
    public Expression deepCopy() {
        return new ParenthesisExpression(inside.deepCopy());
    }

    @Override
    public String convertToString(int indentLevel) {
        return "";
    }

    @Override
    public double evaluate(double x) {
        return inside.evaluate(x);
    }

    @Override
    public Expression differentiate() {
        return inside.differentiate();
    }
}
