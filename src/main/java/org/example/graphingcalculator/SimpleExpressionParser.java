package org.example.graphingcalculator;

import org.example.graphingcalculator.expressions.*;

public class SimpleExpressionParser implements ExpressionParser {
	/**
	 * Attempts to create an expression tree from the specified String.
	 * Throws a ExpressionParseException if the specified string cannot be parsed.
	 * Grammar:
	 * S -> A | P
	 * A -> A+M | A-M | M
	 * M -> M*E | M/E | E
	 * E -> P^E | P | log(P)
	 * P -> (S) | L | V
	 * L -> <float>
	 * V -> x
	 * @param str the string to parse into an expression tree
	 * @return the Expression object representing the parsed expression tree
	 */
	public Expression parse (String str) throws ExpressionParseException {
		str = str.replaceAll(" ", "");
		Expression expression = validateExpression(str);
		if (expression == null) {
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}

		return expression;
	}

	protected Expression validateExpression(String str) {
		Expression expression = parseParentheticalExpression(str);
		if (expression == null) {
			expression = parseAdditiveExpression(str);
		}
		return expression;
	}

	protected Expression parseParentheticalExpression (String str) {
		int leftParenthesis = 0;
		int rightParenthesis = str.length() - 1;

		if (str.length() > 2 &&
			str.charAt(leftParenthesis) == '(' &&
			str.charAt(rightParenthesis) == ')') {

			return validateExpression(str.substring(leftParenthesis + 1, rightParenthesis));
		}

		Expression expression = parseLiteralExpression(str);

		if (expression == null) {
			expression = parseVariableExpression(str);
		}

		return expression;
	}
	
	protected Expression parseAdditiveExpression (String str) {

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '+') {
				Expression left = validateExpression(str.substring(0, i));
				Expression right = validateExpression(str.substring(i+1));

				if (left != null || right != null) {
					return null;
				}

				return new AdditionCompoundExpression(left, right);
			} else if (str.charAt(i) == '-') {
				Expression left = validateExpression(str.substring(0, i));
				Expression right = validateExpression(str.substring(i+1));

				if (left != null || right != null) {
					return null;
				}

				return new SubtractionCompoundExpression(left, right);
			}
		}

		return null;
	}

	protected VariableExpression parseVariableExpression (String str) {
		if (str.equals("x")) {
			return new VariableExpression();
		}
		return null;
	}

	protected ConstantExpression parseLiteralExpression (String str) {
		// From https://stackoverflow.com/questions/3543729/how-to-check-that-a-string-is-parseable-to-a-double/22936891:
		final String Digits     = "(\\p{Digit}+)";
		final String HexDigits  = "(\\p{XDigit}+)";
		// an exponent is 'e' or 'E' followed by an optionally 
		// signed decimal integer.
		final String Exp        = "[eE][+-]?"+Digits;
		final String fpRegex    =
		    ("[\\x00-\\x20]*"+ // Optional leading "whitespace"
		    "[+-]?(" +         // Optional sign character
		    "NaN|" +           // "NaN" string
		    "Infinity|" +      // "Infinity" string

		    // A decimal floating-point string representing a finite positive
		    // number without a leading sign has at most five basic pieces:
		    // Digits . Digits ExponentPart FloatTypeSuffix
		    // 
		    // Since this method allows integer-only strings as input
		    // in addition to strings of floating-point literals, the
		    // two sub-patterns below are simplifications of the grammar
		    // productions from the Java Language Specification, 2nd 
		    // edition, section 3.10.2.

		    // Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
		    "((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

		    // . Digits ExponentPart_opt FloatTypeSuffix_opt
		    "(\\.("+Digits+")("+Exp+")?)|"+

		    // Hexadecimal strings
		    "((" +
		    // 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
		    "(0[xX]" + HexDigits + "(\\.)?)|" +

		    // 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
		    "(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

		    ")[pP][+-]?" + Digits + "))" +
		    "[fFdD]?))" +
		    "[\\x00-\\x20]*");// Optional trailing "whitespace"

		if (str.matches(fpRegex)) {
			return new ConstantExpression(str);
		}

		return null;
	}

	public static void main (String[] args) throws ExpressionParseException {
		final ExpressionParser parser = new SimpleExpressionParser();
		System.out.println(parser.parse("10*2+12-4.").convertToString(0));
	}
}
