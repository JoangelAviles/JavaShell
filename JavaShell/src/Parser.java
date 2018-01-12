import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	private static Map<String,Value> symbolTable = new HashMap<String,Value>();

	private final static String realPattern = "\\d*\\.\\d+";
	private final static String intPattern = "\\d+";
	private final static String idPattern = "[A-Za-z]\\w*";
	private final static String plusPattern = "[+-]";
	private final static String multPattern = "[*/]";
	private final static String assignPattern = "=";
	private final static String lParenPattern = "\\(";
	private final static String rParenPattern = "\\)";
	private final static String whiteSpacePattern = "\\s+";
	private final static String illegalPattern = "\\W+";

	private static Pattern expressionTokenPattern = 
			Pattern.compile(""
					+ realPattern + "|"
					+ intPattern + "|"
					+ idPattern + "|"
					+ plusPattern + "|"
					+ multPattern + "|"
					+ lParenPattern + "|"
					+ rParenPattern + "|"
					+ whiteSpacePattern + "|"
					+ illegalPattern);

	public static Value splitCommand(String s) throws IllegalArgumentException {
		Matcher m = expressionTokenPattern.matcher(s);
		m.find();
		return splitCommand(m);
	}
	
	public static Value evalCommand(String s) throws IllegalArgumentException {

		Matcher m = expressionTokenPattern.matcher(s);
		nextToken(m);
		return evalCommand(m);

	}

	private static boolean nextToken(Matcher m) {
		boolean result = m.find();
		if (!m.hitEnd() && m.group().matches(whiteSpacePattern)) {
			return m.find();
		}
		return result;
	}

	public static Value splitCommand(Matcher m) throws IllegalArgumentException {
		while (!m.hitEnd()) {
			System.out.println("Next Token: " + m.group());
			m.find();
		}
		return new IntegerValue(0);
	}

	public static Value evalCommand(Matcher m) throws IllegalArgumentException {
		Value result = null;
		String id;
		if (m.group().matches(idPattern)) {
			id = m.group();
			nextToken(m);
			if (!m.hitEnd() && m.group().matches(assignPattern)) {
				nextToken(m);
				result = evalExpression(m);
				symbolTable.put(id, result);
				return result;
			}
			else {
				m.reset();
				m.find();
				return evalExpression(m);
			}
		}
		return evalExpression(m);
	}

	public static Value evalExpression(Matcher m) throws IllegalArgumentException {

		Value result = evalTerm(m);

		while (!m.hitEnd() && m.group().matches(plusPattern)) {
			String operator = m.group();
			nextToken(m);
			Value nextOperandValue = evalTerm(m);
			if (operator.equals("+")) {
				result = result.add(nextOperandValue);
			}
			else {
				result = result.subtract(nextOperandValue);
			}
		}
		return result;
	}

	public static Value evalTerm(Matcher m) throws IllegalArgumentException {

		Value result = evalFactor(m);

		while (!m.hitEnd() && m.group().matches(multPattern)) {
			String operator = m.group();
			nextToken(m);
			Value nextOperandValue = evalFactor(m);
			if (operator.equals("*")) {
				result = result.multiply(nextOperandValue);
			}
			else {
				result = result.divide(nextOperandValue);
			}
		}
		return result;
	}

	public static Value evalFactor(Matcher m) throws IllegalArgumentException {
		Value result;
		if (m.group().matches(lParenPattern)) {
			nextToken(m);
			result = evalExpression(m);
			if (!m.group().matches(rParenPattern)) {
				throw new IllegalArgumentException("Missing ')'");
			}
			nextToken(m);
			return result;
		}
		else if (m.group().matches(idPattern)) {
			if (symbolTable.containsKey(m.group())) {
				result = symbolTable.get(m.group());
			}
			else {
				result = null;
			}
			nextToken(m);
			return result;
		}
		else if (m.group().matches(realPattern)) {
			result = new RealValue(Double.parseDouble(m.group()));
			nextToken(m);
			return result;
		}
		else if (m.group().matches(intPattern)) {
			result = new IntegerValue(Long.parseLong(m.group()));
			nextToken(m);
			return result;
		}
		throw new IllegalArgumentException("Syntax Error: Expected (E) or ID or number");
	}
}

