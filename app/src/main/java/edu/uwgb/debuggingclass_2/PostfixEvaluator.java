package edu.uwgb.debuggingclass_2;
// File: PostfixEvaluator.java
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class PostfixEvaluator {

    public double evaluate(List<String> postfixTokens) throws ArithmeticException, IllegalArgumentException {
        Deque<Double> stack = new ArrayDeque<>();
        for (String token : postfixTokens) {
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else if (isOperator(token)) {
                if (stack.size() < 2) throw new IllegalArgumentException("Invalid postfix: insufficient operands for " + token);
                double operand2 = stack.pop();
                double operand1 = stack.pop();
                double result;
                switch (token) {
                    case "+": result = operand1 + operand2; break;
                    case "-": result = operand1 - operand2; break;
                    case "*": result = operand1 * operand2; break;
                    case "/":
                        if (operand2 == 0) throw new ArithmeticException("Division by zero");
                        result = operand1 / operand2;
                        break;
                    default: throw new IllegalArgumentException("Unknown operator: " + token);
                }
                stack.push(result);
            } else {
                throw new IllegalArgumentException("Invalid token in postfix: " + token);
            }
        }
        if (stack.size() != 1) throw new IllegalArgumentException("Invalid postfix: stack should end with one result.");
        return stack.pop();
    }

    private boolean isNumeric(String str) {
        if (str == null) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) { return false; }
    }

    private boolean isOperator(String str) {
        return str != null && (str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/"));
    }
}

