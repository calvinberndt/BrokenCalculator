package edu.uwgb.debuggingclass_2;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Map;

public class ShuntingYardConverter {

    private static final Map<String, Integer> PRECEDENCE = Map.of(
            "+", 1,
            "-", 1,
            "*", 2,
            "/", 2
    );

    private boolean isNumeric(String token) {
        if (token == null) return false;
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String token) {
        return PRECEDENCE.containsKey(token);
    }

    public List<String> infixToPostfix(List<String> infixTokens) throws IllegalArgumentException {
        List<String> outputQueue = new ArrayList<>();
        Deque<String> operatorStack = new ArrayDeque<>();

        for (String token : infixTokens) {
            if (isNumeric(token)) {
                outputQueue.add(token);
            } else if (isOperator(token)) {
                while (!operatorStack.isEmpty() && isOperator(operatorStack.peek()) &&
                        (PRECEDENCE.get(operatorStack.peek()) >= PRECEDENCE.get(token))) {
                    outputQueue.add(operatorStack.pop());
                }
                operatorStack.push(token);
            } else {
                throw new IllegalArgumentException("Unknown token (parentheses not allowed): " + token);
            }
        }
        while (!operatorStack.isEmpty()) {
            outputQueue.add(operatorStack.pop());
        }
        return outputQueue;
    }

    public List<String> tokenize(String expression) {
        List<String> tokens = new ArrayList<>();
        StringBuilder currentNumber = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            String charStr = String.valueOf(ch);

            if (Character.isDigit(ch) || ch == '.') {
                currentNumber.append(ch);
            } else {
                if (currentNumber.length() > 0) {
                    tokens.add(currentNumber.toString());
                    currentNumber.setLength(0);
                }
                if (isOperator(charStr)) {
                    if (ch == '-' && (tokens.isEmpty() || isOperator(tokens.get(tokens.size() - 1)))) {
                        if (i + 1 < expression.length() && (Character.isDigit(expression.charAt(i + 1)) || expression.charAt(i + 1) == '.')) {
                            currentNumber.append(ch);
                        } else {
                            tokens.add(charStr);
                        }
                    } else {
                        tokens.add(charStr);
                    }
                } else if (!Character.isWhitespace(ch)) {
                    // System.err.println("Warning: Unknown character (parentheses not supported): " + ch);
                }
            }
        }
        if (currentNumber.length() > 0) {
            tokens.add(currentNumber.toString());
        }
        return tokens;
    }
}

