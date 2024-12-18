package org.example.graphingcalculator.expressions;

import org.example.graphingcalculator.ExpressionParseException;

public class CosineExpression implements Expression {
    private final Expression inside;

    public CosineExpression(Expression inside) {
        this.inside = inside;
    }

    @Override
    public Expression deepCopy() {
        return new SineExpression(inside.deepCopy());
    }

    @Override
    public String convertToString(int indentLevel) {
        String insideString = inside.convertToString(indentLevel + 1);
        String re = "";

        for (int i = 0; i < indentLevel; i++) {
            re += "\t";
        }

        if (indentLevel == 0) {
            re += "\n";
        }

        return re + String.format("cos\n%s", insideString);
    }

    @Override
    public double evaluate(double x) {
        return Math.cos(inside.evaluate(x));
    }

    @Override
    public Expression differentiate() throws UnsupportedOperationException {
        return new MultiplicationCompoundExpression(new SineExpression(inside), inside.differentiate());
    }
}
