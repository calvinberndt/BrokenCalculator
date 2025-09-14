package edu.uwgb.debuggingclass_2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.List;

public class Calculator extends AppCompatActivity {

    /*
    There are errors in this code, that will cause various issues
    Your goal is to get the calculator working as good as possible
    1. Make sure all Numbers input the proper numbers
    2. Make sure the proper operation is applied when = is hit
    3. Think about as many edge cases as you can, can you break the calculator and then fix it?
    4. Can you make it ignore leading 0's if a bunch of 0's are pressed?
    5. BONUS: Can you get the current operation to display somewhere?
     */

    private String currentValue = "";
    private String lastValue = "";
    private String operation = "";

    //String that accumulates on the display
    private String displayString = "";

    //Instaces of helper classes
    private ShuntingYardConverter shuntingYardConverter;
    private PostfixEvaluator postfixEvaluator;
    
    //TextView to display the current value
    private TextView displayView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        
        //Link displayView to the proper view
        displayView = findViewById(R.id.displayValue);
        
        //Initialize helper class instances
        shuntingYardConverter = new ShuntingYardConverter();
        postfixEvaluator = new PostfixEvaluator();
    }

    private void insertNumber(int num) {
        // Handle leading zeros
        if (currentValue.equals("0") && num == 0) {
            // Ignore additional zeros if we already have a zero
            return;
        } else if (currentValue.equals("0")) {
            // Replace leading zero with the new number
            currentValue = Integer.toString(num);
        } else {
            currentValue = currentValue + Integer.toString(num);
        }
        // Update the display to show the current expression plus the current value being entered
        updateDisplayOnScreen(displayString + currentValue);
    }

    private void updateDisplayOnScreen(String str) {
        displayView.setText(str);
    }

    public void onNum1(View view) {
        insertNumber(1);
    }

    public void onNum2(View view) {
        insertNumber(2);
    }

    public void onNum3(View view) {
        insertNumber(3);
    }

    public void onNum4(View view) {
        insertNumber(4);
    }

    public void onNum5(View view) {
        insertNumber(5);
    }

    public void onNum6(View view) {
        insertNumber(6);
    }

    public void onNum7(View view) {
        insertNumber(7);
    }

    public void onNum8(View view) {
        insertNumber(8);
    }

    public void onNum9(View view) {
        insertNumber(9);
    }

    public void onNum0(View view) {
        insertNumber(0);
    }

    public void onAdd(View view) {
        if (currentValue.length() > 0) {
            displayString = displayString + currentValue + " + ";
            updateDisplayOnScreen(displayString);
            currentValue = "";
        }
    }

    public void onSubtract(View view) {
        if (currentValue.length() > 0) {
            displayString = displayString + currentValue + " - ";
            updateDisplayOnScreen(displayString);
            currentValue = "";
        }
    }

    public void onMultiply(View view) {
        if (currentValue.length() > 0) {
            displayString = displayString + currentValue + " * ";
            updateDisplayOnScreen(displayString);
            currentValue = "";
        }
    }

    public void onDivide(View view) {
        if (currentValue.length() > 0) {
            displayString = displayString + currentValue + " / ";
            updateDisplayOnScreen(displayString);
            currentValue = "";
        }
    }

    public void onEquals(View view) {
        // Add current value to complete the expression
        if (currentValue.length() > 0) {
            displayString = displayString + currentValue;
        }
        
        // Get the complete expression
        String expressionToEvaluate = displayString.trim();
        
        if (expressionToEvaluate.length() > 0) {
            try {
                // Tokenize the expression
                List<String> tokens = shuntingYardConverter.tokenize(expressionToEvaluate);
                
                // Convert to postfix notation
                List<String> postfixTokens = shuntingYardConverter.infixToPostfix(tokens);
                
                // Evaluate the postfix expression
                double result = postfixEvaluator.evaluate(postfixTokens);
                
                // Display the result
                String resultStr = formatResult(result);
                updateDisplayOnScreen(resultStr);
                
                // Reset for next calculation
                displayString = "";
                currentValue = resultStr;
                lastValue = "";
                operation = "";
                
            } catch (ArithmeticException e) {
                // Handle division by zero
                updateDisplayOnScreen("Error: Division by zero");
                resetCalculator();
            } catch (Exception e) {
                // Handle any other errors
                updateDisplayOnScreen("Error");
                resetCalculator();
            }
        }
    }

    public void onClear(View view) {
        operation = "";
        currentValue = "";
        lastValue = "";
        displayString = "";
        updateDisplayOnScreen("0");
    }

    private void resetCalculator() {
        operation = "";
        currentValue = "";
        lastValue = "";
        displayString = "";
    }
    
    private String formatResult(double result) {
        // If the result is a whole number, display it without decimals
        if (result == Math.floor(result) && !Double.isInfinite(result)) {
            return String.valueOf((int) result);
        } else {
            return String.valueOf(result);
        }
    }
}
