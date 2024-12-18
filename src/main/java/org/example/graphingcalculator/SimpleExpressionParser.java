package org.example.graphingcalculator;

import org.example.graphingcalculator.expressions.*;

import java.util.ArrayList;

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
		System.out.println(str);
		Expression expression = validateExpression(str);
		if (expression == null) {
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}

		return expression;
	}

	// S -> A | P
	protected Expression validateExpression(String str) {
		Expression expression = parseParenthesisExpression(str);

		if (expression == null) {
			expression = parseAdditionExpression(str);
		}

		return expression;
	}

	// P -> (S) | L | V
	protected Expression parseParenthesisExpression(String str) {
		Expression expression = null;

		// store indexes of supposed left and right parenthesis
		int leftParenthesis = 0;
		int rightParenthesis = str.length() - 1;

		// if there are parenthesis in assumed positions, return parenthesis expression
		if (str.length() > 2 &&
				str.charAt(leftParenthesis) == '(' &&
				str.charAt(rightParenthesis) == ')') {

			Expression inside = validateExpression(str.substring(leftParenthesis + 1, rightParenthesis));

			if (inside != null) {
				expression = new ParenthesisExpression(inside);
			}
		}

		if (expression == null) {
			expression = parseConstantExpression(str);
		}

		if (expression == null) {
			expression = parseVariableExpression(str);
		}

		return expression;
	}

	// A -> A+M | A-M | M
	protected Expression parseAdditionExpression(String str) {
		Expression expression = null;

		// loop through the string backwards and check for addition/subtraction symbols
		for (int i = str.length() - 1; i >= 0; i--) {
			if (str.charAt(i) == '+' || str.charAt(i) == '-') {
				Expression left = validateExpression(str.substring(0, i));
				Expression right = validateExpression(str.substring(i+1));

				// if left or right is invalid then move on
				if (left == null || right == null) {
					continue;
				}

				if (str.charAt(i) == '+') {
					expression = new AdditionCompoundExpression(left, right);
				} else {
					expression = new SubtractionCompoundExpression(left, right);
				}
				break;
			}
		}

		if (expression == null) {
			expression = parseMultiplicationExpression(str);
		}

		return expression;
	}

	// M -> M*E | M/E | E
	protected Expression parseMultiplicationExpression(String str) {
		Expression expression = null;

		// loop through the string backwards and check for multiplication/division symbols
		for (int i = str.length() - 1; i >= 0; i--) {
			if (str.charAt(i) == '*' || str.charAt(i) == '/') {
				Expression left = validateExpression(str.substring(0, i));
				Expression right = validateExpression(str.substring(i+1));

				// if left or right is invalid then move on
				if (left == null || right == null) {
					continue;
				}

				if (str.charAt(i) == '*') {
					expression = new MultiplicationCompoundExpression(left, right);
				} else {
					expression = new DivisionCompoundExpression(left, right);
				}
				break;
			}
		}

		if (expression == null) {
			expression = parseExponentialExpression(str);
		}

		return expression;
	}

	// E -> P^E | P | log(P) | T
	protected Expression parseExponentialExpression(String str) {
		Expression expression = null;

		// loop through the string forwards and check for exponential symbols
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '^') {
				Expression base = validateExpression(str.substring(0, i));
				Expression exponent = validateExpression(str.substring(i+1));

				// if base or exponent is invalid then move on
				if (base == null || exponent == null) {
					continue;
				}

				expression = new ExponentialCompoundExpression(base, exponent);
				break;
			}
		}

		if (expression == null) {
			try {
				if (str.startsWith("log")) {
					int leftParenthesis = 3;
					int rightParenthesis = str.length() - 1;

					Expression inside = validateExpression(str.substring(leftParenthesis + 1, rightParenthesis));

					if (inside != null) {
						expression = new NaturalLogarithmicExpression(inside);
					}
				}
			} catch (StringIndexOutOfBoundsException e) {
				// log can't be found and expression is already null, do nothing
			}
		}

		if (expression == null) {
			expression = parseTrigonometricFunction(str);
		}

		return expression;
	}

	// T -> sin(P) | cos(P)
	protected Expression parseTrigonometricFunction(String str) {
		Expression expression = null;

		try {
			if (str.startsWith("sin")) {
				int leftParenthesis = 3;
				int rightParenthesis = str.length() - 1;

				Expression inside = validateExpression(str.substring(leftParenthesis + 1, rightParenthesis));

				if (inside != null) {
					expression = new SineExpression(inside);
				}
			}
		} catch (StringIndexOutOfBoundsException e) {
			// sin can't be found and expression is already null, do nothing
		}

		if (expression == null) {
			try {
				if (str.startsWith("cos")) {
					int leftParenthesis = 3;
					int rightParenthesis = str.length() - 1;

					Expression inside = validateExpression(str.substring(leftParenthesis + 1, rightParenthesis));

					if (inside != null) {
						expression = new CosineExpression(inside);
					}
				}
			} catch (StringIndexOutOfBoundsException e) {
				// cos can't be found and expression is already null, do nothing
			}
		}

		return expression;
	}

	// V -> x
	protected VariableExpression parseVariableExpression (String str) {
		if (str.equals("x")) {
			return new VariableExpression();
		}
		return null;
	}

	// L -> <float>
	protected ConstantExpression parseConstantExpression(String str) {
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
		Expression e = parser.parse("(x) * (x)");
		System.out.println(e.evaluate(0));
	}
}
